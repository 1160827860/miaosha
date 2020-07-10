package com.lzy.miaosha.controller;

import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
/**
 * @Description 用户注册模块控制层
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
@Controller
public class RegisterController {

    @Autowired
    UserService userService;
    /**
     * 检查用户名是否注册
     * @return
     */
    @RequestMapping("/checkUserName")
    @ResponseBody
    public Result<CodeMsg> checkUserName(@RequestParam("phone_number") @Valid @NotNull String phone_number){
        User user = userService.getByPhone(phone_number);
        if(user != null){
            return Result.error(CodeMsg.ACCOUNT_EXIST);
        }

        return Result.success(CodeMsg.SUCCESS);
    }

    public static void main(String[] args) {
        int[] nums = {3,7,7,7,4,4,4};
        String a;
        System.out.println(singleNumber(nums));
    }
    static public int singleNumber(int[] nums) {
        int ans = 0;

        for(int i = 0; i < 32; i++){
            int count = 0;
            for(int num : nums){
                if(((1 << i) & num) > 0){
                    count++;
                }
            }
            if(count % 3 == 1){
                ans += (1 << i);
            }
        }
        return ans;
    }

}
