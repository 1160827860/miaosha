package com.lzy.miaosha.dao;

import com.lzy.miaosha.vo.LyhEvaluateVo;
import com.lzy.miaosha.domain.LyhUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LyhUserDao {

    @Select("select * from user where phone_number = #{phone}")
    LyhUser getUserInfo(String phone);

    @Insert("insert user(phone_number,nickname,password,register_date,status)values(#{phoneNumber},#{nickname},#{password},#{registerDate},#{status})")
    void insertUser(LyhUser user);

    @Select("select * from user")
    List<LyhUser> getAllUser();

    @Delete("delete from user where id = #{id}")
    void delUser(@Param("id") Integer id);

    @Select("select e.*,ss.name from evaluate e, scenic_spot ss where ss.id = e.scenic_spot_id")
    List<LyhEvaluateVo> getAllEvaluate();
}
