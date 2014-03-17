package com.example.youlian.mode;

import java.io.Serializable;

import org.json.JSONObject;
/**
 *  申请会员卡
 * @author wells
 *
 */
public class ApplyCardResult implements Serializable{
	public static final long serialVersionUID = 1L;
	
	/*card_id	会员卡ID
	card_name	会员卡名称
	card_no	会员卡实例化编号
	time_from	有效期开始时间
	time_to	有效期结束时间
	card_num	会员卡人数
	card_surplus_num	会员卡剩余数量
	cluber_welfare	会员福利
	card_directions	使用说明
	nonactivatedPic	会员卡图片（未激活）
	activatedPic	会员卡图片（激活）*/
	
	public String card_id;//	会员卡ID
	public String card_name;//	会员卡名称
	public String card_no;//	会员卡实例化编号
	public String time_from;//	有效期开始时间
	public String time_to;//	有效期结束时间
	public String card_num;//	会员卡人数
	public String card_surplus_num;//	会员卡剩余数量
	public String cluber_welfare;//	会员福利
	public String card_directions;//	使用说明
	public String nonactivatedPic;//	会员卡图片（未激活）
	public String activatedPic;//	会员卡图片（激活）
	
	public static ApplyCardResult from(JSONObject json) {
		ApplyCardResult result = null;
		if(json != null) {
			result = new ApplyCardResult();
			result.activatedPic = json.optString("activatedPic");
			result.card_directions = json.optString("card_directions");
			result.card_id = json.optString("card_id");
			result.card_name = json.optString("card_name");
			result.card_no = json.optString("card_no");
			result.card_num = json.optString("card_num");
			result.card_surplus_num = json.optString("card_surplus_num");
			result.cluber_welfare = json.optString("cluber_welfare");
			result.nonactivatedPic = json.optString("nonactivatedPic");
			result.time_from = json.optString("time_from");
			result.time_to = json.optString("time_to");
		}
		return result;
	}
}
