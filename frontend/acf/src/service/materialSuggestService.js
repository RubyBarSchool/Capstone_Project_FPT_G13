import http from "./http-common";

class materialSuggestService {
    searchSuggestMaterial(form) {
        return http.post("/admin/materialsuggest/search", form);
    }
}

export default new materialSuggestService();