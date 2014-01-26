package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Card {
	public String card_id;
	public String card_name;
	public String card_content;
	public String nonactivatedPic;
	public String activatedPic;
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
	public String cardLevel;
	
	public ArrayList<Shop> shops = new ArrayList<Shop>();

	public static Card parse(JSONObject jsonObj) throws JSONException {
		Card ad = new Card();
		ad.card_id = jsonObj.optString("card_id");
		ad.card_name = jsonObj.optString("card_name");
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
		
		JSONArray array = jsonObj.optJSONArray("shops");
		if(array != null){
			int len = array.length();
			for(int i=0; i<len; i++){
				JSONObject o = array.getJSONObject(i);
				ad.shops.add(Shop.parse(o));
			}
		}
		
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
