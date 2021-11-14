import http from "./http-common";

class frameAdminService {
    searchFrame(form) {
        return http.post("/admin/framematerial/search", form);
    }
    deleteFrame(id) {
        return http.delete(`/admin/framematerial/delete?id=${id}`);
    }
    addFrame(form) {
        return http.post(`/admin/framematerial/add`, form);
    }

}

export default new frameAdminService();