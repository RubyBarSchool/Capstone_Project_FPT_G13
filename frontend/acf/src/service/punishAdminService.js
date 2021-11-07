import http from "./http-common";

class punishAdminService {
    searchPunishAdmin(form) {
        return http.post("admin/punish/search", form);
    }
    getAllEmployee(form) {
        return http.post("/admin/employee/search", form);
    }
    addPunishAdmin(form) {
        return http.post(`/admin/punish/add`, form);
    }
    updatePunishAdmin(data) {
        return http.put(`/admin/punish/update`, data);
    }
    deletePunishAdmin(id) {
        return http.delete(`/admin/punish/delete?id=${id}`);
    }
}

export default new punishAdminService();