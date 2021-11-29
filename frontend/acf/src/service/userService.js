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

}

export default new userService();