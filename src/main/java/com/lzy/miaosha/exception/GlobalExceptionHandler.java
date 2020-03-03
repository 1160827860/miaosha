package com.lzy.miaosha.exception;

import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * @Description 全局异常处理器
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	/**
	 * 处理全局异常
	 * @param e 错误信息
	 * @return 错误类包装对象
	 */
	@ExceptionHandler(value=Exception.class)
	public Result<String> exceptionHandler(Exception e){
		//出现错误的时候打印堆栈信息
		e.printStackTrace();
		//如果e为全局异常就返回异常信息
		if(e instanceof GlobalException) {
			GlobalException ex = (GlobalException)e;
			return Result.error(ex.getCm());
		}else if(e instanceof BindException) {//jsr303绑定异常抛出
			BindException ex = (BindException)e;
			List<ObjectError> errors = ex.getAllErrors();
			ObjectError error = errors.get(0);
			String msg = error.getDefaultMessage();
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
		}else {//其他异常抛出
			return Result.error(CodeMsg.ACCESS_FAULT);
		}
	}
}
