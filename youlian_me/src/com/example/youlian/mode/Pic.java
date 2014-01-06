package com.example.youlian.mode;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Pic {
	public String pics;
	public String linkTypes;
	public String linkIds;
	
	public static Pic parse(JSONObject jsonObj) throws JSONException{
		Pic pic = new Pic();
		pic.pics = jsonObj.optString("pics");
		pic.linkTypes = jsonObj.optString("linkTypes");
		pic.linkIds = jsonObj.optString("linkIds");
		return pic;
	}
}
