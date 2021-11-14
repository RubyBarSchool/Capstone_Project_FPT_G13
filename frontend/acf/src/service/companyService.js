import http from "./http-common";

class companyService {
    searchCompany(form) {
        return http.post(`/admin/company/search`, form);
    }
    addCompany(form) {
        return http.post(`/admin/company/add`, form);
    }
    updateCompany(data) {
        return http.put(`/admin/company/update`, data);
    }
    deleteCompany(id) {
        return http.delete(`/admin/company/delete?id=${id}`);
    }

}

export default new companyService();