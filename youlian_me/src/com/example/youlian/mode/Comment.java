package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Comment {
	public String id;
	public String customerId;
	public String loginId;
	public String content;
	public String starLevel;
	public String addTime;
	public String picCount;
	public String pic;
	public String userName;

	public static Comment parse(JSONObject jsonObj) throws JSONException {
		Comment ad = new Comment();
		ad.id = jsonObj.optString("id");
		ad.customerId = jsonObj.optString("customerId");
		ad.loginId = jsonObj.optString("loginId");
		ad.content = jsonObj.optString("content");
		ad.starLevel = jsonObj.optString("starLevel");
		ad.addTime = jsonObj.optString("addTime");
		ad.picCount = jsonObj.optString("picCount");
		ad.userName = jsonObj.optString("userName");
		
		return ad;
	}

	public static List<Comment> parse(String json) throws JSONException {
		if (json != null) {
			JSONObject o = new JSONObject(json);
			JSONArray array = o.optJSONArray("result");
			int size = array.length();
			List<Comment> list = new ArrayList<Comment>();
			for (int i = 0; i < size; i++) {
				JSONObject object = array.getJSONObject(i);
				list.add(parse(object));
			}
			return list;
		}
		return null;
	}

}
