import http from "./http-common";

class coverPlateService {
    searchCoverSheet(form) {
        return http.post("/admin/coversheet/search", form);
    }
    addUnitCoverSheet(form) {
        return http.post(`/admin/coversheet/addunit`, form);
    }
    updateCoverSheet(data) {
        return http.put(`/admin/coversheet/update`, data);
    }

    getAllFrame(form) {
        return http.post(`/admin/framematerial/getframe`, form);
    }
    getAllFrameHeight() {
        return http.get(`/admin/height/get`);
    }
    deleteCoverSheet(id) {
        return http.delete(`/admin/coversheet/delete?id=${id}`);
    }
    addCoverSheet(form) {
        return http.post(`/admin/coversheet/add`, form);
    }
    listGroupCoverPlate() {
        return http.get("/admin/groupcoverplate/get");
    }
    listUnit() {
        return http.get("/admin/unit/get");
    }
    listCompany(form) {
        return http.post(`/admin/company/search`, form);
    }
    listUnitCoverSheet() {
        return http.get("/admin/unit/getheightcoversheet");
    }
    listFrameCoverSheet() {
        return http.get("/admin/framematerial/getframecoversheet");
    }
    listFrameHeightCoverSheet() {
        return http.get("/admin/height/getheightcoversheet");
    }
    addFrameHeightCoverSheet(form) {
        return http.post(`/admin/coversheet/addframeheight`, form);
    }
    getCoverSheetByUnit(id) {
        return http.post(`/admin/coversheet/getcoversheetbyunit?id=${id}`);
    }
    getUnitByCoverSheet(id) {
        return http.post(`/admin/coversheet/getunitbycoversheet?id=${id}`);
    }
    listCodeCoverSheet() {
        return http.get("/admin/coversheet/getcoversheets");
    }

    getFrameByCoverSheetAndHeight(form) {
        return http.post(`/admin/coversheet/getframebycoversheetandheight`, form);
    }
    getCoverSheetByFrameAndHeight(form) {
        return http.post(`/admin/coversheet/getcoversheetbyframeandheight`, form);
    }
    getHeightsByCoverSheetAndFrame(form) {
        return http.post(`/admin/coversheet/getheightbycoversheetandframe`, form);
    }
    preview(name) {
        return http.get(`/files/${name}`, { responseType: 'blob' });
    }
    deleteImage(name) {
        return http.delete(`/files/${name}`);
    }
}

export default new coverPlateService();