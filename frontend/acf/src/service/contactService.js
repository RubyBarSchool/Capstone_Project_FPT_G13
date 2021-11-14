import http from "./http-common";

class contactService {
    importFileContact(form) {
        return http.post(`/admin/contact/readexcel`, form);
    }
    searchAttendance(form) {
        return http.post(`/admin/attendances/search`, form);
    }
    updateAttendance(form) {
        return http.put(`/admin/attendances`, form);
    }
    priviewExcel(form) {
        return http.post(`/admin/attendances/priview`, form);
    }
    downExcel(form) {
        return http.post(`/admin/attendances/down`, form, { responseType: 'blob' });
    }
    preview() {
        return http.get(`/files/anhbuoihoccuoi.png`, { responseType: 'blob' });
    }
}

export default new contactService();