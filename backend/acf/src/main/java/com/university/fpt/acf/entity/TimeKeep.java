package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeKeep  extends EntityCommon {

    private LocalDate date;
    private String type;
    private String note;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;

}
