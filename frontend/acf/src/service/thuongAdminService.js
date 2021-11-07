import http from "./http-common";

class thuongAdminService {
    searchThuongAdmin(form) {
        return http.post("/admin/bonus/search", form);
    }
    getAllEmployee(form) {
        return http.post("/admin/employee/search", form);
    }
    addThuongAdmin(form) {
        return http.post(`/admin/bonus/add`, form);
    }
    updateThuongAdmin(data) {
        return http.put(`/admin/bonus/update`, data);
    }
    deleteThuongAdmin(id) {
        return http.delete(`/admin/bonus/delete?id=${id}`);
    }
}

export default new thuongAdminService();