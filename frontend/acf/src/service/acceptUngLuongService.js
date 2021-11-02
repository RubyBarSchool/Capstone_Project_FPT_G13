import http from "./http-common";

class acceptUngLuongService {
    acceptAdvanceSalaryAdmin(id) {
        return http.put(`/admin/advanceSalary/accept`, id);
    }
    getDetailAdvanceSalaryAdmin(id) {
        return http.get(`/admin/advanceSalary/detail`, id);
    }
    searchAdvanceSalaryAdmin(form) {
        return http.post(`/admin/advanceSalary/search`, form);
    }
}

export default new acceptUngLuongService();