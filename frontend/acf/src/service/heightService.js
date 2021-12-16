import http from "./http-common";

class heightService {
    addFrameHeight(frameHeight) {
        return http.post(`/admin/height/add?frameHeight=${frameHeight}`);
    }
    getAllFrameHeight(form) {
        return http.get(`/admin/height/get`, form);
    }
    deleteFrameHeight(id) {
        return http.delete(`/admin/height/delete?id=${id}`);
    }
}

export default new heightService();