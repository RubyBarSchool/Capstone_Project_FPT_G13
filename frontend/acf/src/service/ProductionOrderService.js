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
    addOrUpdateProductOrder(form) {
        return http.post(`/admin/productionorder/add`, form);
    }
    updateProductOrder(form) {
        return http.post(`/admin/productionorder/update`, form);
    }
    deleteProductOrder(id) {
        return http.delete(`/admin/productionorder/${id}`);
    }
    confirmProductionOrder(id) {
        return http.post(`/admin/productionorder/confirm/${id}`);
    }
}

export default new ProductionOrderService();