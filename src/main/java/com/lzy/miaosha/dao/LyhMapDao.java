package com.lzy.miaosha.dao;

import com.lzy.miaosha.domain.LyhEvaluate;
import com.lzy.miaosha.domain.LyhMapData;
import com.lzy.miaosha.domain.LyhUser;
import com.lzy.miaosha.vo.LyhMap;
import com.lzy.miaosha.vo.LyhMapDataRootVo;
import com.lzy.miaosha.vo.LyhScenic;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface LyhMapDao {
    @Select("select * from map")
    List<LyhMap> getAllMap();

    @Select("select md.value,md.color as stateInitColor,md.city_id as indexs,c.city_spell as city  from map m,map_data md,city c where m.id = md.map_id and md.city_id = c.id  and m.id = #{id}")
    List<LyhMapData> getMapDataById(Integer id);

    @Select("select md.value,md.color,md.city_id,c.city_name,md.map_id,md.id,m.name as mapName  from map m,map_data md,city c where m.id = md.map_id and md.city_id = c.id")
    List<LyhMapDataRootVo> getMapData();

    @Update("update  map_data set color = #{color},value = #{value} where id = #{id}")
    void updateData(@Param("color")Integer color,@Param("id")Integer id,@Param("value")String value);

    @Delete("delete from map_data where id = #{mapId}")
    void delMapData(@Param("mapId")Integer mapId);

    @Insert("insert map(user_id,name)values(2,#{name})")
    void insertMap(LyhMap map);

    @Insert("insert map_data(map_id,city_id,value,color)values(#{mapId},#{cityId},#{value},#{color})")
    void insertMapData(LyhMapDataRootVo mapdata);

    @Select("select ss.*,c.city_name as cityName from scenic_spot ss,city c where ss.city_id = c.id where ss.city_id = #{cityId}")
    List<LyhScenic> getScenic(@Param("cityId")Integer cityId);

    @Select("select ss.*,c.city_name as cityName from scenic_spot ss,city c where ss.city_id = c.id")
    List<LyhScenic> getAllScenic();

    @Delete("delete from scenic_spot where id = #{scenicId}")
    void delScenic(@Param("scenicId")Integer scenicId);

    @Insert("insert scenic_spot(city_id,name,content,price)values(#{cityId},#{name},#{content},#{price})")
    Integer insertScenic(LyhScenic scenic);

    @Update("update  scenic_spot set pic = #{fileName} where id = #{id}")
    void insertImg(@Param("id")Integer id,@Param("fileName") String fileName);

    @Select("select id from scenic_spot where name = #{name}")
    Integer getScenicId(@Param("name")String name);

    @Delete("delete from evaluate where id = #{evaluateId}")
    void delEvaluate(@Param("evaluateId")Integer evaluateId);


    @Select("select ss.*,c.city_name as cityName from scenic_spot ss, city as c where ss.city_id = c.id and city_id = #{cityId}")
    List<LyhScenic> getScenicByCityId(@Param("cityId")Integer cityId);

    @Insert("insert evaluate(user_id,scenic_spot_id,content,status)values(#{userId},#{scenicSpotId},#{content},#{status})")
    void insertEvaluate(LyhEvaluate lyhEvaluate);

    @Select("select e.*,u.nickname as nickName from evaluate e,user u  where u.id = e.user_id and scenic_spot_id = #{scId}")
    List<LyhEvaluate> getEvaluateById(@Param("scId")Integer scId);
}
