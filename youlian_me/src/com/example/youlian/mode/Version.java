package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Version {
	public String verAddress;
	public String verCode;
	public String verSize;
	public String verText;
	public boolean isForceUpgrade;
	
	public static Version parse(JSONObject jsonObj) throws JSONException{
		Version pic = new Version();
		pic.verAddress = jsonObj.optString("verAddress");
		pic.verCode = jsonObj.optString("verCode");
		pic.verSize = jsonObj.optString("verSize");
		pic.verText = jsonObj.optString("verText");
		pic.isForceUpgrade = jsonObj.optBoolean("isForceUpgrade");
		return pic;
	}
}
