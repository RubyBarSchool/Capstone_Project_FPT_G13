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
public class PersonaLeaveApplication extends EntityCommon {
    private String title;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String fileAttach;

    private String accept = "-1";
    private String comment;
    private Long idEmployeeAccept;
    private LocalDate dateAccept;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;
}
