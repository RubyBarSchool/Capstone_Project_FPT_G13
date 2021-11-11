import http from "./http-common";

class adminService {
    searchAccount(name) {
        return http.post("/spadmin/account/search", name);
    }
    getAccountByID(id) {
        return http.post(`/spadmin/account/getAcc?id=${id}`);
    }
    updateAccount(data) {
        return http.put(`/spadmin/account/update`, data);
    }

    // getAllAccount(user) {
    //     return http.post(`/spadmin/account/allaccount`, user);
    // }
    // generateUsername(fullname) {
    //     return http.post(`/spadmin/account/generateUsername?fullname=${fullname}`);
    // }
    // deleteAccount(id) {
    //     return http.delete(`/spadmin/account/delete?id=${id}`);
    // }
    // addAccount(form) {
    //     return http.post(`/spadmin/account/add`, form);
    // }

}

export default new adminService();