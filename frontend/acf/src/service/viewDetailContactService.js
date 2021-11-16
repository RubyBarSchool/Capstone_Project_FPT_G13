import http from "./http-common";

class viewDetailContactService {
    searchDetailContact(form) {
        return http.post(`/admin/contactdetail`, form);
    }
    searchContactInSearch(form) {
        return http.post(`/admin/contactdetail/getcontactinsearch`, form);
    }
    searchContactInAdd() {
        return http.post(`/admin/contactdetail/getcontactinadd`);
    }
    getMaterialOfProduct(id) {
        return http.get(`/admin/contactdetail/getmaterial/${id}`);
    }
}

export default new viewDetailContactService();