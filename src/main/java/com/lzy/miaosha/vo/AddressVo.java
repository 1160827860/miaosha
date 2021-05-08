package com.lzy.miaosha.vo;

import javax.validation.constraints.NotNull;

/**
 * 收货人地址和信息
 */
public class AddressVo {
    //地址的主键用于修改地址用
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
