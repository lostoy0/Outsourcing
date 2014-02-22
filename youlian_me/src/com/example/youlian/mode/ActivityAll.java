package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.youlian.common.Constants;

public class ActivityAll {
	/**活动ID*/	
	public String id;	
	/**活动标题	*/
	public String title;	
	/**活动有效期开始时间*/	
	public String startTime;
	/**活动有效期结束时间*/	
	public String endTime;
	/**是否热门*/	
	public boolean is_hot;	
	/**是否推荐*/	
	public boolean is_recommend;	
	/**该活动所属商家下所有店面	内嵌对象属性（经度：lng ；纬度：lat）*/
	public List<ShopVO> shop;	
	/**活动图片（列表的图片客户端需要按比例缩放）*/	
	public String pic;	
	/**商家分类*/	
	public int category_id;	
	/**活动介绍*/	
	public String description;
	/**活动说明*/	
	public String instruction;	
	/**分享时的内容	html*/
	public String shareContent;	
	/**活动图片宽度*/
	public int pic_width;		
	/**活动图片高度*/
	public int pic_height;		
	/**所属商家ID*/	
	public String customerId;	
	/**所属商家名称*/
	public String customerName;		
	
	public static ActivityAll from(JSONObject json) throws JSONException {
		ActivityAll all = null;
		if(json != null) {
			all = new ActivityAll();
			all.category_id = json.optInt("category_id");
			all.customerId = json.optString("customerId");
			all.customerName = json.optString("customerName");
			all.description = json.optString("description");
			all.endTime = json.optString("endTime");
			all.id = json.optString("id");
			all.instruction = json.optString("instruction");
			all.is_hot = json.optBoolean("is_hot");
			all.is_recommend = json.optBoolean("is_recommend");
			all.pic = json.optString("pic");
			all.pic_height = json.optInt("pic_height");
			all.pic_width = json.optInt("pic_width");
			all.shareContent = json.optString("shareContent");
			all.startTime = json.optString("startTime");
			all.title = json.optString("title");
			
			JSONArray array = json.optJSONArray("shop");
			if(array != null && array.length() > 0) {
				List<ShopVO> vos = new ArrayList<ShopVO>();
				for(int i=0; i<array.length(); i++) {
					ShopVO vo = ShopVO.from(array.getJSONObject(i));
					if(vo != null) {
						vos.add(vo);
					}
				}
				if(vos.size() > 0) all.shop = vos;
			}
			
		}
		return all;
	}
	
	public static List<ActivityAll> getList(JSONObject json) throws JSONException {
		List<ActivityAll> activityAlls = null;
		if(json != null) {
			int status = json.optInt(Constants.key_status);
			if(status == 1) {
				JSONArray array = json.optJSONArray(Constants.key_result);
				if(array != null && array.length() > 0) {
					activityAlls = new ArrayList<ActivityAll>();
					for(int i=0; i<array.length(); i++) {
						ActivityAll all = ActivityAll.from(array.getJSONObject(i));
						if(all != null) activityAlls.add(all);
					}
				}
			}
		}
		return activityAlls;
	}
}
