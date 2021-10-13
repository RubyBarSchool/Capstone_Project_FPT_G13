import http from "./http-common";

class EmployeeService {
    getAllEmployee(form) {
        return http.post(`/spadmin/employee`, form);
    }
    getAllEmployeeNotAccount(form) {
        return http.post(`/spadmin/employee/fullnameEmNotAccount`, form);
    }
}

export default new EmployeeService();