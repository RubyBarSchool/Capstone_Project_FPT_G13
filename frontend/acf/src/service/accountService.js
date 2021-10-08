import http from "./http-common";

class AccounService {
    getAllAccount(user) {
        return http.post(`/spadmin/account/allaccount`, user);
    }
    searchAccount(name) {
        return http.post("/spadmin/account/searchaccount", name);
    }
    addAccount(form) {
        return http.post("/spadmin/account/add", form);
    }
    updateAccount(data) {
        return http.put(`/spadmin/account/update`, data);
    }
    getAccountByID(id) {
        return http.post(`/spadmin/account/getAcc?id=${id}`);
    }
    generateUsername(fullname) {
        return http.post(`/spadmin/account/generateUsername`, fullname);
    }
    deleteAccount(id) {
        return http.delete(`/spadmin/account/delete`, id);
    }
    
}

export default new AccounService();