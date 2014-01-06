package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubjectActivity {

	public List<Pic> pics;
	public String title;
	public int activeTemplate;
	
	
	public static List<SubjectActivity> parse(String json) throws JSONException{
		if(json != null){
			JSONObject o = new JSONObject(json);
			return parse(o);
		}
		return null;
	}
	
	
	public static List<SubjectActivity> parse(JSONObject jsonObj) throws JSONException{
		JSONArray array = jsonObj.optJSONArray("result");
		int size = array.length();
		List<SubjectActivity> list = new ArrayList<SubjectActivity>();
		for(int i=0; i<size; i++){
			JSONObject object = array.getJSONObject(i);
			SubjectActivity subject = new SubjectActivity();
			if(object != null){
				subject.title = object.optString("title");
				subject.activeTemplate = object.optInt("activeTemplate");
				JSONArray jarray = object.optJSONArray("picsList");
				int length = jarray.length();
				Pic pic = null;
				List<Pic> pList = new ArrayList<Pic>();
				for(int j=0; j<length; j++){
					pic = new Pic();
					JSONObject jo = jarray.getJSONObject(j);
					pList.add(Pic.parse(jo));
				}
				subject.pics = pList;
			}
			list.add(subject);
		}
		
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
