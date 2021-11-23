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
    previewExcel(form) {
        return http.post(`/admin/attendances/preview`, form);
    }
    downExcel(form) {
        return http.post(`/admin/attendances/down`, form, { responseType: 'blob' });
    }
    preview() {
        return http.get(`/files/anhbuoihoccuoi.png`, { responseType: 'blob' });
    }
}

export default new attendanceService();