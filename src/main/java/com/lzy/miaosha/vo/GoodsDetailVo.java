package com.lzy.miaosha.vo;

import com.lzy.miaosha.domain.User;

/**
 * 保存秒杀商品信息和用户信息的对象，主要还是为了生成订单
 */
public class GoodsDetailVo {
	private int miaoshaStatus = 0;
	private int remainSeconds = 0;
	private GoodsVo goods ;
	private User user;

	public int getMiaoshaStatus() {
		return miaoshaStatus;
	}

	public void setMiaoshaStatus(int miaoshaStatus) {
		this.miaoshaStatus = miaoshaStatus;
	}

	public int getRemainSeconds() {
		return remainSeconds;
	}

	public void setRemainSeconds(int remainSeconds) {
		this.remainSeconds = remainSeconds;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GoodsVo getGoods() {
		return goods;
	}
	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}

}
