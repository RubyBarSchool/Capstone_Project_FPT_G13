import http from "./http-common";

class RoleService {
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

}

export default new RoleService();