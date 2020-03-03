package com.lzy.miaosha.controller;


import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.GoodsService;
import com.lzy.miaosha.service.UserService;
import com.lzy.miaosha.vo.GoodsDetailVo;
import com.lzy.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @Description 商品模块控制层
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    /**
     *读取数据库向用户发送商品信息
     * @return 所有商品的信息
     */
    @RequestMapping("/to_list")
    @ResponseBody
    public Result<List<GoodsVo>> getGoodsList(){
        List<GoodsVo> goodsVoList = goodsService.getGoodsList();
        return Result.success(goodsVoList);
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> getGoodsDetail(User user,@PathVariable("goodsId") String goodsId){
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }

}
