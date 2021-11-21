import http from "./http-common";

class viewWorkEmployeeService {
    searchWorkEmployee(form) {
        return http.post(`/employee/viewwork/search`, form);
    }
    searchMaterialInWork(id) {
        return http.post(`/employee/viewwork/${id}`);
    }
    confirmWork(id) {
        return http.post(`/employee/viewwork/confirm/${id}`);
    }
    updateWorkEmployee(form) {
        return http.put(`/employee/viewwork`, form);
    }
}

export default new viewWorkEmployeeService();