import http from "./http-common";

class groupCoverPlateService {
    addGroup(name) {
        return http.post(`/admin/groupcoverplate/add?name=${name}`);
    }
    getAllGroupS(form) {
        return http.get(`/admin/groupcoverplate/get`, form);
    }
    deleteGroup(id) {
        return http.delete(`/admin/groupcoverplate/delete?id=${id}`);
    }
}

export default new groupCoverPlateService();