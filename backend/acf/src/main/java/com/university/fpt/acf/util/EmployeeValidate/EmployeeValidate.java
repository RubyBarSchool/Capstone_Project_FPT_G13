package com.university.fpt.acf.util.EmployeeValidate;

import org.springframework.validation.annotation.Validated;

@Validated
public class EmployeeValidate {
    //check form phone
    public  boolean checkFormPhone(String phone){
        String regex ="^[0-9]{10}$";
        if(phone.matches(regex)){
            return true;
        }
        return false;
    }
    // check form email
    public  boolean checkFormEmail(String email){
//        String regexEmail ="^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
        String regexEmail ="^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        if(email.matches(regexEmail)){
            return true;
        }
        return false;
    }
}
