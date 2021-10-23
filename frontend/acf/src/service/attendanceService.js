import http from "./http-common";

class attendanceService {
    getEmployee(form) {
        return http.post("/admin/attendances/getemployee", form);
    }
    addAttendance(form) {
        return http.post(`/admin/attendances`, form);
    }
    searchAttendance(form) {
        return http.post(`/admin/attendances/search`, form);
    }
    updateAttendance(form) {
        return http.put(`/admin/attendances`, form);
    }
}

export default new attendanceService();