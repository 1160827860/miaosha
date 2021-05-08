package com.lzy.miaosha.vo;

import javax.validation.constraints.NotNull;

public class ShopVo {
    //店铺名称
    @NotNull
    private String name;
    //店铺介绍
    @NotNull
    private String info;
    @NotNull
    private String peopleName;
    @NotNull
    private String peopleNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }
}
