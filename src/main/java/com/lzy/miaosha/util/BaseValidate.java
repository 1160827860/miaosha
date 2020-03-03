package com.lzy.miaosha.util;

import java.io.Serializable;

public class BaseValidate implements Serializable {
    public static final long serialVersionUID = 1L;
    //base64的值
    private String Base64Str;
    //验证码的值
    private String value;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBase64Str() {
        return Base64Str;
    }

    public void setBase64Str(String base64Str) {
        Base64Str = base64Str;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
