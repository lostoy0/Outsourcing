package com.example.youlian.mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Customer implements Serializable{
	private static final String TAG = "Customer";
	public String id;
	public String name;
	public String logo;
	public String introduce;
	public String isCoreCustomer;
	public String customerKindId;
	public String averagePrice;
	public String browseCount;
	public String userName;
	public String canBestpay;
	public String favEntityCount;
	public String cardEntityCount;
	public String favoritesCount;
	public String isHot;
	public String isRecommend;
	public String isNew;
	public String nearby;
	public String starLevel;
	public String provinceId;
	public String cityId;
	public String districtId;
	
	
	public ArrayList<Shop> shops = new ArrayList<Shop>();
	public List<String> images = new ArrayList<String>();
	public String comments;
	public String is_follow;
	public String brief;
	
	public static Customer parse(JSONObject jsonObj) throws JSONException {
		Customer ad = new Customer();
		ad.id = jsonObj.optString("id");
		ad.name = jsonObj.optString("name");
		ad.logo = jsonObj.optString("logo");
		ad.introduce = jsonObj.optString("introduce");
		ad.isCoreCustomer = jsonObj.optString("isCoreCustomer");
		ad.customerKindId = jsonObj.optString("customerKindId");
		ad.averagePrice = jsonObj.optString("averagePrice");
		ad.browseCount = jsonObj.optString("browseCount");
		ad.canBestpay = jsonObj.optString("canBestpay");
		ad.favEntityCount = jsonObj.optString("favEntityCount");
		ad.cardEntityCount = jsonObj.optString("cardEntityCount");
		ad.favoritesCount = jsonObj.optString("favoritesCount");
		ad.isHot = jsonObj.optString("isHot");
		ad.isRecommend = jsonObj.optString("isRecommend");
		ad.isNew = jsonObj.optString("isNew");
		ad.nearby = jsonObj.optString("nearby");
		ad.starLevel = jsonObj.optString("starLevel");
		ad.provinceId = jsonObj.optString("provinceId");
		ad.cityId = jsonObj.optString("cityId");
		ad.districtId = jsonObj.optString("districtId");
		
		ad.comments = jsonObj.optString("comments");
		ad.is_follow = jsonObj.optString("is_follow");
		ad.brief = jsonObj.optString("brief");
		
		JSONArray array = jsonObj.optJSONArray("shops");
		if(array != null){
			int len = array.length();
			for(int i=0; i<len; i++){
				JSONObject o = array.getJSONObject(i);
				ad.shops.add(Shop.parse(o));
			}
		}
		
		JSONArray arr = jsonObj.optJSONArray("pics");
		if(arr != null){
			int len = arr.length();
			for(int j=0; j<len; j++){
				ad.images.add(arr.get(j).toString());
				Log.i(TAG, arr.get(j).toString());
			}
		}
		
		return ad;
	}

	public static List<Customer> parse(String json) throws JSONException {
		if (json != null) {
			JSONObject o = new JSONObject(json);
			JSONArray array = o.optJSONArray("result");
			int size = array.length();
			List<Customer> list = new ArrayList<Customer>();
			for (int i = 0; i < size; i++) {
				JSONObject object = array.getJSONObject(i);
				list.add(parse(object));
			}
			return list;
		}
		return null;
	}
	public static List<Customer> parseList(JSONObject o) throws JSONException {
			JSONArray array = o.optJSONArray("result");
			int size = array.length();
			List<Customer> list = new ArrayList<Customer>();
			for (int i = 0; i < size; i++) {
				JSONObject object = array.getJSONObject(i);
				list.add(parse(object));
			}
			return list;
	}
}
