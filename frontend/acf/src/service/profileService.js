import http from "./http-common";

class profileService {
    getProfile() {
        return http.get(`/employee/profile`);
    }

    changePassword(form) {
        return http.post(`/employee/changepassword`, form);
    }

    preview(name) {
        return http.get(`/files/${name}`, { responseType: 'blob' });
    }

    delete(name) {
        return http.delete(`/files/${name}`);
    }

    changeImage(image) {
        return http.get(`/employee/changeimage?image=${image}`);
    }


}

export default new profileService();