import http from "./http-common";

class EmployeeService {
    getAllEmployee(form) {
        return http.post(`/admin/employee`, form);
    }
    getAllEmployeeNotAccount(form) {
        return http.post(`/admin/employee/fullnameEmNotAccount`, form);
    }
}

export default new EmployeeService();