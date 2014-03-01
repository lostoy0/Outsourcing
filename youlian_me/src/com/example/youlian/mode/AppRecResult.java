package com.example.youlian.mode;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
/**
 *  关于我们
 * @author wells
 *
 */
public class AppRecResult {
	public String id	;//应用ID
	public String appName	;//应用名称
	public String introduction	;//应用简介
	public String pic	;//应用图片
	public String url	;//应用下载地址
	
	
	public static AppRecResult parse(JSONObject jsonObj) throws JSONException{
		AppRecResult pic = new AppRecResult();
		pic.id = jsonObj.optString("id");
		pic.appName = jsonObj.optString("appName");
		pic.introduction = jsonObj.optString("introduction");
		pic.pic = jsonObj.optString("pic");
		pic.url = jsonObj.optString("url");
		return pic;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
