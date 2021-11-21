package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.common.entity.ColumnCommon;
import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.Product;
import com.university.fpt.acf.entity.ProductionOrder;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.ProductRepository;
import com.university.fpt.acf.repository.ProductionOrderCustomRepository;
import com.university.fpt.acf.repository.ProductionOrderRepository;
import com.university.fpt.acf.service.ProductionOrderService;
import com.university.fpt.acf.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductionOrderServiceImpl implements ProductionOrderService {

    @Autowired
    private ProductionOrderCustomRepository productionOrderCustomRepository;
    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public HashMap<String, Object> viewWorkEmployee(DateWorkEmployeeFrom dateWorkEmployeeFrom) {
        List<ColumnCommon> listColumn = new ArrayList<>();
        HashMap<String, Object> data = new HashMap<>();
        List<HashMap<String, Object>> mapList = new ArrayList<>();
        data.put("data", mapList);
        try {
            listColumn = generateColumn(dateWorkEmployeeFrom);
            data.put("columns", listColumn);
            List<ProductionOrderViewWorkVO> productionOrderViewWorkVOS = productionOrderCustomRepository.getListWorkEmployee(dateWorkEmployeeFrom);
            System.out.println(productionOrderViewWorkVOS.toString());
            HashMap<String, Object> dataEmployee = new HashMap<>();
            dataEmployee.put("id", 0);
            int count = 0;
            HashMap<String, Object> stringObjectHashMap = defaultData(dateWorkEmployeeFrom);
            for (ProductionOrderViewWorkVO productionOrderViewWorkVO : productionOrderViewWorkVOS) {
                Boolean checkNewEmpl = false;
                if (dataEmployee.get("id") != null && !productionOrderViewWorkVO.getIdEmployee().equals(Long.parseLong(dataEmployee.get("id").toString()))) {
                    checkNewEmpl = true;
                    dataEmployee.put("average", (double) Math.round((count * 1.0 / stringObjectHashMap.size()) * 10) / 10);
                    dataEmployee = new HashMap<>();
                    mapList.add(dataEmployee);
                    dataEmployee.put("id", productionOrderViewWorkVO.getIdEmployee());
                    dataEmployee.put("name", productionOrderViewWorkVO.getNameEmployee());
                    dataEmployee.put("idProductionOrder", productionOrderViewWorkVO.getIdProductionOrder());
                    dataEmployee.put("nameProductionOrder", productionOrderViewWorkVO.getNameProductionOrder());
                    count = 0;
                    dataEmployee.putAll(stringObjectHashMap);
                }
                if (productionOrderViewWorkVO.getDateStart() == null || productionOrderViewWorkVO.getDateEnd() == null) {
                    continue;
                }
                if(!checkNewEmpl){
                    String idPro = dataEmployee.get("idProductionOrder").toString();
                    String namePro = dataEmployee.get("nameProductionOrder").toString();
                    dataEmployee.put("idProductionOrder", idPro +","+ productionOrderViewWorkVO.getIdProductionOrder());
                    dataEmployee.put("nameProductionOrder",namePro+","+ productionOrderViewWorkVO.getNameProductionOrder());
                }
                if (productionOrderViewWorkVO.getDateStart().isBefore(dateWorkEmployeeFrom.getDateStart())) {
                    productionOrderViewWorkVO.setDateStart(dateWorkEmployeeFrom.getDateStart());
                }

                if (productionOrderViewWorkVO.getDateEnd().isAfter(dateWorkEmployeeFrom.getDateEnd())) {
                    productionOrderViewWorkVO.setDateEnd(dateWorkEmployeeFrom.getDateEnd());
                }

                LocalDate localDate = productionOrderViewWorkVO.getDateStart();
                Boolean checkExitDate = false;
                while (localDate.isBefore(productionOrderViewWorkVO.getDateEnd())) {
                    checkExitDate = true;
                    count++;
                    Integer value = Integer.parseInt(dataEmployee.get(localDate.toString()).toString()) + 1;
                    dataEmployee.put(localDate.toString(), value);
                    localDate = localDate.plusDays(1);
                }
                if (checkExitDate || productionOrderViewWorkVO.getDateStart().isEqual(productionOrderViewWorkVO.getDateEnd())) {
                    count++;
                    Integer value = Integer.parseInt(dataEmployee.get(localDate.toString()).toString()) + 1;
                    dataEmployee.put(localDate.toString(), value);
                }
            }
            dataEmployee.put("average", (double) Math.round((count * 1.0 / stringObjectHashMap.size()) * 10) / 10);


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return data;
    }

    @Override
    public List<SearchProductionOrderVO> searchProductionOrder(SearchProductionOrderForm searchForm) {
        List<SearchProductionOrderVO> list = new ArrayList<>();
        try {
            list = productionOrderCustomRepository.searchProductOrder(searchForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearchProductionOrder(SearchProductionOrderForm searchForm) {
        int size = 0;
        try {
            size = productionOrderCustomRepository.totalSearchProductOrder(searchForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return size;
    }

    @Override
    public List<ViewWorkVO> searchProductionOrderEmployee(SearchWorkEmployeeForm searchWorkEmployeeForm) {
        List<ViewWorkVO> list = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            list = productionOrderCustomRepository.searchProductOrderEmployee(searchWorkEmployeeForm,accountSercurity.getUserName());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearchProductionOrderEmployee(SearchWorkEmployeeForm searchWorkEmployeeForm) {
        if (searchWorkEmployeeForm.getTotal() != null && searchWorkEmployeeForm.getTotal() != 0) {
            return searchWorkEmployeeForm.getTotal().intValue();
        }
        int total = 0;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            total = productionOrderCustomRepository.totalSearchProductOrderEmployee(searchWorkEmployeeForm, accountSercurity.getUserName());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }

    @Override
    public List<ViewWorkDetailVO> searchProductionOrderDetailEmployee(Long id) {
        List<ViewWorkDetailVO> list = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            list = productionOrderCustomRepository.searchProductOrderDetailEmployee(accountSercurity.getUserName(),id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    @Override
    public Boolean addProductionOrder(AddProductionOrderFrom addProductionOrderFrom) {
        Boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            ProductionOrder productionOrder = new ProductionOrder();
            productionOrder.setCreated_by(accountSercurity.getUserName());
            productionOrder.setModified_by(accountSercurity.getUserName());
            productionOrder.setName(addProductionOrderFrom.getName());
            Product product = productRepository.getProductByID(addProductionOrderFrom.getIdProduct());
            product.setStatus("Đang làm");
            product = productRepository.saveAndFlush(product);
            productionOrder.setProducts(product);
            productionOrder.setDateStart(addProductionOrderFrom.getDateStart());
            productionOrder.setDateEnd(addProductionOrderFrom.getDateEnd());
            productionOrder.setNumberFinish("0/"+product.getCount());
            List<Employee> employees = new ArrayList<>();
            for(Long idEmpl : addProductionOrderFrom.getIdEmployees()){
                Employee employee = new Employee();
                employee.setId(idEmpl);
                employees.add(employee);
            }
            productionOrder.setEmployees(employees);
            productionOrderRepository.saveAndFlush(productionOrder);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean updateProductionOrder(AddProductionOrderFrom addProductionOrderFrom) {
        Boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            ProductionOrder productionOrder = productionOrderRepository.getById(addProductionOrderFrom.getId());
            productionOrder.setModified_by(accountSercurity.getUserName());
            productionOrder.setModified_date(LocalDate.now());
            productionOrder.setName(addProductionOrderFrom.getName());
            productionOrder.setDateStart(addProductionOrderFrom.getDateStart());
            productionOrder.setDateEnd(addProductionOrderFrom.getDateEnd());
            List<Employee> employees = new ArrayList<>();
            for(Long idEmpl : addProductionOrderFrom.getIdEmployees()){
                Employee employee = new Employee();
                employee.setId(idEmpl);
                employees.add(employee);
            }
            productionOrder.setEmployees(employees);
            productionOrderRepository.saveAndFlush(productionOrder);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean confirmWork(Long id) {
        Boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            ProductionOrder productionOrder = productionOrderRepository.getProductionOrderByID(id);
            productionOrder.setModified_by(accountSercurity.getUserName());
            productionOrder.setModified_date(LocalDate.now());
            productionOrder.setStatus(0);
            productionOrderRepository.saveAndFlush(productionOrder);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean confirmWorkDone(Long id) {
        Boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            ProductionOrder productionOrder = productionOrderRepository.getProductionOrderByID(id);
            productionOrder.setModified_by(accountSercurity.getUserName());
            productionOrder.setModified_date(LocalDate.now());
            productionOrder.setStatus(1);
            productionOrderRepository.saveAndFlush(productionOrder);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean updateWork(UpdateWorkEmployeeFrom updateWorkEmployeeFrom) {
        Boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            ProductionOrder productionOrder = productionOrderRepository.getProductionOrderByID(updateWorkEmployeeFrom.getId());
            productionOrder.setModified_by(accountSercurity.getUserName());
            productionOrder.setModified_date(LocalDate.now());
            String[] numberfinish = productionOrder.getNumberFinish().split("/");
            Integer numberProduct = Integer.parseInt(numberfinish[1]);
            if(numberProduct.intValue() == updateWorkEmployeeFrom.getNumber().intValue()){
                productionOrder.setStatus(-2);
            }
            productionOrder.setNumberFinish(updateWorkEmployeeFrom.getNumber()+"/"+numberProduct);
            productionOrderRepository.saveAndFlush(productionOrder);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean deleteProductionOrder(Long id) {
        Boolean check = false;
        try {
            productionOrderRepository.deleteById(id);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public List<ProductionOrderDetailVO> getDetailProduction(Long idProduction) {
        List<ProductionOrderDetailVO> productionOrderDetailVOS = new ArrayList<>();
        try {
            productionOrderDetailVOS = productionOrderRepository.getProductionOrder(idProduction);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return productionOrderDetailVOS;
    }

    private HashMap<String, Object> defaultData(DateWorkEmployeeFrom dateWorkEmployeeFrom) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        LocalDate localDate = dateWorkEmployeeFrom.getDateStart();
        while (localDate.isBefore(dateWorkEmployeeFrom.getDateEnd())) {
            stringObjectHashMap.put(localDate.toString(), 0);
            localDate = localDate.plusDays(1);
        }
        stringObjectHashMap.put(localDate.toString(), 0);
        return stringObjectHashMap;
    }

    private List<ColumnCommon> generateColumn(DateWorkEmployeeFrom dateWorkEmployeeFrom) {
        List<ColumnCommon> listColumn = new ArrayList<>();

        ColumnCommon columnCommon = new ColumnCommon();
        columnCommon.setTitle("STT");
        columnCommon.setDataIndex("id");
        columnCommon.setKey("id");
        columnCommon.setWidth(80);
        columnCommon.setFixed("left");
        listColumn.add(columnCommon);

        ColumnCommon columnCommon1 = new ColumnCommon();
        columnCommon1.setTitle("Tên nhân viên");
        columnCommon1.setDataIndex("name");
        columnCommon1.setKey("name");
        columnCommon1.setWidth(150);
        listColumn.add(columnCommon1);

        ColumnCommon columnCommon2 = new ColumnCommon();
        columnCommon2.setTitle("Trung Bình");
        columnCommon2.setDataIndex("average");
        columnCommon2.setKey("average");
        columnCommon2.setWidth(150);
        columnCommon2.getScopedSlots().setCustomRender("average");
        listColumn.add(columnCommon2);

        LocalDate localDate = dateWorkEmployeeFrom.getDateStart();
        while (localDate.isBefore(dateWorkEmployeeFrom.getDateEnd())) {
            ColumnCommon columnCommonDate = new ColumnCommon();
            columnCommonDate.setTitle(localDate.getDayOfMonth() + "-" + localDate.getMonthValue() + "-" + localDate.getYear());
            columnCommonDate.setDataIndex(localDate.toString());
            columnCommonDate.setKey(localDate.toString());
            columnCommonDate.setWidth(150);
            listColumn.add(columnCommonDate);
            localDate = localDate.plusDays(1);
        }
        ColumnCommon columnCommonDateEnd = new ColumnCommon();
        columnCommonDateEnd.setTitle(localDate.getDayOfMonth() + "-" + localDate.getMonthValue() + "-" + localDate.getYear());
        columnCommonDateEnd.setDataIndex(localDate.toString());
        columnCommonDateEnd.setKey(localDate.toString());
        columnCommonDateEnd.setWidth(150);
        listColumn.add(columnCommonDateEnd);

        return listColumn;
    }
}
