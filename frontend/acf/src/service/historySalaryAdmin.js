import http from "./http-common";

class historySalaryAdminService {
    salaryHistory(form) {
        return http.post(`/admin/salary/history`, form);
    }
}

export default new historySalaryAdminService();