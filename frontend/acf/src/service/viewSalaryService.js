import http from "./http-common";

class viewSalaryService {
    getSalary(form) {
        return http.post("/employee/salary", form);
    }
    getBonus(form) {
        return http.post(`/employee/salary/bonus`, form);
    }
    getPunish(form) {
        return http.post(`/employee/salary/punish`, form);
    }
}

export default new viewSalaryService();