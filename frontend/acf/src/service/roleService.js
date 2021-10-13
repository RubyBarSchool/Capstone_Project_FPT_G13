import http from "./http-common";

class RoleService {
    getAllRole(form) {
        return http.post(`/spadmin/role`, form);
    }
}

export default new RoleService();