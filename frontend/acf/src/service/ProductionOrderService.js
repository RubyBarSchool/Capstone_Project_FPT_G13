import http from "./http-common";

class ProductionOrderService {
    viewWorkEmployee(form) {
        return http.post(`/admin/productionorder/viewworkemployee`, form);
    }
    searchProductOrder(form) {
        return http.post(`/admin/productionorder/search`, form);
    }

}

export default new ProductionOrderService();