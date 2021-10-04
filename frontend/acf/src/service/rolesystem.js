import http from "./http-common";

class RoleService {
  getAllAccount(user) {
    return http.post(`/spadmin/account/allaccount`, user);
  }
  searchAccount(name) {
    return http.post("/spadmin/account/searchaccount", name);
  }
}

export default new RoleService();
