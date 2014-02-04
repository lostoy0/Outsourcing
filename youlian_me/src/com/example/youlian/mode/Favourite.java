package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.example.youlian.common.Constants;

/**
 * 收藏条目
 * @author raymond
 *
 */
public class Favourite {
	/**关注的会员卡或者优惠券ID*/
	public String favour_id;
	/**关注的会员卡或者优惠券名称*/
	public String name;
	/**关注的类型：1为会员卡2为优惠券 3为商家 4为普通活动 5 签到活动*/
	public int type;	
	/**是否申请过：1为申请 0为未申请*/
	public int apply;
	/**会员卡/优惠券图片（未激活）*/
	public String nonactivatedPic;
	/**会员卡/优惠券图片（激活）*/
	public String activatedPic;
	/**会员卡/优惠券被关注的人数*/
	public int amount;

	public static Favourite from(JSONObject json) {
		if(json != null) {
			Favourite favourite = new Favourite();
			favourite.favour_id = json.optString("favour_id");
			favourite.name = json.optString("name");
			favourite.type = json.optInt("type");
			favourite.apply = json.optInt("apply");
			favourite.nonactivatedPic = json.optString("nonactivatedPic");
			favourite.activatedPic = json.optString("activatedPic");
			favourite.amount = json.optInt("amount");
		}
		return null;
	}
	
	public static List<Favourite> from(String response) {
		if(!TextUtils.isEmpty(response)) {
			try {
				JSONObject jsonObject = new JSONObject(response);
				if("1".equals(jsonObject.optString(Constants.key_status))) {
					JSONArray array = jsonObject.optJSONArray(Constants.key_result);
					if(array != null && array.length() > 0) {
						List<Favourite> favourites = new ArrayList<Favourite>();
						for(int i=0; i<array.length(); i++) {
							favourites.add(Favourite.from(array.getJSONObject(i)));
						}
						return favourites;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		return null;
	}
}
