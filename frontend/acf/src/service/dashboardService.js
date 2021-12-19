import http from "./http-common";

class dashboardService {
    getNumberEmployeeHaveNotAccount() {
        return http.post("/dashboard/spadmin/getnumberemployeehavenotaccount");
    }
    countDashboardAdmin() {
        return http.post("/dashboard/admin/countdashboardadmin");
    }
    countDashboardEmployee() {
        return http.post("/dashboard/employee/countdashboardemployee");
    }

    reportContact() {
        return http.post("/dashboard/admin/reportContact");
    }

    topEmployee() {
        return http.post("/dashboard/admin/topemployee");
    }
}

export default new dashboardService();