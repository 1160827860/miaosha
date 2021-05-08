package com.lzy.miaosha.dao;


import com.lzy.miaosha.domain.Shop;
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
                if(user.getAuthority()!=null){
                    SET("authority=#{authority}");
                }
                if(user.getLastLoginDate() != null){
                    SET("last_login_Date=#{lastLoginDate}");
                }
                if(user.getHead() != null){
                    SET("head=#{head}");
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
                if(user.getAuthority() != null){
                VALUES("authority","#{authority}");
                }
            }}.toString();
    }

    /**
     * 动态sql更新用户信息 user_id
     * @param shop 商店信息
     * @return sql语句
     */
    public static String updateShop(final Shop shop){
        return new SQL() {
            {
                UPDATE("shop");
                if(shop.getAuthority() != null){
                    SET("authority=#{authority}");
                }
                if(shop.getFrontPic() != null){
                    SET("front_pic=#{frontPic}");
                }
                if(shop.getBackPic() != null){
                    SET("back_pic=#{backPic}");
                }
                if(shop.getInfo() != null){
                    SET("info=#{info}");
                }
                if(shop.getName() != null){
                    SET("name=#{name}");
                }
                WHERE("user_id=#{userId}");
            }}.toString();
    }

    public static String updateShopById(final Shop shop){
        return new SQL() {
            {
                UPDATE("shop");
                if(shop.getAuthority() != null){
                    SET("authority=#{authority}");
                }
                if(shop.getFrontPic() != null){
                    SET("front_pic=#{frontPic}");
                }
                if(shop.getBackPic() != null){
                    SET("back_pic=#{backPic}");
                }
                if(shop.getInfo() != null){
                    SET("info=#{info}");
                }
                if(shop.getName() != null){
                    SET("name=#{name}");
                }
                WHERE("id=#{id}");
            }}.toString();
    }
    public static String updateUserById(final User user){
        return new SQL() {
            {
                UPDATE("user");
                if(user.getPassword() != null){
                    SET("password=#{password}");
                }
                if(user.getNickname() != null){
                    SET("nickname=#{nickname}");
                }
                if(user.getAuthority()!=null){
                    SET("authority=#{authority}");
                }
                if(user.getLastLoginDate() != null){
                    SET("last_login_Date=#{lastLoginDate}");
                }
                if(user.getHead() != null){
                    SET("head=#{head}");
                }
                if(user.getLogincount() != null){
                    SET("login_count=#{logincount}");
                }
                WHERE("id=#{id}");
            }}.toString();
    }

}
