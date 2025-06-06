import axios from "../commen/axios";

class ManagerService {

    postSchedule = async (data) => {
        const promise = new Promise((resolve, reject) => {
            axios.post('schedule/schedule-delivery', data)  
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        });

        return await promise;
    }


    fetchShedulesByOutlet = async (id) => {
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

    fetchOutletByManager = async (email) => {
        const promise = new Promise((resolve, reject) => {
            axios.get(`outlet/find/${email}`)
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }

    fetchClientRequestsByOutlet = async (id) => {
        const promise = new Promise((resolve, reject) => {
            axios.get(`request/all/outlet/${id}`)
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }

    updateClientRequestStatus = async (id,status) => {
        const promise = new Promise((resolve, reject) => {
            axios.put(`request/update-status/${id}`,null, { status })
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

const managerervice = new ManagerService();
export default managerervice;