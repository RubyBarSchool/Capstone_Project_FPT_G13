import http from "./http-common";

class EmployeeService {
    getAllEmployee(form) {
        return http.post(`/spadmin/employee`, form);
    }
}

export default new EmployeeService();