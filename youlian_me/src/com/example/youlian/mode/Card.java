package com.example.youlian.mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Card implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String card_id;
	public String card_name;
	public String card_no;
	public String card_content;
	public String nonactivatedPic;
	public String activatedPic;
	/**折扣信息*/
	public String agioInfo;	
	public String is_store_club;
	public String is_follow;
	public String is_league;
	public String category_id;
	public String is_new;
	public String is_hot;
	public String nearby;
	public String is_recommend;
	public String have_favourable;
	public String user_card;
	public String starLevel;
	public String canBestPay;
	public String canOnlinePay;
	public String payPolicy;
	public String shareContent;
	public String provinceId;
	public String cityId;
	public String districtId;
	
	public ArrayList<Shop> shops = new ArrayList<Shop>();
	
	
	public String card_num;
	public String card_surplus_num;
	public String time_from;
	public String time_to;
	public String card_directions;
	public String input_fileds;
	public String comments;
	/**商家ID*/
	public String customer_id;
	public String customer_brief;
	public String customer_introduce;
	
	/**申请方式	1-商家自发 2-商家自发+自主申请 3-自主申请 只有2，3方式的才能进行会员卡的编辑与修改*/
	public String applyWay;
	/**其他来源管理余额	如果此字段不为空，则我的卡包会员卡详情里的余额需要通过请求该URL来获取*/
	public String balanceUrl;
	/**其他计次来源	如果此字段不为空，则我的卡包会员卡详情里的消费需要通过请求该URL来获取*/
	public String countUrl;
	/**积分来源	如果此字段不为空，则我的卡包会员卡详情里的积分需要通过请求该URL来获取*/
	public String pointUrl;
	/**商家星级*/
	public String starLevle;
	
	/**余额*/
	public String myMoney;
	/**积分*/
	public String myScore;
	/**级别*/
	public String cardLevel;
	/**福利*/
	public String cluber_welfare;


	public static Card parse(JSONObject jsonObj) throws JSONException {
		Card ad = new Card();
		ad.card_id = jsonObj.optString("card_id");
		ad.card_name = jsonObj.optString("card_name");
		ad.card_no = jsonObj.optString("card_no");
		ad.card_content = jsonObj.optString("card_content");
		ad.nonactivatedPic = jsonObj.optString("nonactivatedPic");
		ad.activatedPic = jsonObj.optString("activatedPic");
		ad.agioInfo = jsonObj.optString("agioInfo");
		ad.is_store_club = jsonObj.optString("is_store_club");
		ad.is_follow = jsonObj.optString("is_follow");
		ad.is_league = jsonObj.optString("is_league");
		ad.category_id = jsonObj.optString("category_id");
		ad.is_new = jsonObj.optString("is_new");
		ad.is_hot = jsonObj.optString("is_hot");
		ad.nearby = jsonObj.optString("nearby");
		ad.is_recommend = jsonObj.optString("is_recommend");
		ad.have_favourable = jsonObj.optString("have_favourable");
		ad.user_card = jsonObj.optString("user_card");
		ad.starLevel = jsonObj.optString("starLevel");
		ad.canBestPay = jsonObj.optString("canBestPay");
		ad.canOnlinePay = jsonObj.optString("canOnlinePay");
		ad.payPolicy = jsonObj.optString("payPolicy");
		ad.shareContent = jsonObj.optString("shareContent");
		ad.provinceId = jsonObj.optString("provinceId");
		ad.cityId = jsonObj.optString("cityId");
		ad.districtId = jsonObj.optString("districtId");
		ad.cardLevel = jsonObj.optString("cardLevel");
		
		ad.applyWay = jsonObj.optString("applyWay");
		ad.balanceUrl = jsonObj.optString("balanceUrl");
		ad.countUrl = jsonObj.optString("countUrl");
		ad.pointUrl = jsonObj.optString("pointUrl");
		ad.starLevle = jsonObj.optString("starLevle");
		
		ad.myMoney = jsonObj.optString("myMoney");
		ad.myScore = jsonObj.optString("myScore");
		
		JSONArray array = jsonObj.optJSONArray("shop");
		if(array != null){
			int len = array.length();
			for(int i=0; i<len; i++){
				JSONObject o = array.getJSONObject(i);
				ad.shops.add(Shop.parse(o));
			}
		}
		
		ad.card_num = jsonObj.optString("card_num");
		ad.card_surplus_num = jsonObj.optString("card_surplus_num");
		ad.time_from = jsonObj.optString("time_from");
		ad.time_to = jsonObj.optString("time_to");
		ad.cluber_welfare = jsonObj.optString("cluber_welfare");
		ad.card_directions = jsonObj.optString("card_directions");
		ad.input_fileds = jsonObj.optString("input_fileds");
		ad.comments = jsonObj.optString("comments");
		ad.customer_id = jsonObj.optString("customer_id");
		ad.customer_brief = jsonObj.optString("customer_brief");
		ad.customer_introduce = jsonObj.optString("customer_introduce");
		
		return ad;
	}

	public static List<Card> parse(String json) throws JSONException {
		if (json != null) {
			JSONObject o = new JSONObject(json);
			JSONArray array = o.optJSONArray("result");
			int size = array.length();
			List<Card> list = new ArrayList<Card>();
			for (int i = 0; i < size; i++) {
				JSONObject object = array.getJSONObject(i);
				list.add(parse(object));
			}
			return list;
		}
		return null;
	}

}
