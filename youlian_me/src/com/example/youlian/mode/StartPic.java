package com.example.youlian.mode;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class StartPic {
	public String begTime;
	public String endTime;
	public String pic;
	
	public static StartPic parse(JSONObject jsonObj) throws JSONException{
		StartPic pic = new StartPic();
		pic.begTime = jsonObj.optString("begTime");
		pic.endTime = jsonObj.optString("endTime");
		pic.pic = jsonObj.optString("pic");
		return pic;
	}
	
	
}
