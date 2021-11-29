import http from "./http-common";

class userService {
    getAllPosition(form) {
        return http.post(`/admin/position`, form);
    }
    searchUser(name) {
        return http.post("/admin/employee/search", name);
    }
    addUser(form) {
        return http.post(`/admin/employee/add`, form);
    }
    updateUser(data) {
        return http.put(`/admin/employee/update`, data);
    }
    getUserByID(id) {
        return http.get(`/admin/employee/getEmployee?id=${id}`);
    }
    deleteUser(id) {
        return http.delete(`/admin/employee/delete?id=${id}`);
    }
    uploadImage(file) {
        let formData = new FormData();

        formData.append("file", file);

        return http.post("/admin/employee/image", formData, {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        });
    }
    preview(name) {
        return http.get(`/files/${name}`, { responseType: 'blob' });
    }

    deleteImage(name) {
        return http.delete(`/files/${name}`);
    }
}

export default new userService();