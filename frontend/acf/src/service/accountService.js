import http from "./http-common";

class AccounService {
    getAllAccount(user) {
        return http.post(`/spadmin/account/allaccount`, user);
    }
    searchAccount(name) {
        return http.post("/spadmin/account/searchaccount", name);
    }
    createAccount(form) {
        return http.post("/spadmin/account", form);
    }
    updateAccount(id, data) {
        return http.put(`/spadmin/account/${id}`, data);
    }
    getAccountByID(id) {
        return http.post(`/spadmin/account/getAcc`, id);
    }
}

export default new AccounService();