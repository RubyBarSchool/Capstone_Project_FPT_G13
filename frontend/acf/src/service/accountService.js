import http from "./http-common";

class AccounService {
    getAllAccount(user) {
        return http.post(`/spadmin/account/allaccount`, user);
    }
    searchAccount(name) {
        return http.post("/spadmin/account/searchaccount", name);
    }
    updateAccount(data) {
        return http.put(`/spadmin/account/update`, data);
    }
    getAccountByID(id) {
        return http.post(`/spadmin/account/getAcc?id=${id}`);
    }
    generateUsername(fullname) {
        return http.post(`/spadmin/account/generateUsername?fullname=${fullname}`);
    }
    deleteAccount(id) {
        return http.delete(`/spadmin/account/delete?id=${id}`);
    }

}

export default new AccounService();