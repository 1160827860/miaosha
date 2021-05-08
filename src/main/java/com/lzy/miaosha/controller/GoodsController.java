package com.lzy.miaosha.controller;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.lzy.miaosha.domain.Goods;
import com.lzy.miaosha.domain.OrderInfo;
import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.GoodsService;
import com.lzy.miaosha.service.UserService;
import com.lzy.miaosha.util.UUIDUtil;
import com.lzy.miaosha.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Date;
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






    @RequestMapping("/getAllGoods")
    @ResponseBody
    public Result<List<Goods>> getAllGoods(){
        List<Goods> goodsList = goodsService.getAllGoods();
        return Result.success(goodsList);
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> getMiaoShaGoodsDetail(User user,@PathVariable("goodsId") Integer goodsId){
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
        List<AddressVo> addressVos = userService.getAddressById(user.getId());
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setAddressVos(addressVos);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }

    @RequestMapping(value = "/{goodsId}")
    @ResponseBody
    public Result<NormalGoodsVo> getGoodsDetail(@PathVariable("goodsId") Integer goodsId, HttpServletRequest request, HttpServletResponse response){
        Goods goods = goodsService.getGoodsById(goodsId);
        NormalGoodsVo resGoods = new NormalGoodsVo();
        resGoods.setGoods(goods);
        User user = userService.getByToken(request,response);
        resGoods.setUser(user);
        List<AddressVo> addressVos = userService.getAddressById(user.getId());
        resGoods.setAddressVos(addressVos);
        return Result.success(resGoods);
    }

    @RequestMapping("/addCart")
    @ResponseBody
    public Result<CodeMsg> addGoodsToCart(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        Integer goodsId = Integer.parseInt(request.getParameter("goods"));
        Integer addressId = Integer.parseInt(request.getParameter("addressVo"));
        Integer number = Integer.parseInt(request.getParameter("number"));
        goodsService.addGoodsToShopintCart(goodsId,addressId,number,user);
        return Result.success(CodeMsg.SUCCESS);
    }

    /**
     * 购买货物
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/buy")
    @ResponseBody
    public Result<CodeMsg> buyGoods(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        Integer goodsId = Integer.parseInt(request.getParameter("goods"));
        Integer addressId = Integer.parseInt(request.getParameter("addressVo"));
        Integer number = Integer.parseInt(request.getParameter("number"));
        Goods goods = goodsService.getGoodsById(goodsId);
        if(goods.getGoodsStock() >= number){
            OrderInfo orderInfo = goodsService.buy(user,goods,addressId,number);
            Integer stock = goods.getGoodsStock() - number;
            goodsService.decrGoodsCount(goods,stock);
            goodsService.buyGoods(orderInfo);
        }else {
            Result.error(CodeMsg.GOODS_OVER);
        }
        return Result.success(CodeMsg.SUCCESS);
    }


    @RequestMapping("/order")
    @ResponseBody
    public Result<List<OrderVo>> getAllOrderByUserId(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        List<OrderVo> order = goodsService.getAllOrderByUserId(user.getId());

        for (int i = 0; i < order.size(); i++) {
            OrderVo tmp = order.get(i);
            if(tmp.getStatus().equals("1")){
                tmp.setStatus("已支付等待发货");
            }else if (tmp.getStatus().equals("2")){
                tmp.setStatus("已发货");
            }else if (tmp.getStatus().equals("3")){
                tmp.setStatus("已收货");
            }else if (tmp.getStatus().equals("4")){
                tmp.setStatus("已退款");
            }else if (tmp.getStatus().equals("6")){
                tmp.setStatus("申请退款");
            }else if (tmp.getStatus().equals("7")){
                tmp.setStatus("已投诉");
            }else {
                tmp.setStatus("已完成");
            }
        }
        return Result.success(order);
    }

    /**
     * 收货成功
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping("/agree/{params}")
    @ResponseBody
    public Result<CodeMsg> reviceGoods(HttpServletRequest request, HttpServletResponse response,@PathVariable("params")String id){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        goodsService.changeGoodsStaus(3,id);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/cart")
    @ResponseBody
    public Result<List<ShopingCartVo>> getUserShopingCart(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        List<ShopingCartVo> res = goodsService.getCartById(user.getId());
        return Result.success(res);
    }


    @RequestMapping("/cart/del/{params}")
    @ResponseBody
    public Result<CodeMsg> delCartGoods(HttpServletRequest request, HttpServletResponse response,@PathVariable("params")Integer id){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        goodsService.delShopingCartGoods(id);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/cart_buy")
    @ResponseBody
    public Result<CodeMsg> buyGoodsByCart(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }


        Integer goodsId = Integer.parseInt(request.getParameter("goods"));
        Integer addressId = Integer.parseInt(request.getParameter("addressVo"));
        Integer number = Integer.parseInt(request.getParameter("number"));
        Integer id = Integer.parseInt(request.getParameter("id"));
        Goods goods = goodsService.getGoodsById(goodsId);


        if(goods.getGoodsStock() >= number){
            OrderInfo orderInfo = goodsService.buy(user,goods,addressId,number);
            Integer stock = goods.getGoodsStock() - number;
            goodsService.decrGoodsCount(goods,stock);
            goodsService.buyGoods(orderInfo);
        }else {
            Result.error(CodeMsg.GOODS_OVER);
        }

        goodsService.delShopingCartGoods(id);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/seller_manage")
    @ResponseBody
    public Result<List<OrderVo>> sellerGetAllOrder(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }

        List<OrderVo> order = goodsService.sellerGetAllOrder(user.getId());
        if(order == null){
            return  Result.error(CodeMsg.UNSUPPLIED_USER);
        }
        for (int i = 0; i < order.size(); i++) {
            OrderVo tmp = order.get(i);
            if(tmp.getStatus().equals("1")){
                tmp.setStatus("已支付等待发货");
            }else if (tmp.getStatus().equals("2")){
                tmp.setStatus("已发货");
            }else if (tmp.getStatus().equals("3")){
                tmp.setStatus("已收货");
            }else if (tmp.getStatus().equals("4")){
                tmp.setStatus("已退款");
            }else if (tmp.getStatus().equals("6")){
                tmp.setStatus("申请退款");
            }else if (tmp.getStatus().equals("7")){
                tmp.setStatus("已投诉");
            }else {
                tmp.setStatus("已完成");
            }
        }
        return Result.success(order);
    }

    /**
     * 发货
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping("/shipment/{params}")
    @ResponseBody
    public Result<CodeMsg> shipment(HttpServletRequest request, HttpServletResponse response,@PathVariable("params")String id){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        goodsService.changeGoodsStaus(2,id);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/reback/{params}")
    @ResponseBody
    public Result<CodeMsg> reback(HttpServletRequest request, HttpServletResponse response,@PathVariable("params")String id){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        goodsService.changeGoodsStaus(6,id);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/seller/accept_reback/{params}")
    @ResponseBody
    public Result<CodeMsg> acceptReback(HttpServletRequest request, HttpServletResponse response,@PathVariable("params")String id){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        goodsService.changeGoodsStaus(4,id);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/finish/{params}")
    @ResponseBody
    public Result<CodeMsg> finsh(HttpServletRequest request, HttpServletResponse response,@PathVariable("params")String id){
        User user = userService.getByToken(request,response);
        if(user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        goodsService.changeGoodsStaus(5,id);
        return Result.success(CodeMsg.SUCCESS);
    }
}
