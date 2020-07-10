package com.lzy.miaosha.controller;

import com.lzy.miaosha.domain.MiaoshaGoods;
import com.lzy.miaosha.domain.MiaoshaOrder;
import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.rabbitmq.MQSender;
import com.lzy.miaosha.rabbitmq.MiaoshaMessage;
import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.redis.keys.GoodsKey;
import com.lzy.miaosha.redis.keys.MiaoshaKey;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.GoodsService;
import com.lzy.miaosha.service.MiaoshaService;
import com.lzy.miaosha.service.OrderService;
import com.lzy.miaosha.util.Base64Util;
import com.lzy.miaosha.util.BaseValidate;
import com.lzy.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 秒杀模块控制层
 * @author 李正阳
 * @date 2020/3/3
 * @version 0.1
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    OrderService orderService;

    @Autowired
    MQSender sender;
    /**
     * 记录商品是否已经售空
     */
    private ConcurrentHashMap<String, Boolean> localOverMap =  new ConcurrentHashMap<String, Boolean>();

    /**
     * 系统初始化，保存秒杀商品的数量
     * */
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.getGoodsList();
        if(goodsList == null) {
            return;
        }
        for(GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getMiaoShaGoods, goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }

    /**
     * 获取秒杀时的验证码
     * @param user 用户信息
     * @param goodsId
     * @return String
     */
    @RequestMapping(value = "/verifyCode",method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaVerifyCode(User user,@RequestParam("goodsId") String goodsId){
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        BaseValidate validate = Base64Util.getRandomCode();
        redisService.set(MiaoshaKey.getMiaoshaVerifyCode,user.getPhonenumber()+","+goodsId,validate.getValue());
        StringBuffer sb = new StringBuffer();
        sb.append("data:image/png;base64,");
        sb.append(validate.getBase64Str());
        return Result.success(sb.toString());
    }

    /**
     * 获取隐藏的秒杀URL
     * @param user 用户信息
     * @param goodsId 商品信息
     * @param verifyCode 验证码
     * @return
     */
    @RequestMapping(value = "/path",method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaPath(User user,
                                         @RequestParam("goodsId") String goodsId,
                                         @RequestParam(value = "verifyCode", defaultValue = "0") String verifyCode){
        if(user == null) {
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        boolean check = miaoshaService.checkVerifyCode(user,goodsId,verifyCode);
        if(!check){
            return Result.error(CodeMsg.CHECKCODE_ERROR);
        }
        String path = miaoshaService.createMiaoshaPath(user,goodsId);
        return Result.success(path);
    }

    /**
     * 秒杀功能实现
     * @param user 用户信息
     * @param goodsId 商品id
     * @param path 秒杀url动态部分
     * @return 0 排队中，
     */
    @RequestMapping(value="/{path}/do_miaosha", method=RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(User user,
                                   @RequestParam("goodsId")String goodsId,
                                   @PathVariable("path") String path) {
        if(user == null) {
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        //验证path的正确性
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if(!check){
            return Result.error(CodeMsg.ACCESS_FAULT);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getMiaoShaGoods, goodsId);//10
        if(stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(CodeMsg.ACCESS_FAULT);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(mm);
        return Result.success(0);//排队中
    }

    /**
     * 获得秒杀的结果
     * @param user 用户信息
     * @param goodsId 商品ID
     * @return -1排队失败 0 排队中 orderId 成功
     */
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(User user,
                                      @RequestParam("goodsId")String goodsId) {
        if(user == null) {
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        Long result  =miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

        public static void main(String[] args) {
            String a = "13534654364565476573534654765";
            String b ="457658623534658768946547678978";

            //b*a
            int[][] t = new int[a.length()][b.length() + a.length()];
            for (int i = 0; i < a.length(); i++) {
                int jLength = t[0].length - 1 -i;
                for (int j = b.length() - 1; j >= 0; j--,jLength --) {
                    int temp = Integer.parseInt(a.charAt(a.length() - 1 - i) + "") * Integer.parseInt(b.charAt(j) + "");
                    t[i][jLength] += temp % 10;
                    if (temp > 10 && j > 0) {
                        t[i][jLength - 1] = temp / 10 % 10;
                    }
                }
            }
            //49830301552812754113469933452170977 15147889329259278030170
            //61942512865043976426582245772180978 15147889329259278030170
            LinkedList<Integer> res = new LinkedList<>();
            int temp = 0;
            for (int j = t[0].length - 1; j >= 0; j--) {
                for (int i = 0; i < a.length(); i++) {
                    temp+=t[i][j];
                }
                res.addFirst(temp % 10);
                if(temp >= 10){
                    temp = temp/10;
                }else {
                    temp = 0;
                }
            }
            boolean index = true;
            for (int i = 0; i < res.size(); i++) {
                if(res.get(i) == 0 && index ){
                }else {
                    index = false;
                    System.out.print(res.get(i));
                }
            }
            System.out.println();
            BigInteger a1 = new BigInteger("13534654364565476573534654765");
            BigInteger a2 = new BigInteger("457658623534658768946547678978");
            System.out.println(a1.multiply(a2).toString());
        }
    }


