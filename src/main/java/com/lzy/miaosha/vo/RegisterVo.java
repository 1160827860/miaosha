package com.lzy.miaosha.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 注册抽象的对象
 * @author 李正阳
 * @date 2020/2/27
 * @version 0.1
 */
public class RegisterVo {
    @NotNull
    @Length(min =11 ,max =14 )
    private String phone_number;
    @NotNull
    @Length(max = 15,min = 6)
    private String passWords;
    @NotNull
    private String nickName;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
