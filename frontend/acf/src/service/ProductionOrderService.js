import http from "./http-common";

class ProductionOrderService {
    viewWorkEmployee(form) {
        return http.post(`/admin/productionorder/viewworkemployee`, form);
    }
    searchProductOrder(form) {
        return http.post(`/admin/productionorder/search`, form);
    }
    getDetailProduction(id) {
        return http.get(`/admin/productionorder/getdetailproductionorder?idproduction=${id}`);
    }
}

export default new ProductionOrderService();