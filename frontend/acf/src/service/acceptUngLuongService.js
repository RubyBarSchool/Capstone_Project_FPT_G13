import http from "./http-common";

class acceptUngLuongService {
    acceptAdvanceSalaryAdmin() {
        return http.put(`/admin/advanceSalary/accept`);
    }
    getDetailAdvanceSalaryAdmin(id) {
        return http.get(`/admin/advanceSalary/detail?id=${id}`);
    }
    searchAdvanceSalaryAdmin(form) {
        return http.post(`/admin/advanceSalary/search`, form);
    }
}

export default new acceptUngLuongService();