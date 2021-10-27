import http from "./http-common";

class userService {
    getAllColor(form) {
        return http.post(`/admin/color/search`, form);
    }
    addColor(form) {
        return http.post(`/admin/color/add`, form);
    }
    updateColor(data) {
        return http.put(`/admin/color/update`, data);
    }
    deleteColor(id) {
        return http.delete(`/admin/color/delete?id=${id}`);
    }
    getAllCompany(form) {
        return http.post(`/admin/company/search`, form);
    }

}

export default new userService();