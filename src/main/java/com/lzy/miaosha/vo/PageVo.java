package com.lzy.miaosha.vo;

import com.lzy.miaosha.domain.Goods;

import java.util.List;

public class PageVo {
    List<Goods> goodsList;
    //一共多少页
    private int count ;
    //当前是第几页，从第一页开始 limit (curPage-1)*pageSize,pageSize
    private int curPage;
    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
