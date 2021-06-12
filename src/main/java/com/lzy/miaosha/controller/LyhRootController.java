package com.lzy.miaosha.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lzy.miaosha.vo.LyhEvaluateVo;
import com.lzy.miaosha.domain.LyhUser;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.LyhMapService;
import com.lzy.miaosha.service.LyhUserService;
import com.lzy.miaosha.vo.LyhMap;
import com.lzy.miaosha.vo.LyhMapDataRootVo;
import com.lzy.miaosha.vo.LyhScenic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/map_root")
public class LyhRootController {
    @Autowired
    LyhUserService userService;
    @Autowired
    LyhMapService mapService;

    @RequestMapping("/user")
    @ResponseBody
    public Result<List<LyhUser>> getMapData(HttpServletRequest request){
        List<LyhUser> users = userService.getAllUser();
        return Result.success(users);
    }
    @RequestMapping("/banUser")
    @ResponseBody
    public Result<CodeMsg> banUser(@RequestBody JSONArray data){
        String user = data.toString();
        Integer userId = Integer.parseInt(user.substring(user.indexOf(":")+1,user.indexOf(",")));
        userService.delUser(userId);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/show_map_data")
    @ResponseBody
    public Result<List<LyhMapDataRootVo>> showMapData(){
        List<LyhMapDataRootVo> mapData = mapService.getMapData();
        return Result.success(mapData);
    }

    @RequestMapping("/update_map_data")
    @ResponseBody
    public Result<CodeMsg> updateMapData(@Valid LyhMapDataRootVo mapDataRootVo){
        mapService.updateMapData(mapDataRootVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/del_map_data")
    @ResponseBody
    public Result<CodeMsg> delMapData(@RequestBody JSONArray data){
        String map = data.toString();
        Integer mapId = Integer.parseInt(map.substring(map.indexOf(":")+1,map.indexOf(",")));
        mapService.delMapData(mapId);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/insert_map")
    @ResponseBody
    public Result<CodeMsg> insertMap(@Valid LyhMap map){
        mapService.insertMap(map);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/insert_map_data")
    @ResponseBody
    public Result<CodeMsg> insertMapData(@Valid LyhMapDataRootVo mapdata){
        mapService.insertMapData(mapdata);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/show_scenic")
    @ResponseBody
    public Result<List<LyhScenic>> getScenic(){
        return Result.success(mapService.getAllScenic());
    }

    @RequestMapping("/del_scenic")
    @ResponseBody
    public Result<CodeMsg> delScenic(@RequestBody JSONArray data){
        String map = data.toString();
        Integer scenicId = Integer.parseInt(map.substring(map.indexOf(":")+1,map.indexOf(",")));
        mapService.delScenic(scenicId);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/updateImg")
    @ResponseBody
    public Result<CodeMsg> updateGoodsImg(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false,value = "params")String params) throws IOException {
        LyhScenic scenic = JSON.parseObject(params,LyhScenic.class);
         mapService.insertScenic(scenic);
         Integer id = mapService.getScenicId(scenic.getName());
        String fileName = mapService.updateGoodsPic(request,response);
        fileName = "/lyh/images/"+fileName;
        mapService.updateScenicPic(id,fileName);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/show_evaluate")
    @ResponseBody
    public Result<List<LyhEvaluateVo>> showEvaluate(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false,value = "params")String params) throws IOException {
        List<LyhEvaluateVo> evaluates = userService.getAllEvaluate();
        return Result.success(evaluates);
    }
    @RequestMapping("/del_evaluate")
    @ResponseBody
    public Result<CodeMsg>  delEvaluate(@RequestBody JSONArray data){
        String map = data.toString();
        Integer evaluateId = Integer.parseInt(map.substring(map.indexOf(":")+1,map.indexOf(",")));
         mapService.delEvaluate(evaluateId);
        return Result.success(CodeMsg.SUCCESS);
    }

}
