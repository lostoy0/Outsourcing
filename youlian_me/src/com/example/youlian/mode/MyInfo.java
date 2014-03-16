package com.example.youlian.mode;

import org.json.JSONObject;

/**
 * 
 * “我的”信息
 * @author Raymond
 * @date 2014-1-25 下午2:54:39
 */
public class MyInfo {
	/**登录名（手机号）*/
	public String loginId;
	/**用户名*/
	public String userName;
	/**用户头像*/
	public String logo;
	/**用户userToken*/
	public String userToken;
	/**总收藏数量*/
	public int favouritesCount;
	/**订单总数量	包含（未支付、已支付、已取消、已关闭）*/
	public int orderCount;
	/**优惠券数量*/
	public int favourableCount;
	/**U币数量*/
	public int youcoin;
	/**未支付订单数量	表示可以进行支付的订单，不包含（已取消、已关闭）*/
	public int unpaidCount;
	/**U点数量*/
	public int youdot;
	/**会员卡数量*/
	public int cardCount;
	/**会员卡有余额的数量*/
	public int hasBlanceCardCount;
	/**优惠券即将过期数量*/
	public int willExpFavourableCount;
	/**昵称*/
	public String nickname;
	
	public static MyInfo from(JSONObject json) {
		MyInfo info = null;
		if(json != null) {
			info = new MyInfo();
			info.cardCount = json.optInt("cardCount");
			info.favourableCount = json.optInt("favourableCount");
			info.favouritesCount = json.optInt("favouritesCount");
			info.hasBlanceCardCount = json.optInt("hasBlanceCardCount");
			info.loginId = json.optString("loginId");
			info.logo = json.optString("logo");
			info.orderCount = json.optInt("orderCount");
			info.unpaidCount = json.optInt("unpaidCount");
			info.userToken = json.optString("userToken");
			info.willExpFavourableCount = json.optInt("willExpFavourableCount");
			info.youcoin = json.optInt("youcoin");
			info.youdot = json.optInt("youdot");
			info.userName = json.optString("userName");
			info.nickname = json.optString("nickname");
		}
		return info;
	}
}
