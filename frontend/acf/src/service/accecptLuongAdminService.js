import http from "./http-common";

class accecptLuongAdminService {
    salaryAccept(form) {
        return http.post(`/admin/salary/accept`, form);
    }
    salaryAcceptDone(id) {
        return http.put(`/admin/salary/accept/${id}`);
    }
}

export default new accecptLuongAdminService();