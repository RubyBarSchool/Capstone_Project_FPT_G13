import http from "./http-common";

class acceptAdvanceSalaryService {
    acceptAdvanceSalaryAdmin(form) {
        return http.put(`/admin/advanceSalary/accept`, form);
    }
    searchAdvanceSalaryAdmin(form) {
        return http.post(`/admin/advanceSalary/search`, form);
    }
    rejectAdvanceSalaryAdmin(form) {
        return http.put(`/admin/advanceSalary/reject`, form);
    }
}

export default new acceptAdvanceSalaryService();