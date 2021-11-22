import http from "./http-common";

class productService {
    getProductInContact(id) {
        return http.get(`/admin/product/getproductincontact?idcontact=${id}`);
    }

    getProductInContactAll(id) {
        return http.get(`/admin/product/getproductincontactall?idcontact=${id}`);
    }
}

export default new productService();