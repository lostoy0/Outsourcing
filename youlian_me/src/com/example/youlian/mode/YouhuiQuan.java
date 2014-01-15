package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YouhuiQuan {
	public String fav_ent_id;
	public String fav_ent_name;
	public String nonactivatedPic;
	public String activatedPic;
	public String use_date_from;
	public String use_date_to;
	public String apply_date_from;
	public String apply_date_to;
	
	
	
	public String money;
	public String fav_id;
	public String simple_description;
	public String participate_num;
	public String isHot;
	public String isRecommend;
	public String cat_id;
	public String isNew;
	public String isAuthenByMoblie;
	public String isLimitQuantity;
	public String isBuy;
	public String isAgio;
	public String isHotBuy;
	public String shareContent;
	public String provinceId;
	public String cityId;
	public String districtId;
	public String nearby;
	public List<Shop> shops = new ArrayList<Shop>();
	
	public static YouhuiQuan parse(JSONObject jsonObj) throws JSONException{
		YouhuiQuan pic = new YouhuiQuan();
		pic.fav_ent_id = jsonObj.optString("fav_ent_id");
		pic.fav_ent_name = jsonObj.optString("fav_ent_name");
		pic.nonactivatedPic = jsonObj.optString("nonactivatedPic");
		pic.activatedPic = jsonObj.optString("activatedPic");
		pic.use_date_from = jsonObj.optString("use_date_from");
		pic.use_date_to = jsonObj.optString("use_date_to");
		pic.apply_date_from = jsonObj.optString("apply_date_from");
		pic.apply_date_to = jsonObj.optString("apply_date_to");
		pic.money = jsonObj.optString("money");
		pic.fav_id = jsonObj.optString("fav_id");
		pic.simple_description = jsonObj.optString("simple_description");
		pic.participate_num = jsonObj.optString("participate_num");
		pic.isHot = jsonObj.optString("isHot");
		pic.isRecommend = jsonObj.optString("isRecommend");
		pic.cat_id = jsonObj.optString("cat_id");
		pic.isNew = jsonObj.optString("isNew");
		pic.isAuthenByMoblie = jsonObj.optString("isAuthenByMoblie");
		pic.isLimitQuantity = jsonObj.optString("isLimitQuantity");
		pic.isBuy = jsonObj.optString("isBuy");
		pic.isAgio = jsonObj.optString("isAgio");
		pic.isHotBuy = jsonObj.optString("isHotBuy");
		pic.shareContent = jsonObj.optString("shareContent");
		pic.provinceId = jsonObj.optString("provinceId");
		pic.cityId = jsonObj.optString("cityId");
		pic.districtId = jsonObj.optString("districtId");
		pic.nearby = jsonObj.optString("nearby");
		
		JSONArray array = jsonObj.optJSONArray("shops");
		if(array != null){
			int len = array.length();
			for(int i=0; i<len; i++){
				JSONObject o = array.getJSONObject(i);
				pic.shops.add(Shop.parse(o));
			}
		}
		return pic;
	}
}
