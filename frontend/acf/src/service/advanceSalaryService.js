import http from "./http-common";

class advanceSalaryService {
    searchAdvanceSalaryEmployee(name) {
        return http.post("/employee/advanceSalary/search", name);
    }
    addAdvanceSalaryEmployee(form) {
        return http.post(`/employee/advanceSalary/add`, form);
    }
    updateAdvanceSalaryEmployee(data) {
            return http.put(`/employee/advanceSalary/update`, data);
        }
        // getAdvanceSalaryEmployeeByID(id) {
        //     return http.get(`/employee/advanceSalary/detail?id=${id}`);
        // }
    deleteAdvanceSalaryEmployee(id) {
        return http.delete(`/employee/advanceSalary/delete?id=${id}`);
    }
}

export default new advanceSalaryService();