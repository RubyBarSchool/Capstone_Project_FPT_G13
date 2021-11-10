import http from "./http-common";

class groupMaterialService {
    addGroupMaterial(name) {
        return http.post(`/admin/groupmaterial/add?name=${name}`);
    }
    getAllGroupMaterial(form) {
        return http.get(`/admin/groupmaterial/get`, form);
    }
    deleteGroupMaterial(id) {
        return http.delete(`/admin/groupmaterial/delete?id=${id}`);
    }
}

export default new groupMaterialService();