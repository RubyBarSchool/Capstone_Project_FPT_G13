import http from "./http-common";

class bonusAdminService {
    searchBonusAdmin(form) {
        return http.post("/admin/bonus/search", form);
    }
    getAllEmployee(form) {
        return http.post("/admin/employee/search", form);
    }
    addBonusAdmin(form) {
        return http.post(`/admin/bonus/add`, form);
    }
    updateBonusAdmin(data) {
        return http.put(`/admin/bonus/update`, data);
    }
    deleteBonusAdmin(id) {
        return http.delete(`/admin/bonus/delete?id=${id}`);
    }
}

export default new bonusAdminService();