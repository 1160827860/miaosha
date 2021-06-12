package com.lzy.miaosha.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.miaosha.domain.Complaint;
import com.lzy.miaosha.domain.Goods;
import com.lzy.miaosha.domain.Shop;
import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.redis.KeyPrefix;
import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.redis.keys.BanKey;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.GoodsService;
import com.lzy.miaosha.service.RootService;
import com.lzy.miaosha.service.UserService;
import com.mysql.cj.xdevapi.JsonArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/root")
public class RootController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    RootService rootService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;
    /**
     * 到管理员界面
     * @return
     */
    @RequestMapping("/to_root")
    public String toLogin() {
        return "root_index";
    }

    @RequestMapping(value = "/user")
    @ResponseBody
    public Result<List<User>> getUsers(){
        return Result.success(userService.getAllUser());
    }

    @RequestMapping(value = "/banUser")
    @ResponseBody
    public Result<CodeMsg> banUser(@RequestBody JSONArray data){
        String user = data.toString();
        String phone = user.substring(24,35);
        rootService.banUser(phone);
        redisService.set(BanKey.banedUser,""+phone,"被封禁");
        return Result.success(CodeMsg.SUCCESS);
    }
    @RequestMapping(value = "/apply")
    @ResponseBody
    public Result<List<Shop>> getApply(){
        List<Shop> res = rootService.getAllShopApply();
        for (int i = 0; i < res.size(); i++) {
            Shop shop = res.get(i);
            shop.setFrontPic("http://localhost:8080/"+shop.getFrontPic());
            shop.setBackPic("http://localhost:8080/"+shop.getBackPic());
            if (shop.getAuthority().equals("1")){
                shop.setAuthority("正在审核");
            }else if (shop.getAuthority().equals("2")){
                shop.setAuthority("正常经营");
            }else {
                shop.setAuthority("封号中");
            }
        }
        return Result.success(res);
    }

    @RequestMapping(value = "/agree")
    @ResponseBody
    public Result<CodeMsg> agreeApply(HttpServletRequest request,HttpServletResponse response,@RequestBody JSONArray data){
        String shop = data.toString();
        List<Shop> shops = new ArrayList<>(JSONArray.parseArray(shop,Shop.class));
        int userId = shops.get(0).getUserId();
        int shopId = shops.get(0).getId();
        rootService.agreeAplly(userId,shopId);
        return Result.success(CodeMsg.SUCCESS);
    }

    /**
     * 封禁店铺
     * @param data
     * @return
     */
    @RequestMapping(value = "/baned_shop")
    @ResponseBody
    public Result<CodeMsg> banedShop(@RequestBody JSONArray data){
        String shop = data.toString();
        List<Shop> shops = new ArrayList<>(JSONArray.parseArray(shop,Shop.class));
        int id = shops.get(0).getId();
        rootService.banShop(id);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping(value = "/goods")
    @ResponseBody
    public Result<List<Goods>> getAllGoods(){
        return  Result.success(goodsService.rootGetAllGoodsByRoot());
    }


    @RequestMapping(value = "/ban_goods/{params}")
    @ResponseBody
    public Result<CodeMsg> banGoods(@PathVariable("params")Integer id){
        Goods goods = new Goods();
        goods.setStatus(1);
        goods.setId(id);
        goodsService.updateGoods(goods);
        return Result.success(CodeMsg.SUCCESS);
    }


    @RequestMapping(value = "/complaint")
    @ResponseBody
    public Result<List<Complaint>> getAllComplaint(){
        return  Result.success(rootService.getAllComplaint());
    }


}
