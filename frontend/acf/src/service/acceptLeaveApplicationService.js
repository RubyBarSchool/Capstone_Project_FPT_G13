import http from "./http-common";

class acceptLeaveApplicationService {
    acceptPersonalApplication(form) {
        return http.put(`/admin/acceptPersonalLeaveApplication/accept`, form);
    }
    searchPersonalLeaveApplication(form) {
        return http.post(`/admin/acceptPersonalLeaveApplication/search`, form);
    }
    rejectPersonalApplication(form) {
        return http.put(`/admin/acceptPersonalLeaveApplication/reject`, form);
    }
}

export default new acceptLeaveApplicationService();