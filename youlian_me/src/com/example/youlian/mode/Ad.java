package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ad {
	public String id;
	public String pic;
	public String linkType;
	public String linkUrl;
	public String linkId;
	public String top;
	public String message;

	public static Ad parse(JSONObject jsonObj) throws JSONException {
		Ad ad = new Ad();
		ad.id = jsonObj.optString("id");
		ad.pic = jsonObj.optString("pic");
		ad.linkType = jsonObj.optString("linkType");
		ad.linkUrl = jsonObj.optString("linkUrl");
		ad.linkId = jsonObj.optString("linkId");
		ad.top = jsonObj.optString("top");
		ad.message = jsonObj.optString("message");
		return ad;
	}

	public static List<Ad> parse(String json) throws JSONException {
		if (json != null) {
			JSONObject o = new JSONObject(json);
			JSONArray array = o.optJSONArray("result");
			int size = array.length();
			List<Ad> list = new ArrayList<Ad>();
			for (int i = 0; i < size; i++) {
				JSONObject object = array.getJSONObject(i);
				list.add(parse(object));
			}
			return list;
		}
		return null;
	}

}
