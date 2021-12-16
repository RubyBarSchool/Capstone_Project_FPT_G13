import http from "./http-common";

class viewBonusPunish {
    getBonusAndPunish(form) {
        return http.post(`/employee/salary/bonusandpunish`, form);
    }
    getBonus(form) {
        return http.post(`/employee/salary/bonus`, form);
    }
    getPunish(form) {
        return http.post(`/employee/salary/punish`, form);
    }
}

export default new viewBonusPunish();