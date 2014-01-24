package com.example.youlian.mode;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class City {
	public String areaId;
	public String areaName;
	
	public static City parse(JSONObject jsonObj) throws JSONException{
		City pic = new City();
		pic.areaId = jsonObj.optString("areaId");
		pic.areaName = jsonObj.optString("areaName");
		return pic;
	}
	
}
