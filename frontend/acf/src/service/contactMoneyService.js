import http from "./http-common";

class contactMoneyService {
    searchContactMoney(form) {
        return http.post(`/admin/contactmoney/search`, form);
    }
    addContactMoney(form) {
        return http.post(`/admin/contactmoney`, form);
    }
    updateContactMoney(form) {
        return http.put(`/admin/contactmoney`, form);
    }
    deleteContactMoney(id) {
        return http.delete(`/admin/contactmoney/${id}`);
    }
    confirmContactMoney(form) {
        return http.post(`/admin/contactmoney/confirm`, form);
    }
}

export default new contactMoneyService();