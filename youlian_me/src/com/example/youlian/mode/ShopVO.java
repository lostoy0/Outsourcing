package com.example.youlian.mode;

import java.io.Serializable;

import org.json.JSONObject;

public class ShopVO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**经度*/
	public String lng;
	/**纬度*/
	public String lat;
	/**序号*/
	public String id;
	/**店名*/
	public String name;
	/**分店地址*/
	public String address;
	/**分店联系电话*/
	public String phone;

	public static ShopVO from(JSONObject json) {
		ShopVO vo = null;
		if(json != null) {
			vo = new ShopVO();
			vo.lat = json.optString("lat");
			vo.lng = json.optString("lng");
			vo.address = json.optString("address");
			vo.id = json.optString("id");
			vo.name = json.optString("name");
			vo.phone = json.optString("phone");
		}
		return vo;
	}


}
