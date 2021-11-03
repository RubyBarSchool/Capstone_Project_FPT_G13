import http from "./http-common";

class acceptXinNghiService {
    acceptPersonalApplication() {
        return http.post(`/admin/acceptPersonalLeaveApplication/accept`);
    }
    getDetailPersonalLeaveApplicationAdmin(id) {
        return http.get(`/admin/acceptPersonalLeaveApplication/detail?id=${id}`);
    }
    searchPersonalLeaveApplication(form) {
        return http.post(`/admin/acceptPersonalLeaveApplication/search`, form);
    }
}

export default new acceptXinNghiService();