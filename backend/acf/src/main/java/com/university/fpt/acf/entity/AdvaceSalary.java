package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvaceSalary extends EntityCommon {
    private String title;
    private String advaceSalary;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String comment;
    private Boolean accept = false;
    private Long idEmployeeAccept;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;
}
