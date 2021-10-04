package com.university.fpt.acf.util.AccountValidate;

import com.university.fpt.acf.config.security.entity.Account;
import org.springframework.validation.annotation.Validated;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Validated
public class AddAccountValidate {
    // 1. check tồn tại
    public boolean checkExitAccount(String username, List<String> listUsername){
        for(String us : listUsername){
            if(us.equals(username)){
                return true;
            }
        }
        return false;

    }
    //2. check password
    public boolean checkPassword(String password){
        String regex ="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if(password.matches(regex)){
            return true;
        }
        return false;
    }
    //2. ham convert VietNamese -> English
    public String convertEnglish(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    //3. ham generate
    public  String generateFormatUsernameByFullname(String fullname){
        String[] list = fullname.split(" ");
        String usernameFormat = list[list.length-1];
        for(int i =0;i< list.length-2;i++){
                usernameFormat+=list[i].substring(0,1).toLowerCase(Locale.ROOT);
        }
        return usernameFormat;
    }
    //4. Ham checkNumberUsername
    public Integer genNumberUsername(String username,List<String> listUsername){
        int number =0;
        for(String us: listUsername){
            if(username.contains(us)){
                number++;
            }
        }
        return number+1;
    }
}
