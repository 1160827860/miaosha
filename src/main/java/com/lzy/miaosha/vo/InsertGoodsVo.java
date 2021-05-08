package com.lzy.miaosha.vo;

import javax.validation.constraints.NotNull;

public class InsertGoodsVo {
    @NotNull
    private String name;
    @NotNull
    private String title;
    @NotNull
    private String detail;
    @NotNull
    private Double price;
    @NotNull
    private Integer stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
