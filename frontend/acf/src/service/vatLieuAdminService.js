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
    getAllFrameMaterial() {
        return http.get("/admin/framematerial/getframematerial");
    }
    getAllFrame(form) {
        return http.post("/admin/framematerial/getframe", form);
    }
    getAllUnitsMaterialToInset() {
        return http.get("/admin/unit/getunitsmaterial");
    }
    getAllFrameHeightMaterialToInset() {
        return http.get("/admin/height/getheightmaterial");
    }
}

export default new vatLieuAdminService();