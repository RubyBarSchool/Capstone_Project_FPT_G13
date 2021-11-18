import http from "./http-common";

class contactService {
    submitContact(form) {
        return http.post(`/admin/contact`, form);
    }
    searchCompany(form) {
        return http.post(`/admin/contact/search`, form);
    }
}

export default new contactService();