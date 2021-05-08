package com.lzy.miaosha.dao;

import com.lzy.miaosha.domain.Address;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 李正阳
 * 更新地址
 */
public class AddressSqlProvider {
    /**
     * 动态sql更新地址信息
     * @param address 地址信息
     * @return sql语句
     */
    public static String updateAddresss(final Address address){
        return new SQL() {
            {
                UPDATE("address");
                if(address.getName() != null){
                    SET("name=#{name}");
                }
                if(address.getPhonenumber() != null){
                    SET("phone_number=#{phonenumber}");
                }
                if(address.getUser_address() != null){
                    SET("user_address=#{user_address}");
                }
                WHERE("id=#{id}");
            }}.toString();
    }
}
