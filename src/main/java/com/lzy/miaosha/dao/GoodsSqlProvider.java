package com.lzy.miaosha.dao;

import com.lzy.miaosha.domain.Address;
import com.lzy.miaosha.domain.Goods;
import com.lzy.miaosha.vo.GoodsVo;
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

    public static String updateMiaoshaGoods(final GoodsVo goods){
        return new SQL() {
            {
                UPDATE("miaosha_goods");
                if(goods.getMiaoshaPrice() != null){
                    SET("miaosha_price=#{miaoshaPrice}");
                }
                if(goods.getStockCount() != null){
                    SET("stock_count=#{stockCount}");
                }
                if(goods.getStartDate() != null){
                    SET("start_date=#{startDate}");
                }
                if(goods.getEndDate() != null){
                    SET("end_date=#{endDate}");
                }
                WHERE("goods_id=#{goodsId}");
            }}.toString();
    }

}
