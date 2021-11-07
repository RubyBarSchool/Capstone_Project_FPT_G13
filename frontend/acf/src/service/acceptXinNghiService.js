import http from "./http-common";

class acceptXinNghiService {
    acceptPersonalApplication(form) {
        return http.post(`/admin/acceptPersonalLeaveApplication/accept`, form);
    }
    searchPersonalLeaveApplication(form) {
        return http.post(`/admin/acceptPersonalLeaveApplication/search`, form);
    }
    rejectPersonalApplication(form) {
        return http.post(`/admin/acceptPersonalLeaveApplication/reject`, form);
    }
}

export default new acceptXinNghiService();