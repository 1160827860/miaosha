package com.lzy.miaosha.result;
/**
 * 包装错误代码
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
public class CodeMsg {
    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "成功");
    public static CodeMsg ACCESS_FAULT = new CodeMsg(1, "访问失败");
    public static CodeMsg BIND_ERROR = new CodeMsg(3, "参数校验异常：%s");
    //SESSION过期等情况
    public static CodeMsg NEED_LOGIN = new CodeMsg(10, "需要登录");
    public static CodeMsg INSUFFICIENT_AUTHORITY = new CodeMsg(2, "权限不足");
    //登陆模块的错误码
    public static CodeMsg PASS_ERROR = new CodeMsg(100, "密码错误");
    public static CodeMsg CHECKCODE_ERROR = new CodeMsg(101, "验证码错误");
    public static CodeMsg ACCOUNT_OR_PASS_EMPTY = new CodeMsg(102, "密码或者账号为空");
    public static CodeMsg ACCOUNT_NOT_EXIST = new CodeMsg(103, "密码或者账号为空");
    //注册模块
    public static CodeMsg ACCOUNT_EXIST = new CodeMsg(104, "账号已经存在");
    //秒杀模块
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(105, "商品已经秒杀完毕");
    /**
     * 多条错误信息进入
     * @param args 多个参数
     * @return CodeMsg包装类对象
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
