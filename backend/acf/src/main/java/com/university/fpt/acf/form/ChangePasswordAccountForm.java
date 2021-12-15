package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordAccountForm {
    @NotNull(message = "Old password cannot be null")
    private String oldPassword;
    @NotNull(message = "New password cannot be null")
    private String newPassword;
}
