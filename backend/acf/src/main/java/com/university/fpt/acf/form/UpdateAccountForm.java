package com.university.fpt.acf.form;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class UpdateAccountForm {
    private Long idAccount;
    private Boolean status;
    private Collection<Long> idRole = new ArrayList<>();

}
