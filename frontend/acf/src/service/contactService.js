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
    updateContact(data) {
        return http.put(`/admin/contact`, data);
    }
    deleteContact(id) {
        return http.delete(`/admin/contact?id=${id}`);
    }
    exportContact(idContact) {
        let data = {
            id: idContact
        };
        return http.post(`/admin/contact/exportcontact`, data, { responseType: 'blob' });
    }
    templateContact() {
        let data = {
            id: 0
        };
        return http.post(`/admin/contact/templatecontact`, data, { responseType: 'blob' });
    }
}

export default new contactService();