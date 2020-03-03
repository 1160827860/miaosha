package com.lzy.miaosha.vo;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * 登陆抽象的对象
 * @author 李正阳
 * @date 2020/2/27
 * @version 0.1
 */
public class LoginVo {
    @NotNull
    @Length(min =11 ,max =14 )
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

    @Override
    public String toString() {
        return "LoginVo{" +
                "phone_number='" + phone_number + '\'' +
                ", passWords='" + passWords + '\''+  "登陆时间"+new Date();
    }
}
