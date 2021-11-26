package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMoney extends EntityCommon {
    private LocalDate date = LocalDate.now();
    private String money;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Contact contact;
}
