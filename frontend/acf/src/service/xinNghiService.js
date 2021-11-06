import http from "./http-common";

class xinNghiService {
    searchPersonalLeaveApplication(name) {
        return http.post("/employee/personalLeaveApplication/search", name);
    }
    addPersonalApplication(form) {
        return http.post(`/employee/personalLeaveApplication/add`, form);
    }
    updatePersonalApplication(data) {
        return http.put(`/employee/personalLeaveApplication/update`, data);
    }
    getDetailPersonalLeaveApplicationEmployee(id) {
        return http.get(`/employee/personalLeaveApplication/detail?id=${id}`);
    }
    deletePersonalApplication(id) {
        return http.delete(`/employee/personalLeaveApplication/delete?id=${id}`);
    }
}

export default new xinNghiService();