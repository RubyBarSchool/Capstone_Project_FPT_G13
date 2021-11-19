import http from "./http-common";

class contactService {
    submitContact(form) {
        return http.post(`/admin/contact`, form);
    }
    searchCompany(form) {
        return http.post(`/admin/contact/search`, form);
    }
    searchContact() {
        return http.post(`/admin/contact/getcontact`);
    }
}

export default new contactService();