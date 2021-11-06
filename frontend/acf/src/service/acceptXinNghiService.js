import http from "./http-common";

class acceptXinNghiService {
    acceptPersonalApplication(form) {
        return http.post(`/admin/acceptPersonalLeaveApplication/accept`, form);
    }
    getDetailPersonalLeaveApplicationAdmin(id) {
        return http.get(`/admin/acceptPersonalLeaveApplication/detail?id=${id}`);
    }
    searchPersonalLeaveApplication(form) {
        return http.post(`/admin/acceptPersonalLeaveApplication/search`, form);
    }
}

export default new acceptXinNghiService();