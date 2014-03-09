package com.example.youlian.mode;

import org.json.JSONObject;

public class CardInfo {
	/**序号*/
	public String id;	
	/**名字*/
	public String memberName;	
	/**性别 1-男 0-女*/
	public int memberSex;
	/**生日*/
	public String memberBirth;
	/**手机号码*/
	public String memberPhone;
	/**Email*/
	public String memberEmail;	
	/**联系地址*/
	public String memberAddress;
	/**QQ*/
	public String memberQq;
	/**身份证号*/
	public String membeIdCard;
	/**是否允许推送（1：允许 0：不允许）*/
	public int isAllowPush;
	
	public static CardInfo from(JSONObject json) {
		CardInfo info = null;
		if(json != null) {
			info = new CardInfo();
			info.id = json.optString("id");
			info.isAllowPush = json.optInt("isAllowPush");
			info.membeIdCard = json.optString("membeIdCard");
			info.memberAddress = json.optString("memberAddress");
			info.memberBirth = json.optString("memberBirth");
			info.memberEmail = json.optString("memberEmail");
			info.memberName = json.optString("memberName");
			info.memberPhone = json.optString("memberPhone");
			info.memberQq = json.optString("memberQq");
			info.memberSex = json.optInt("memberSex");
		}
		return info;
	}

}
