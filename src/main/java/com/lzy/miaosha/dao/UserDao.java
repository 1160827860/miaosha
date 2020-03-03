package com.lzy.miaosha.dao;

import com.lzy.miaosha.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

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



}
