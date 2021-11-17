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
    getMaterialInAddProduct(form) {
        return http.post(`/admin/material/searchmaterial`, form);
    }
    addProduct(form) {
        return http.post(`/admin/product/addproduct`, form);
    }
    deleteProduct(id) {
        return http.delete(`/admin/product/${id}`);
    }
}

export default new viewDetailContactService();