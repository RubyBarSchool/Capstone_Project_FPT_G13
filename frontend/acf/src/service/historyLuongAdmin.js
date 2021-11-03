import http from "./http-common";

class historyLuongAdminService {
    salaryHistory(form) {
        return http.post(`/admin/salary/history`, form);
    }
}

export default new historyLuongAdminService();