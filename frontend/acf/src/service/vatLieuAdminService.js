import http from "./http-common";

class vatLieuAdminService {
    searchMaterial(form) {
        return http.post("/admin/material/search", form);
    }
    addMaterial(form) {
        return http.post(`/admin/material/add`, form);
    }
    addFrameHeightMaterial(form) {
        return http.post(`/admin/material/addframeheight`, form);
    }
    addUnitMaterial(form) {
        return http.post(`/admin/material/addunit`, form);
    }
    updateMaterial(data) {
        return http.put(`/admin/material/update`, data);
    }
    deleteMaterial(id) {
        return http.delete(`/admin/material/delete?id=${id}`);
    }
    getAllFrame(form) {
        return http.post(`/admin/framematerial/getframe`, form);
    }
    getUnitByMaterial(id) {
        return http.post(`/admin/material/getunitbymaterial?id=${id}`);
    }
    searchMaterialByUnit(unit) {
        return http.post(`/admin/material/getmaterialbyunit?unit=${unit}`);
    }
    getHeightsByMaterialAndFrame(form) {
        return http.post(`/admin/material/getheightbymaterialandframe`, form);
    }
}

export default new vatLieuAdminService();