package com.example.youlian.mode;

import java.io.Serializable;

import org.json.JSONObject;


public class RegioninfoVO implements Serializable {
	private static final long serialVersionUID = 6282864773782273412L;
	
	/**区域编码*/
	public String areaId;
	/**区域名称*/
	public String areaName;

	public static RegioninfoVO from(JSONObject json) {
		RegioninfoVO vo = null;
		if(json != null) {
			vo = new RegioninfoVO();
			vo.areaId = json.optString("areaId");
			vo.areaName = json.optString("areaName");
		}
		return vo;
	}
}
