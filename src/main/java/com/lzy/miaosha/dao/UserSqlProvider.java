package com.lzy.miaosha.dao;


import com.lzy.miaosha.domain.User;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * 动态SQL生成类
 */
public class UserSqlProvider {
    /**
     * 动态sql更新用户信息
     * @param user 用户信息
     * @return sql语句
     */
    public static String updateUser(final User user){
        return new SQL() {
            {
                UPDATE("user");
                if(user.getPassword() != null){
                    SET("password=#{password}");
                }
                if(user.getNickname() != null){
                    SET("nickname=#{nickname}");
                }
                if(user.getLastLoginDate() != null){
                    SET("last_login_Date=#{lastLoginDate}");
                }
                if(user.getLogincount() != null){
                    SET("login_count=#{logincount}");
                }
                WHERE("phone_number=#{phonenumber}");
            }}.toString();
    }

    public static String insertUser(final User user){
        return new SQL() {
            {
                INSERT_INTO("user");
                if(user.getPassword() != null){
                    VALUES("password","#{password}");
                }
                if(user.getNickname() != null){
                    VALUES("nickname","#{nickname}");
                }
                if(user.getRegisterdate() != null){
                    VALUES("register_date","#{registerdate}");
                }
                if(user.getLogincount() != null){
                    VALUES("login_count","#{logincount}");
                }
                if(user.getSalt() != null){
                    VALUES("salt","#{salt}");
                }
                if(user.getPhonenumber() != null){
                    VALUES("phone_number","#{phonenumber}");
                }
            }}.toString();
    }



}
