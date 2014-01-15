package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Shop {
	public String id;
	public String name;
	public String address;
	public String phone;
	public String lng;
	public String lat;
	
	public static Shop parse(JSONObject jsonObj) throws JSONException{
		Shop pic = new Shop();
		pic.id = jsonObj.optString("id");
		pic.name = jsonObj.optString("name");
		pic.address = jsonObj.optString("address");
		pic.phone = jsonObj.optString("phone");
		pic.lng = jsonObj.optString("lng");
		pic.lat = jsonObj.optString("lat");
		return pic;
	}
}
