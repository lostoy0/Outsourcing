package com.example.youlian.mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 活动
 * @author Administrator
 * 
 */
public class Act implements Serializable {
	public String id;
	public String title;
	public String startTime;
	public String endTime;
	public String is_hot;
	public String is_recommend;
	public String is_new;
	public String pic;
	public String pic_width;
	public String pic_height;
	public String category_id;
	public String description;
	public String instruction;
	public String shareContent;
	public String customerId;
	public String customerName;
	
	public String top;
	
	public ArrayList<Shop> shops = new ArrayList<Shop>();
	public String is_follow;
	public String telphone; 
	
	
	
	public static Act parse(JSONObject jsonObj) throws JSONException{
		Act pic = new Act();
		pic.id = jsonObj.optString("id");
		pic.title = jsonObj.optString("title");
		pic.startTime = jsonObj.optString("startTime");
		pic.endTime = jsonObj.optString("endTime");
		pic.is_hot = jsonObj.optString("is_hot");
		pic.is_recommend = jsonObj.optString("is_recommend");
		pic.is_new = jsonObj.optString("is_new");
		pic.pic = jsonObj.optString("pic");
		pic.pic_width = jsonObj.optString("pic_width");
		pic.pic_height = jsonObj.optString("pic_height");
		pic.category_id = jsonObj.optString("category_id");
		pic.description = jsonObj.optString("description");
		pic.instruction = jsonObj.optString("instruction");
		pic.shareContent = jsonObj.optString("shareContent");
		pic.customerId = jsonObj.optString("customerId");
		pic.customerName = jsonObj.optString("customerName"); 
		pic.top = jsonObj.optString("top");
		pic.is_follow = jsonObj.optString("is_follow");
		pic.telphone = jsonObj.optString("telphone");
		
		
		
		
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
