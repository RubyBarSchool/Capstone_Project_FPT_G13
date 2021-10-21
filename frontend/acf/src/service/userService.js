import http from "./http-common";

class userService {
    getAllPosition(form) {
        return http.post(`/position`, form);
    }

    addPosition(form) {
        return http.post(`/position/add`, form);
    }
}

export default new userService();