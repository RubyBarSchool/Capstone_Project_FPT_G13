import http from "./http-common";

class viewDetailContactService {
    searchDetailContact(form) {
        return http.post("/admin/contactdetail", form);
    }
    searchContactInSearch(form) {
        return http.post("/admin/contactdetail/getContactInSearch", form);
    }
}

export default new viewDetailContactService();