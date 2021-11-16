package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileContactVO {
    private String noteContact;
    private String priceContact = "0";
    private List<FileProductVO> fileProductVOList = new ArrayList<>();
}
