package com.lzy.miaosha.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class LyhLoginVo {
    private String phone_number;
    @NotNull
    @Length(min =6 ,max =40 )
    private String passWords;
    @NotNull
    @Length(max = 4,min = 4)
    private String checkCode;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassWords() {
        return passWords;
    }

    public void setPassWords(String passWords) {
        this.passWords = passWords;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
