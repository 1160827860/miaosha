package com.lzy.miaosha.service;

import com.lzy.miaosha.dao.LyhMapDao;
import com.lzy.miaosha.domain.LyhEvaluate;
import com.lzy.miaosha.domain.LyhMapData;
import com.lzy.miaosha.domain.LyhUser;
import com.lzy.miaosha.exception.GlobalException;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.util.UUIDUtil;
import com.lzy.miaosha.vo.LyhEvaluateVo;
import com.lzy.miaosha.vo.LyhMap;
import com.lzy.miaosha.vo.LyhMapDataRootVo;
import com.lzy.miaosha.vo.LyhScenic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class LyhMapService {
    @Autowired
    LyhMapDao mapDao;
    public List<LyhMap> getAllMap(){
        return mapDao.getAllMap();
    }

    public List<LyhMapData> getMapDataById(Integer id){
        return mapDao.getMapDataById(id);
    }

    public List<LyhMapDataRootVo> getMapData() {
        return mapDao.getMapData();
    }

    public void updateMapData(LyhMapDataRootVo mapDataRootVo) {
        mapDao.updateData(mapDataRootVo.getColor(),mapDataRootVo.getId(),mapDataRootVo.getValue());
    }

    public void delMapData(Integer mapId) {
        mapDao.delMapData(mapId);
    }

    public void insertMap(LyhMap map) {
        mapDao.insertMap(map);
    }

    public void insertMapData(LyhMapDataRootVo mapdata) {
        mapDao.insertMapData(mapdata);
    }

    public List<LyhScenic> getAllScenic() {
        return mapDao.getAllScenic();
    }

    public void delScenic(Integer scenicId) {
        mapDao.delScenic(scenicId);
    }

    public Integer insertScenic(LyhScenic scenic) {
        return mapDao.insertScenic(scenic);
    }

    public String updateGoodsPic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file"); //获取单个文件
        if (file == null){
            throw new GlobalException(CodeMsg.UPDATE_EMPTY);
        }
        //使用的绝对路径未改
        String originalFileName = file.getOriginalFilename();
        String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUIDUtil.uuid()+suffixName;
        File newFile=new File("D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\lyh\\images\\"+fileName);
        file.transferTo(newFile);
        return fileName;
    }

    public void updateScenicPic(Integer id, String fileName) {
        mapDao.insertImg(id,fileName);
    }

    public Integer getScenicId(String name) {
        return mapDao.getScenicId(name);
    }

    public void delEvaluate(Integer evaluateId) {
        mapDao.delEvaluate(evaluateId);
    }
    public List<LyhScenic> getScenicById(Integer cityId){
       return mapDao.getScenicByCityId(cityId);
    }

    public void insertEvaluate(HttpServletRequest request, HttpServletResponse response,LyhEvaluateVo evaluateVo) {
        LyhEvaluate lyhEvaluate = new LyhEvaluate();
        lyhEvaluate.setContent(evaluateVo.getContent());
        lyhEvaluate.setScenicSpotId(evaluateVo.getScenicSpotId());
        lyhEvaluate.setStatus(evaluateVo.getStatus());
        LyhUser user = (LyhUser) request.getSession().getAttribute("user");
        lyhEvaluate.setUserId(user.getId());
        mapDao.insertEvaluate(lyhEvaluate);
    }

    public List<LyhEvaluate> getEvaluateById(Integer scId) {
        return mapDao.getEvaluateById(scId);
    }
}
