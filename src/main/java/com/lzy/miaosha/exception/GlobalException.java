package com.lzy.miaosha.exception;

import com.lzy.miaosha.result.CodeMsg;
/**
 * @Description 全局异常处理，继承了运行时异常
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private CodeMsg cm;
	
	public GlobalException(CodeMsg cm) {
		super(cm.toString());
		this.cm = cm;
	}

	public CodeMsg getCm() {
		return cm;
	}

}
