package com.lzy.miaosha.service;

import com.lzy.miaosha.vo.LyhEvaluateVo;
import com.lzy.miaosha.exception.GlobalException;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.dao.LyhUserDao;
import com.lzy.miaosha.domain.LyhUser;
import com.lzy.miaosha.vo.LyhLoginVo;
import com.lzy.miaosha.vo.LyhRegiseterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;

@Service
public class LyhUserService {

    @Autowired
    LyhUserDao lyhUserDao;
    public void login(HttpServletRequest request, HttpServletResponse response,  LyhLoginVo loginVo){
        String checkCode = (String)request.getSession().getAttribute("CHECK_CODE");
        if(!loginVo.getCheckCode().equals(checkCode)){
            throw new GlobalException(CodeMsg.CHECKCODE_ERROR);
        }
        LyhUser user = lyhUserDao.getUserInfo(loginVo.getPhone_number());
        if(!user.getPassword().equals(loginVo.getPassWords())){
            throw new GlobalException(CodeMsg.PASS_ERROR);
        }
        request.getSession().setAttribute("user",user);
    }


    public void register(LyhRegiseterVo registerVo) {
        LyhUser user = new LyhUser();
        user.setNickname(registerVo.getNickname());
        user.setPassword(registerVo.getPassword());
        user.setPhoneNumber(registerVo.getPhone_number());
        user.setStatus(1);
        user.setRegisterDate(new Date(new java.util.Date().getTime()));
        lyhUserDao.insertUser(user);
    }

    public List<LyhUser> getAllUser(){
       return lyhUserDao.getAllUser();
    }

    public void delUser(Integer id){
         lyhUserDao.delUser(id);
    }

    public List<LyhEvaluateVo> getAllEvaluate() {
        return lyhUserDao.getAllEvaluate();
    }
}
