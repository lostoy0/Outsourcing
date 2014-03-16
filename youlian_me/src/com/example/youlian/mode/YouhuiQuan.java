package com.example.youlian.mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YouhuiQuan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public ArrayList<Shop> shops = new ArrayList<Shop>();
	public String fav_detail;
	public String valid_time_from;
	public String valid_time_to;
	public String amount;
	public String is_favorites;
	public String sellingPrice;
	public String comments;
	public String customer_id;
	public String starLevel;
	public String fav_name;
	
	public String customer_brief;
	public String customer_introduce;
	public String customerName;
	
	public List<YouhuiQuan> moreYouHuiquan = new ArrayList<YouhuiQuan>(); 
	
	public static YouhuiQuan parse(JSONObject jsonObj) throws JSONException{
		YouhuiQuan pic = new YouhuiQuan();
		pic.fav_ent_id = jsonObj.optString("fav_ent_id");
		pic.fav_ent_name = jsonObj.optString("fav_ent_name");
		pic.nonactivatedPic = jsonObj.optString("nonactivatedPic");
		pic.activatedPic = jsonObj.optString("activatedPic");
		
		String result = jsonObj.optString("use_date_from");
		pic.use_date_from = result.substring(0, result.indexOf(" "));
		result = jsonObj.optString("use_date_to");
		pic.use_date_to = result.substring(0, result.indexOf(" "));
		result = jsonObj.optString("apply_date_from");
		pic.apply_date_from = result.substring(0, result.indexOf(" "));
		result = jsonObj.optString("apply_date_to");;
		pic.apply_date_to =  result.substring(0, result.indexOf(" "));
		
		
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
		
		
		pic.fav_detail = jsonObj.optString("fav_detail");
		
		pic.valid_time_from = jsonObj.optString("valid_time_from");
		pic.valid_time_to = jsonObj.optString("valid_time_to");
		pic.amount = jsonObj.optString("amount");
		
		
		pic.is_favorites = jsonObj.optString("is_favorites");
		
		pic.sellingPrice = jsonObj.optString("sellingPrice");
		
		
		pic.comments = jsonObj.optString("comments");
		pic.customer_id = jsonObj.optString("customer_id");
		pic.starLevel = jsonObj.optString("starLevel");
		pic.fav_name = jsonObj.optString("fav_name");
		
		pic.customer_brief = jsonObj.optString("customer_brief");
		pic.customer_introduce = jsonObj.optString("customer_introduce");
		
		pic.customerName = jsonObj.optString("customerName");
		
		
		
		JSONArray jArray = jsonObj.optJSONArray("otherFavEntListResult");
		if(jArray != null){
			int len = jArray.length();
			for(int i=0; i<len; i++){
				JSONObject jo = jArray.getJSONObject(i);
				pic.moreYouHuiquan.add(parse(jo));
			}
		}
		
		return pic;
	}
	
	public static List<YouhuiQuan> parse(String json) throws JSONException {
		if (json != null) {
			JSONObject o = new JSONObject(json);
			JSONArray array = o.optJSONArray("result");
			int size = array.length();
			List<YouhuiQuan> list = new ArrayList<YouhuiQuan>();
			for (int i = 0; i < size; i++) {
				JSONObject object = array.getJSONObject(i);
				list.add(parse(object));
			}
			return list;
		}
		return null;
	}
}
