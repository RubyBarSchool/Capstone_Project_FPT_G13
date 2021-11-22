package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrder extends EntityCommon {

    private String name;

    private LocalDate dateStart;

    private LocalDate dateEnd;

    private Integer status = -1;

     private String numberFinish;

    @ManyToMany()
    @JoinTable(name = "production_oder_employee",
    joinColumns = @JoinColumn(name = "production_oder_id"),inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Collection<Employee> employees;

    @OneToOne()
    @JoinColumn(name = "product_id")
    private Product products;

}
