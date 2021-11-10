import http from "./http-common";

class chieuCaoService {
    addUnit(height) {
        return http.post(`/admin/height/add?height=${height}`);
    }
    getAllUnits(form) {
        return http.get(`/admin/height/get`, form);
    }
    deleteUnit(id) {
        return http.delete(`/admin/height/delete?id=${id}`);
    }
}

export default new chieuCaoService();