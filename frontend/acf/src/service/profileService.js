import http from "./http-common";

class profileService {
    getProfile() {
        return http.get(`/admin/employee/profile`);
    }
    changePassword(form) {
        return http.post(`/spadmin/account/changepassword`, form);
    }
}

export default new profileService();