import http from "./http-common";

class fileService {
    importContact(file) {
        let formData = new FormData();
        formData.append("file", file);

        return http.post("/admin/contact/readexcel", formData, {
            headers: {
                "Content-Type": "multipart/form-data"
            },
        });
    }
}

export default new fileService();