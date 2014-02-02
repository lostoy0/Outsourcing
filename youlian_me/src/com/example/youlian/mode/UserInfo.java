package com.example.youlian.mode;

import org.json.JSONObject;

public class UserInfo {
	public String id	;//用户ID
	public String userName	;//用户名称
	public String phone	;//联系电话
	public String email	;//电子邮件
	public String logo	;//用户头像
	public  RegioninfoVO province	;//所属省份
	public  RegioninfoVO city;//	所属市级
	public  RegioninfoVO district	;//所属城区
	
	public static UserInfo from(JSONObject json) {
		UserInfo info = null;
		if(json != null) {
			info = new UserInfo();
			info.id = json.optString("id");
			info.userName = json.optString("userName");
			info.phone = json.optString("phone");
			info.email = json.optString("email");
			info.logo = json.optString("logo");
			
			JSONObject provinceObject = json.optJSONObject("province");
			if(provinceObject != null) {
				RegioninfoVO province = new RegioninfoVO();
				province.areaId = provinceObject.optString("regionId");
				province.areaName = provinceObject.optString("region");
				info.province = province;
			}
			
			JSONObject cityObject = json.optJSONObject("city");
			if(provinceObject != null) {
				RegioninfoVO city = new RegioninfoVO();
				city.areaId = cityObject.optString("regionId");
				city.areaName = cityObject.optString("region");
				info.city = city;
			}
			
			JSONObject districtObject = json.optJSONObject("district");
			if(provinceObject != null) {
				RegioninfoVO district = new RegioninfoVO();
				district.areaId = districtObject.optString("regionId");
				district.areaName = districtObject.optString("region");
				info.district = district;
			}
		}
		return info;
	}
}
