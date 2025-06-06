import axios from "../commen/axios";

class AdminService {

    approveBusinessReg = async (params) => {
        const promise = new Promise((resolve, reject) => {
            axios.put(`client/business/verify`, null, { params })
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }

    fetchBusinessRegRequests = async () => {
        const promise = new Promise((resolve, reject) => {
            axios.get(`client/business/pending`)
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }

    fetchScheduleByOutlet = async (id) => {
        const promise = new Promise((resolve, reject) => {
            axios.get(`schedule/all/outlet/${id}`)
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }

    fetchScheduleByStatus = async (status) => {
        const promise = new Promise((resolve, reject) => {
            axios.get(`schedule/all/${status}`)
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }


    approveSchedule = async (id,params) => {
        const promise = new Promise((resolve, reject) => {
            axios.put(`schedule/update-status/${id}`, null, { params })
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }

    cancelScheduleRequest = async (id) => {
        const promise = new Promise((resolve, reject) => {
           axios.delete(`schedule/delete/${id}`)
           .then((res) => {
               return resolve(res)
           }) 
           .catch((err) => {
               return resolve(err)
           })
        })
        return await promise;
   };
}

const adminService = new AdminService();
export default adminService;