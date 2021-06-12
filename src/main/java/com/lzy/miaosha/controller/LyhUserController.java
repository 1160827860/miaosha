package com.lzy.miaosha.controller;

import com.alibaba.fastjson.JSONArray;
import com.lzy.miaosha.domain.LyhEvaluate;
import com.lzy.miaosha.domain.LyhMapData;
import com.lzy.miaosha.domain.LyhUser;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.LyhMapService;
import com.lzy.miaosha.util.Base64Util;
import com.lzy.miaosha.util.BaseValidate;
import com.lzy.miaosha.service.LyhUserService;
import com.lzy.miaosha.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/map")
public class LyhUserController {
    @Autowired
    LyhUserService userService;
    @Autowired
    LyhMapService mapService;
    /**
     * 登陆功能
     *
     * @return 是否成功的数据
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public Result<String> doLogin(HttpServletRequest request, HttpServletResponse response, @Valid LyhLoginVo loginVo) {
        userService.login(request,response,loginVo);
        LyhUser user = (LyhUser)request.getSession().getAttribute("user");
        if( user == null){
            return Result.error(CodeMsg.NEED_LOGIN);
        }
        if(user.getStatus() == 1){
            return Result.success("user");
        }else {
            return Result.success("root");
        }

    }


    @RequestMapping(value = "/register")
    @ResponseBody
    public Result<CodeMsg> register(@Valid LyhRegiseterVo registerVo){
        userService.register(registerVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    /**
     * 获取验证码
     * @return base64的
     */
    @RequestMapping("/getCheckCode")
    @ResponseBody
    public String getChcekCode(HttpServletRequest request){
        BaseValidate validate = Base64Util.getRandomCode();
        System.out.println(validate.getValue());
        request.getSession().setAttribute("CHECK_CODE",validate.getValue());
        return "data:image/png;base64,"+validate.getBase64Str();
    }

    @RequestMapping("/exit")
    @ResponseBody
    public Result<CodeMsg> exit(HttpServletRequest request){
        request.getSession().setAttribute("user",null);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/check_login")
    @ResponseBody
    public Result<LyhUser> checkLogin(HttpServletRequest request){
        LyhUser user = (LyhUser) request.getSession().getAttribute("user");
        if(user == null){
            return  Result.error(CodeMsg.NEED_LOGIN);
        }else {
            return Result.success(user);
        }
    }

    @RequestMapping("/getMap")
    @ResponseBody
    public Result<List<LyhMap>> getMap(HttpServletRequest request){
        List<LyhMap> map = mapService.getAllMap();
       return Result.success(map);
    }

    @RequestMapping("/get_map_data/{mapId}")
    @ResponseBody
    public Result<List<LyhMapData>> getMapData(HttpServletRequest request,@PathVariable("mapId") Integer mapId){
        List<LyhMapData> mapData =mapService.getMapDataById(mapId);
        return Result.success(mapData);
    }

    @RequestMapping("/getAllScenic/{cityId}")
    @ResponseBody
    public Result<List<LyhScenic>> getAllScenicById(@PathVariable("cityId") Integer cityId){
        List<LyhScenic> mapData =mapService.getScenicById(cityId);
        return Result.success(mapData);
    }
    @RequestMapping("/insert_evaluate")
    @ResponseBody
    public Result<CodeMsg> insertEvaluate(HttpServletRequest request, HttpServletResponse response,@Valid LyhEvaluateVo evaluateVo){
        mapService.insertEvaluate(request,response,evaluateVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/get_evaluate/{scId}")
    @ResponseBody
    public Result<List<LyhEvaluate>> getEvaluate(@PathVariable("scId") Integer scId){
        List<LyhEvaluate> evaluates = mapService.getEvaluateById(scId);
        return Result.success(evaluates);
    }


}
