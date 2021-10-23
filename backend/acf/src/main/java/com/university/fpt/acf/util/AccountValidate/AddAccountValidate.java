package com.university.fpt.acf.util.AccountValidate;

import com.university.fpt.acf.config.security.entity.Account;
import org.springframework.validation.annotation.Validated;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Validated
public class AddAccountValidate {
    //check password
    public boolean checkPassword(String password){
        String regex ="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if(password.matches(regex)){
            return true;
        }
        return false;
    }

    //ham convert VietNamese -> English
    private static String convertEnglish(String str) {
        str = str.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
        str = str.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
        str = str.replaceAll("ì|í|ị|ỉ|ĩ", "i");
        str = str.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
        str = str.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
        str = str.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
        str = str.replaceAll("đ", "d");

        str = str.replaceAll("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A");
        str = str.replaceAll("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E");
        str = str.replaceAll("Ì|Í|Ị|Ỉ|Ĩ", "I");
        str = str.replaceAll("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O");
        str = str.replaceAll("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U");
        str = str.replaceAll("Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y");
        str = str.replaceAll("Đ", "D");
        return str;
    }

    //ham generate username by fullname
    public  String generateFormatUsernameByFullname(String fullname){
        String fullNameEng = this.convertEnglish(fullname);
        String[] list = fullNameEng.split(" ");
        String usernameFormat = list[list.length-1].toLowerCase(Locale.ROOT);
        for(int i =0;i< list.length-1;i++){
                usernameFormat+=list[i].substring(0,1).toLowerCase(Locale.ROOT);
        }
        return usernameFormat;
    }
    //Ham checkNumberUsername
    public Integer genNumberUsername(String username,List<String> listUsername){
        int number =-1;
        for(String us: listUsername){
            number++;
        }
        return number+1;
    }
}
