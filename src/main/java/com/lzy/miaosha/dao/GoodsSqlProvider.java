package com.lzy.miaosha.dao;

import com.lzy.miaosha.domain.Address;
import com.lzy.miaosha.domain.Goods;
import org.apache.ibatis.jdbc.SQL;

public class GoodsSqlProvider {

    public static String updateGoodsByIdAndName(final Goods goods){
        return new SQL() {
            {
                UPDATE("goods");
                if(goods.getGoodsImg() != null){
                    SET("goods_img=#{goodsImg}");
                }
                WHERE("shop_id=#{shopId} and goods_name =#{goodsName}");
            }}.toString();
    }

    public static String updateGoods(final Goods goods){
        return new SQL() {
            {
                UPDATE("goods");
                if(goods.getGoodsImg() != null){
                    SET("goods_img=#{goodsImg}");
                }
                if(goods.getGoodsName() != null){
                    SET("goods_name=#{goodsName}");
                }
                if(goods.getGoodsTitle() != null){
                    SET("goods_title=#{goodsTitle}");
                }
                if(goods.getGoodsDetail() != null){
                    SET("goods_detail=#{goodsDetail}");
                }
                if(goods.getGoodsPrice() != null){
                    SET("goods_price=#{goodsPrice}");
                }
                if(goods.getGoodsStock() != null){
                    SET("goods_stock=#{goodsStock}");
                }
                if(goods.getStatus() != null){
                    SET("status=#{status}");
                }
                WHERE("id=#{id}");
            }}.toString();
    }
}
