package com.lmu.gasbygas.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmu.gasbygas.dto.request.GasRequestDetailsDTO;
import com.lmu.gasbygas.dto.request.GasRequestReqDTO;
import com.lmu.gasbygas.dto.response.ClientSearchResDTO;
import com.lmu.gasbygas.dto.response.GasReqDetailsResDTO;
import com.lmu.gasbygas.dto.response.GasRequestResDTO;
import com.lmu.gasbygas.dto.response.TokenResDTO;
import com.lmu.gasbygas.entity.ClientEntity;
import com.lmu.gasbygas.entity.DeliveryScheduleEntity;
import com.lmu.gasbygas.entity.DeliveryStockEntity;
import com.lmu.gasbygas.entity.GasEntity;
import com.lmu.gasbygas.entity.GasRequestDetailsEntity;
import com.lmu.gasbygas.entity.GasRequestEntity;
import com.lmu.gasbygas.entity.OutletEntity;
import com.lmu.gasbygas.entity.TokenEntity;
import com.lmu.gasbygas.repository.ClientRepo;
import com.lmu.gasbygas.repository.GasRepo;
import com.lmu.gasbygas.repository.OutletRepo;
import com.lmu.gasbygas.repository.RequestDetailsRepo;
import com.lmu.gasbygas.repository.RequestRepo;
import com.lmu.gasbygas.repository.ScheduleRepo;
import com.lmu.gasbygas.repository.StockRepo;
import com.lmu.gasbygas.repository.TokenRepo;
import com.lmu.gasbygas.service.RequestService;
import com.lmu.gasbygas.util.ResponseUtil;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private RequestDetailsRepo requestDetailsRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private GasRepo gasRepo;

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private StockRepo stockRepo;

    @Autowired
    private OutletRepo outletRepo;

    @Autowired
    private TokenRepo tokenRepo;

    @Override
    public ResponseUtil requestGas(GasRequestReqDTO reqDto) {

        ClientEntity clientEntity = clientRepo.findClientByClientId(reqDto.getClientId());
        if (clientEntity == null) {
            return new ResponseUtil(404, "Client not found with ID: " + reqDto.getClientId(), null);
        }

        OutletEntity outletEntity = outletRepo.findByOutletId(reqDto.getOutletId());
        if (outletEntity == null) {
            return new ResponseUtil(404, "Outlet not found with ID: " + reqDto.getOutletId(), null);
        }

        long existingRequestCount = requestRepo.countByClient_ClientIdAndStatusNot(reqDto.getClientId(),
                GasRequestEntity.RequestStatus.COMPLETED);
        boolean existingRequest = existingRequestCount > 0;
        if (existingRequest) {
            return new ResponseUtil(400, "Client already has an active gas request.", null);
        }

        DeliveryScheduleEntity scheduleEntity = scheduleRepo.findByOutletAndStatusOrderByDeliveryDateAsc(
                reqDto.getOutletId());
        if (scheduleEntity == null) {
            return new ResponseUtil(400, "No scheduled gas delivery available for this outlet.", null);
        }

        boolean hasSufficientStock = true;
        List<DeliveryStockEntity> updatedStockList = new ArrayList<>();

        for (GasRequestDetailsDTO detailsDTO : reqDto.getRequestList()) {
            GasEntity gasEntity = gasRepo.findGasByGasId(detailsDTO.getGasId());
            if (gasEntity == null) {
                return new ResponseUtil(404, "Gas type not found with ID: " + detailsDTO.getGasId(), null);
            }
            DeliveryStockEntity stockEntity = stockRepo.findByScheduleIdAndGasId(scheduleEntity.getScheduleId(),
                    gasEntity.getGasId());
            if (stockEntity == null || stockEntity.getQty() < detailsDTO.getQuantity()) {
                hasSufficientStock = false;
                break;
            } else {
                stockEntity.setQty(stockEntity.getQty() - detailsDTO.getQuantity());
                updatedStockList.add(stockEntity);
            }
        }
        GasRequestEntity gasRequest = new GasRequestEntity();
        gasRequest.setClient(clientEntity);
        gasRequest.setOutlet(outletEntity);
        gasRequest.setStatus(
                hasSufficientStock ? GasRequestEntity.RequestStatus.APPROVED : GasRequestEntity.RequestStatus.PENDING);
        gasRequest = requestRepo.save(gasRequest);

        List<GasRequestDetailsEntity> requestDetailsList = new ArrayList<>();
        for (GasRequestDetailsDTO detailsDTO : reqDto.getRequestList()) {
            GasRequestDetailsEntity requestDetails = new GasRequestDetailsEntity();
            requestDetails.setRequest(gasRequest);
            requestDetails.setGas(gasRepo.findById(detailsDTO.getGasId()).orElse(null));
            requestDetails.setQuantity(detailsDTO.getQuantity());
            requestDetailsList.add(requestDetails);
        }
        requestDetailsRepo.saveAll(requestDetailsList);

        if (hasSufficientStock) {
            TokenEntity token = new TokenEntity();
            token.setRequest(gasRequest);

            LocalDateTime pickupStartTime = scheduleEntity.getDeliveryDate().atTime(8, 0);
            System.out.println("Scheduled delivery date: " + scheduleEntity.getDeliveryDate());

            LocalDateTime pickupSlot = calculateNextAvailableSlot(pickupStartTime, scheduleEntity.getScheduleId());

            token.setPickupDate(pickupSlot);
            token.setExpiryDate(pickupSlot.plusWeeks(2));

            token.setStatus(TokenEntity.TokenStatus.ACTIVE);

            tokenRepo.save(token);
            stockRepo.saveAll(updatedStockList);

            return new ResponseUtil(200, "Gas request approved and token generated", null);
        }
        return new ResponseUtil(200, "Gas request added to waiting list (PENDING)", null);
    }

    private LocalDateTime calculateNextAvailableSlot(LocalDateTime startTime, int scheduleId) {
        System.out.println("Starting slot calculation with date: " + startTime);

        List<TokenEntity> existingTokens = tokenRepo.findByScheduleId(scheduleId);

        int slotDurationMinutes = 10;
        int maxPeoplePerSlot = 30;
        int totalSlotsPerDay = (5 * 60) / slotDurationMinutes;

        Map<LocalDateTime, Long> slotCount = existingTokens.stream()
                .collect(Collectors.groupingBy(TokenEntity::getPickupDate, Collectors.counting()));

        LocalDateTime pickupSlot = startTime;
        int assignedSlots = 0;

        while (slotCount.getOrDefault(pickupSlot, 0L) >= maxPeoplePerSlot) {
            System.out.println("Slot full at: " + pickupSlot + ", moving to next slot...");
            pickupSlot = pickupSlot.plusMinutes(slotDurationMinutes);
            assignedSlots++;

            if (assignedSlots >= totalSlotsPerDay) {
                pickupSlot = pickupSlot.plusDays(1).withHour(8).withMinute(0);
                assignedSlots = 0;
            }
        }

        System.out.println("Final selected slot: " + pickupSlot);
        return pickupSlot;
    }

    @Override
    public ResponseUtil getAllRequests() {
        List<GasRequestEntity> requestEntities = requestRepo.findAll();
        List<GasRequestResDTO> requestResDTOs = new ArrayList<>();
        if (requestEntities != null) {
            for (GasRequestEntity gre : requestEntities) {
                GasRequestResDTO gdto = new GasRequestResDTO();
                gdto.setRequestId(gre.getRequestId());
                gdto.setClientId(gre.getClient().getClientId());
                gdto.setOutletName(gre.getOutlet().getName());
                gdto.setRequestDate(gre.getCreated_at());
                gdto.setStatus(gre.getStatus().name());
                gdto.setResDetails(gre.getRequestDetails().stream().map(deatils -> new GasReqDetailsResDTO(
                        deatils.getDetailId(),
                        deatils.getGas().getGasId(),
                        deatils.getGas().getType(),
                        deatils.getGas().getPrice(),
                        deatils.getQuantity())).collect(Collectors.toList()));
                requestResDTOs.add(gdto);
            }
            return new ResponseUtil(200, "Success", requestResDTOs);
        } else {
            throw new RuntimeException("Unsuccess");
        }
    }

    @Override
    public ResponseUtil getAllRequestByClientId(int id) {
        List<GasRequestEntity> requestEntities = requestRepo.findAllByClientId(id);
        List<GasRequestResDTO> requestResDTOs = new ArrayList<>();
        if (requestEntities != null) {
            for (GasRequestEntity gre : requestEntities) {
                GasRequestResDTO gdto = new GasRequestResDTO();
                gdto.setRequestId(gre.getRequestId());
                gdto.setClientId(gre.getClient().getClientId());
                gdto.setOutletName(gre.getOutlet().getName());
                gdto.setRequestDate(gre.getCreated_at());
                gdto.setStatus(gre.getStatus().name());
                gdto.setResDetails(gre.getRequestDetails().stream().map(deatils -> new GasReqDetailsResDTO(
                        deatils.getDetailId(),
                        deatils.getGas().getGasId(),
                        deatils.getGas().getType(),
                        deatils.getGas().getPrice(),
                        deatils.getQuantity())).collect(Collectors.toList()));
                requestResDTOs.add(gdto);
            }
            return new ResponseUtil(200, "Success", requestResDTOs);
        } else {
            throw new RuntimeException("Unsuccess");
        }
    }

    @Override
    public ResponseUtil getAllRequestByOutletId(int id) {
        List<GasRequestEntity> requestEntities = requestRepo.findAllByOutletId(id);
        List<GasRequestResDTO> requestResDTOs = new ArrayList<>();
        if (requestEntities != null) {
            for (GasRequestEntity gre : requestEntities) {
                GasRequestResDTO gdto = new GasRequestResDTO();
                gdto.setRequestId(gre.getRequestId());
                gdto.setClientId(gre.getClient().getClientId());
                gdto.setOutletName(gre.getOutlet().getName());
                gdto.setRequestDate(gre.getCreated_at());
                gdto.setStatus(gre.getStatus().name());
                gdto.setResDetails(gre.getRequestDetails().stream().map(deatils -> new GasReqDetailsResDTO(
                        deatils.getDetailId(),
                        deatils.getGas().getGasId(),
                        deatils.getGas().getType(),
                        deatils.getGas().getPrice(),
                        deatils.getQuantity())).collect(Collectors.toList()));
                requestResDTOs.add(gdto);
            }
            return new ResponseUtil(200, "Success", requestResDTOs);
        } else {
            throw new RuntimeException("Unsuccess");
        }
    }

    @Override
    public ResponseUtil updateRequestStatus(int id, String status) {
        Optional<GasRequestEntity> optionalRequest = requestRepo.findById(id);
        if (optionalRequest.isEmpty()) {
            return new ResponseUtil(404, "Request not found with ID: " + id, null);
        }
        GasRequestEntity requestEntity = optionalRequest.get();
        try {
            requestEntity.setStatus(GasRequestEntity.RequestStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            return new ResponseUtil(400, "Invalid status value: " + status, null);
        }
        requestRepo.save(requestEntity);
        return new ResponseUtil(200, "Request status updated successfully", null);
    }

    @Override
    public ResponseUtil cancelRequest(int id) {
        Optional<GasRequestEntity> requestOptional = requestRepo.findById(id);
        if (requestOptional.isEmpty()) {
            return new ResponseUtil(404, "Gas request not found with ID: " + id, null);
        }
        GasRequestEntity gasRequest = requestOptional.get();

        if (gasRequest.getStatus() == GasRequestEntity.RequestStatus.REJECTED ||
                gasRequest.getStatus() == GasRequestEntity.RequestStatus.COMPLETED) {
            return new ResponseUtil(400, "Request cannot be canceled", null);
        }

        if (gasRequest.getStatus() == GasRequestEntity.RequestStatus.APPROVED) {
            List<GasRequestDetailsEntity> requestDetailsList = requestDetailsRepo.findByGasRequestId(id);
            for (GasRequestDetailsEntity details : requestDetailsList) {

                Optional<DeliveryScheduleEntity> latestSchedule = scheduleRepo
                        .findTopByOutletOrderByDeliveryDateDesc(gasRequest.getOutlet());

                if (latestSchedule.isPresent()) {
                    DeliveryStockEntity stock = stockRepo.findByScheduleIdAndGasId(
                            latestSchedule.get().getScheduleId(), details.getGas().getGasId());

                    if (stock != null) {
                        stock.setQty(stock.getQty() + details.getQuantity());
                        stockRepo.save(stock);
                    }
                }
            }
        }
        requestDetailsRepo.deleteByRequest(gasRequest);
        requestRepo.delete(gasRequest);

        return new ResponseUtil(200, "Gas request canceled successfully", null);
    }

    @Override
    public ResponseUtil getTokenByRequestId(int id) {

        TokenEntity tokenEntity = tokenRepo.findByRequestId(id);
        if (tokenEntity != null) {
            TokenResDTO tokenResDTO = setTokenDTO(tokenEntity, new TokenResDTO());
            if (tokenResDTO != null) {
                return new ResponseUtil(200, "Success", tokenResDTO);
            } else {
                throw new RuntimeException("Unsuccess");
            }
        } else {
            throw new RuntimeException("Token not found");
        }

    }

    private TokenResDTO setTokenDTO(TokenEntity tokenEntity, TokenResDTO tokenResDTO) {
        tokenResDTO.setTokenId(tokenEntity.getTokenId());
        tokenResDTO.setPickupDate(tokenEntity.getPickupDate());
        tokenResDTO.setExpiryDate(tokenEntity.getExpiryDate());
        tokenResDTO.setStatus(tokenEntity.getStatus().name());
        return tokenResDTO;
       
    }

}
