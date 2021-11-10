import http from "./http-common";

class unitService {
    getAllUnits() {
        return http.get(`/admin/unit/get`);
    }
    addUnitAdmin(name) {
        return http.post(`/admin/unit/add?name=${name}`);
    }
    deleteUnitAdmin(id) {
        return http.delete(`/admin/unit/delete?id=${id}`);
    }
}

export default new unitService();