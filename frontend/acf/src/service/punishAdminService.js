import http from "./http-common";

class punishAdminService {
    searchPunishAdmin(form) {
        return http.post("admin/punish/search", form);
    }
    // getAllEmployee(form) {
    //     return http.post("/admin/employee/search", form);
    // }
    // addThuongAdmin(form) {
    //     return http.post(`/admin/bonus/add`, form);
    // }
    // updateThuongAdmin(data) {
    //     return http.put(`/admin/bonus/update`, data);
    // }
    // getAdvanceSalaryEmployeeByID(id) {
    //     return http.get(`/employee/advanceSalary/detail?id=${id}`);
    // }
    // deleteThuongAdmin(id) {
    //     return http.delete(`/admin/bonus/delete?id=${id}`);
    // }
}

export default new punishAdminService();