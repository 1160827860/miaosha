package com.lzy.miaosha.result;

/**
 * 携带后端的数据返回前端是统一的接口
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 * @param <T>携带的数据
 */
public class Result<T> {
    public int code;
    private String msg;
    private T data;

    /**
     * 成功时候的调用
     * @param data 携带的数据
     * @param <T> 携带数据的泛型
     * @return 携带数据的Result类对象
     */
    public static  <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 失败时候的调用
     * @param codeMsg 携带的数据
     * @param <T> 携带数据的泛型
     * @return 携带数据的Result类对象
     */
    public static  <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    private Result(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.msg =  codeMsg.getMsg();
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
