package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.youlian.common.Constants;

public class ActivitySign {
	/**活动ID*/	
	public String id;	
	/**活动标题*/
	public String title;
	/**活动LOGO*/
	public String logo;		
	/**活动内容*/
	public String content;	
	/**活动开始时间*/
	public String begtime;	
	/**活动结束时间*/
	public String endtime;		
	/**签到成功所赠送的礼包名称*/
	public String favourableGiftName;
	/**所属商家ID*/
	public String customerId;	
	/**所属商家*/
	public String customerName;
	/**分店信息	对象ShopVO属性有（id：序号name：店名address：分店地址phone：分店联系电话lng：分店经度lat：分店纬度）*/
	public List<ShopVO> shop;	
	/**分享时的内容	html*/
	public String shareContent;
	
	public static ActivitySign from(JSONObject json) throws JSONException {
		ActivitySign sign = null;
		if(json != null) {
			sign = new ActivitySign();
			sign.begtime = json.optString("begtime");
			sign.content = json.optString("content");
			sign.customerId = json.optString("customerId");
			sign.customerName = json.optString("customerName");
			sign.endtime = json.optString("endtime");
			sign.favourableGiftName = json.optString("favourableGiftName");
			sign.id = json.optString("id");
			sign.logo = json.optString("logo");
			sign.shareContent = json.optString("shareContent");
			sign.title = json.optString("title");
			
			JSONArray array = json.optJSONArray("shop");
			if(array != null && array.length() > 0) {
				List<ShopVO> vos = new ArrayList<ShopVO>();
				for(int i=0; i<array.length(); i++) {
					ShopVO vo = ShopVO.from(array.getJSONObject(i));
					if(vo != null) {
						vos.add(vo);
					}
				}
				if(vos.size() > 0) sign.shop = vos;
			}
		}
		return sign;
	}

	public static List<ActivitySign> getList(JSONObject json) throws JSONException {
		List<ActivitySign> signs = null;
		if(json != null) {
			int status = json.optInt(Constants.key_status);
			if(status == 1) {
				JSONArray array = json.optJSONArray(Constants.key_result);
				if(array != null && array.length() > 0) {
					signs = new ArrayList<ActivitySign>();
					for(int i=0; i<array.length(); i++) {
						ActivitySign all = ActivitySign.from(array.getJSONObject(i));
						if(all != null) signs.add(all);
					}
				}
			}
		}
		return signs;
	}
	
}
