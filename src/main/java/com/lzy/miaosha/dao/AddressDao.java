package com.lzy.miaosha.dao;

import com.lzy.miaosha.domain.Address;
import com.lzy.miaosha.vo.AddressVo;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface AddressDao {
    @Select("select id, phone_number as phone,name,user_address as address from address where user_id = #{id}")
    List<AddressVo> getByUserId(int id);

    @UpdateProvider(type = AddressSqlProvider.class,method = "updateAddresss")
    @Options(useGeneratedKeys = true,keyProperty = "address.id")
    void updateAddress(Address address);

    @Insert("insert into address(user_id,phone_number,name,user_address) values(#{id},#{phonenumber},#{name},#{user_address}) ")
    void insertAddress(Address address);

}
