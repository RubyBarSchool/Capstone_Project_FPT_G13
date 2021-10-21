package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchEmployeeVO {
    private Long id;
    private String image;
    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    private String email;
    private Long idPosition;
    private String positionName;
    private Boolean statusDelete;
}
