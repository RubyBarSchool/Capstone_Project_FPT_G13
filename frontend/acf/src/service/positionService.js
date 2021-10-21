import http from "./http-common";

class PositionService {
    getAllPosition(form) {
        return http.post(`/admin/position`, form);
    }
    addPosition(form) {
        return http.post(`/admin/position/add`, form);
    }
    deletePosition(id) {
        return http.delete(`/admin/position/delete?id=${id}`);
    }
    updatePosition(data) {
        return http.put(`/admin/position/update`, data);
    }
}

export default new PositionService();