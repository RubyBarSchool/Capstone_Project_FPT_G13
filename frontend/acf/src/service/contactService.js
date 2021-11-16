import http from "./http-common";

class contactService {
    submitContact(form) {
        return http.post(`/admin/contact`, form);
    }
}

export default new contactService();