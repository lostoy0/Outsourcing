package com.example.youlian.mode;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Pic {
	public String pics;
	public String linkTypes;
	public String linkIds;
	
	public String url;
	public String width;
	public String height;
	
	public static Pic parse(JSONObject jsonObj) throws JSONException{
		Pic pic = new Pic();
		pic.pics = jsonObj.optString("pics");
		pic.linkTypes = jsonObj.optString("linkTypes");
		pic.linkIds = jsonObj.optString("linkIds");
		
		pic.width = jsonObj.optString("width");
		pic.height = jsonObj.optString("height");
		pic.url = jsonObj.optString("url");
		return pic;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "pic:" + pics + ", type:" + linkTypes + ", linkId:" + linkIds;
	}
}
