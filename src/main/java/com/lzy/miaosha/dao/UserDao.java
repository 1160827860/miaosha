package com.lzy.miaosha.dao;

import com.lzy.miaosha.domain.Complaint;
import com.lzy.miaosha.domain.Shop;
import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.vo.ComplaintVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.hibernate.validator.internal.metadata.aggregated.ValidatableParametersMetaData;

import java.lang.reflect.Type;
import java.util.List;

@Mapper
public interface UserDao {


    /**
     * @Description 通过id来查找用户的全部信息
     * @param id 用户ID
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(@Param("id")int id);
    /**
     * @Description 通过手机号来查找用户的全部信息
     * @param phone_number 手机号
     * @return 用户信息
     */
    @Select("select * from user where phone_number = #{phone_number}")
    User getByPhone(String phone_number);

    /**
     * 将用户信息插入数据库
     * @param user 用户信息
     * @return 用户的信息
     */
    @InsertProvider(type = UserSqlProvider.class,method = "insertUser")
    void insertUser(User user);

    @UpdateProvider(type = UserSqlProvider.class,method = "updateUser")
    @Options(useGeneratedKeys = true,keyProperty = "user.id")
    void updateUser(User user);

    @Insert("insert into shop(user_id,name,info,authority,people_name,people_num) values(#{userId},#{name},#{info},#{authority},#{peopleName},#{peopleNum}) ")
    void applyShop(Shop shop);

    @UpdateProvider(type = UserSqlProvider.class,method = "updateShop")
    @Options(useGeneratedKeys = true,keyProperty = "shop.id")
    void updateShop(Shop shop);

    @Select("select * from user")
    List<User> getAllUser();

    @Select("select * from shop")
    List<Shop> getAllShop();

    @Select("select * from shop where user_id = #{id}")
    Shop getShopByUserId(int id);

    @UpdateProvider(type = UserSqlProvider.class,method = "updateShopById")
    @Options(useGeneratedKeys = true,keyProperty = "shop.id")
    void updateShopById(Shop shop);

    @UpdateProvider(type = UserSqlProvider.class,method = "updateUserById")
    @Options(useGeneratedKeys = true,keyProperty = "user.id")
    void updateUserById(User user);

    @Insert("insert into user_complaint(complaint_id,order_id,content,apply_date,phone_number) values(#{complaintId},#{orderId},#{content},#{applyDate},#{phone})")
    void insertComplaint(Complaint complaintVo);
}
