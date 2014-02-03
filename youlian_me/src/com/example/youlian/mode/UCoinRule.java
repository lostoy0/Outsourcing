package com.example.youlian.mode;

import org.json.JSONObject;

public class UCoinRule {

	/**注册奖励U点数量*/
	public int register;
	/**每天首次登陆奖励U点数量*/
	public int login;	
	/**申请卡奖励U点数量*/
	public int applyCard;	
	/**申请卡每天最多奖励次数*/
	public int applyCardMax;
	/**使用券奖励U点数量*/
	public int useFavourable;	
	/**使用券每天最多奖励次数*/
	public int useFavourableMax;
	/**申请券奖励U点数量*/
	public int applyFavourable;	
	/**申请券每天最多奖励U点次数	*/
	public int applyFavourableMax;
	/**充值U币数量	每充值youCoin U币，奖励 youDot U点*/
	public int youCoin;
	/**奖励U点数量*/
	public int youDot;
	/**分享奖励U点数量*/
	public int share;	
	/**分享每天最多奖励U点次数*/
	public int shareMax;
	/**短信推荐好友奖励U点数量*/
	public int recommend;	
	/**短信推荐每天最多奖励U点次数*/
	public int recommendMax;	
	/**U点兑换U币的规则	youdotToYoucoin U点=1U币*/
	public int youdotToYoucoin;
	/**U币价格（人民币价格）*/
	public double youcoinPrice;	
	public int orderValid;
	public String recommendContent;
	public int smsSendType;
	
	public static UCoinRule from(JSONObject json) {
		if(json != null) {
			UCoinRule rule = new UCoinRule();
			rule.applyCard = json.optInt("applyCard");
			rule.applyCardMax = json.optInt("applyCardMax");
			rule.applyFavourable = json.optInt("applyFavourable");
			rule.applyFavourableMax = json.optInt("applyFavourableMax");
			rule.login = json.optInt("login");
			rule.orderValid = json.optInt("orderValid");
			rule.recommend = json.optInt("recommend");
			rule.recommendContent = json.optString("recommendContent");
			rule.recommendMax = json.optInt("recommendMax");
			rule.register = json.optInt("register");
			rule.share = json.optInt("share");
			rule.shareMax = json.optInt("shareMax");
			rule.smsSendType = json.optInt("smsSendType");
			rule.useFavourable = json.optInt("useFavourable");
			rule.useFavourableMax = json.optInt("useFavourableMax");
			rule.youCoin = json.optInt("youCoin");
			rule.youcoinPrice = json.optDouble("youcoinPrice");
			rule.youDot = json.optInt("youDot");
			rule.youdotToYoucoin = json.optInt("youdotToYoucoin");
			return rule;
		}
		return null;
	}
}
