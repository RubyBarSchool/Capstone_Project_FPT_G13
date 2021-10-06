import http from "./http-common";

class RoleService {
    getAllRole(form) {
        return http.get(`/spadmin/role`, form);
    }
}

export default new RoleService();