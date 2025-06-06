import axios from "../commen/axios";

class CustomerService {

    postRequest = async (data) => {
        const promise = new Promise((resolve, reject) => {
            axios.post('request/request-gas', data)  
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        });

        return await promise;
    }

    updateProfile = async (data) => {
        const promise = new Promise((resolve, reject) => {
            axios.put(`client/update-profile`, data) 
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        });

        return await promise;
    }

    fetchCustomer = async (email) => {
        const promise = new Promise((resolve, reject) => {
            axios.get(`client/search?username=${email}`)
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }

    fetchToken = async (id) => {
        const promise = new Promise((resolve, reject) => {
            axios.get(`request/token/${id}`)
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }



    fetchRequests = async (id) => {
        const promise = new Promise((resolve, reject) => {
            axios.get(`request/all/client/${id}`)
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }

    fetchOutlets = async () => {
        const promise = new Promise((resolve, reject) => {
            axios.get(`outlet/all/outlet-names`)
                .then((res) => {
                    return resolve(res)
                })
                .catch((err) => {
                    return resolve(err)
                })
        })
        return await promise;
    }


    cancelRequest = async (id) => {
        const promise = new Promise((resolve, reject) => {
           axios.delete(`request/cancel/${id}`)
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

const customerService = new CustomerService();
export default customerService;