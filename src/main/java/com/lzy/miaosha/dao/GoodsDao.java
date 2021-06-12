package com.lzy.miaosha.dao;

import com.lzy.miaosha.domain.*;
import com.lzy.miaosha.vo.GoodsVo;
import com.lzy.miaosha.vo.OrderVo;
import com.lzy.miaosha.vo.ShopingCartVo;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id ")
    List<GoodsVo> listGoods();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(Integer goodsId);

    @Insert("insert into goods(goods_name,goods_title,goods_detail,goods_price,goods_stock,shop_id,shop_name) values(#{goodsName},#{goodsTitle},#{goodsDetail},#{goodsPrice},#{goodsStock},#{shopId},#{shopName}) ")
    void insertGoods(Goods goods);

    @Insert("insert into shoping_cart(user_id,goods_id,address_id,number) values(#{userId},#{goodsId},#{addressId},#{number}) ")
    void insertGoodsToCart(@Param("userId") int userId,@Param("goodsId")int goodsId,@Param("addressId")int addressId,@Param("number")int number);

    @UpdateProvider(type = GoodsSqlProvider.class,method = "updateGoodsByIdAndName")
    void updateGoodsByIdAndName(Goods goods);

    @Select("select * from goods where  shop_id=#{id} and status != 1 ")
    List<Goods> showMyGoods(Shop shop);


    @Select("select * from goods where  shop_id=#{id} and status = 1 ")
    List<Goods> showMyDelGoods(Shop shop);

    //方法废弃，已经改为软删除
    @Delete("delete from goods where id=#{id}")
    void delGoods(int id);

    @UpdateProvider(type = GoodsSqlProvider.class,method = "updateGoods")
    void updateGoods(Goods goods);

    @Select("select * from goods where status != 1")
    List<Goods> getAllGoods();

    @Select("select * from goods")
    List<Goods> getAllGoodsByRoot();

    @Update("update goods set goods_img = #{goodsImg} where shop_id = #{shopId} and goods_name = #{goodsName}")
    void updateGoodsPic(Goods goods);

    @Select("select * from goods where id = #{id}")
    Goods getGoodsById(Integer id);

    @Insert("insert into order_info(id,user_id,goods_id,delivery_add_id,goods_name,goods_count,goods_price,status,create_date,pay_date) values(#{id},#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{status},#{createDate},#{payDate})")
    void createOrder(OrderInfo orderInfo);

    @Select("select oi.*,ad.name as name,ad.user_address as address,ad.phone_number as phone from order_info oi left join address ad on oi.delivery_add_id = ad.id where oi.user_id = #{UserId}")
    List<OrderVo> getAllOrderByUserId(Integer UserId);

    @Update("update order_info set status = #{status} where id = #{id}")
    void reviveGoods(@Param("status")Integer status,@Param("id")String id);

    @Select("select sc.id,sc.number as goodsCount,g.goods_name as goodsName,g.goods_img as goodsImg,g.goods_price as goodsPrice,g.id as goodsId,ad.id as addressId, ad.name as name,ad.user_address as address,ad.phone_number as phone  from shoping_cart sc,goods g,address ad where sc.goods_id = g.id and sc.address_id = ad.id and sc.user_id = #{id}")
    List<ShopingCartVo>getCartById(Integer id);

    @Delete("delete from shoping_cart where id=#{id}")
    void delCartGoods(Integer id);

    @Select("select oi.*,g.goods_img as goodsImg,ad.name as name,ad.user_address as address,ad.phone_number as phone from shop as s,goods as g,order_info as oi ,address as ad where s.id = g.shop_id and g.id = oi.goods_id and oi.delivery_add_id = ad.id and s.id = #{id}")
    List<OrderVo> sellerGetAllOrderByShopId(Integer id);

    @Update("update goods  set goods_stock = #{stock} where id = #{id}" )
    void updateCount(@Param("id")Integer id,@Param("stock")Integer stock);

    @Insert("insert into miaosha_goods(goods_id,miaosha_price,stock_count,start_date,end_date) values(#{goodsId},#{miaoshaPrice},#{stockCount},#{startDate},#{endDate})")
    void insertMiaoShaGoods(MiaoshaGoods miaoshaGoods);

    @Insert("insert into miaosha_order(user_id,order_id,goods_id) values(#{userId},#{orderId},#{goodsId})")
    void createMiaoshaOrder(@Param("userId")Integer userId,@Param("goodsId")Integer goodsId,@Param("orderId")String orderId);

    @Select("select * from miaosha_order where order_id = #{orderId}")
    MiaoshaOrder getMiaoshaOrderByOrderId(@Param("orderId")String orderId);

    @Update("update miaosha_goods  set stock_count = #{stock} where goods_id = #{id} and stock_count > 0" )
    int updateMiaoshaCount(@Param("id")Integer id,@Param("stock")Long stock);

    @Select("select g.*,mg.stock_count, mg.start_date as startDate , mg.end_date as endDate,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where shop_id = #{shopId} and status != 1")
    List<GoodsVo> getMiaoshaGoodsByUser(@Param("shopId")Integer shopId);

    @UpdateProvider(type = GoodsSqlProvider.class,method = "updateMiaoshaGoods")
    void updateMiaoshaGoods(GoodsVo goods);

    @Select("select * from goods where goods_title like #{key} and status != 1 limit #{start},#{size} ")
    List<Goods> search(@Param("key")String key, @Param("start")int start, @Param("size")int size);

    @Select("select count(1) from goods where goods_title like #{key} and status != 1 ")
    int count(@Param("key")String key);
}
