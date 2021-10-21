import http from "./http-common";

class PositionService {
    getAllPosition(form) {
        return http.post(`/api/position`, form);
    }
}

export default new PositionService();