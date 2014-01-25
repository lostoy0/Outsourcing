package com.example.youlian.mode;

import org.json.JSONObject;

/**
 * 
 * 登录注册返回结果
 * @Package com.example.youlian.mode
 * @ClassName: LoginResult
 * @author Raymond
 * @date 2014-1-24 下午2:09:33
 */
public class LoginResult{
	/**
	 * 登陆标识
	 */
	public String user_token;
	/**
	 * 用户昵称
	 */
	public String user_name;
	/**
	 * 用户积分
	 */
	public String user_integral;
	/**
	 * 用户类型
	 * 0普通用户1商家
	 */
	public String type;

	public static LoginResult from(JSONObject json) {
		LoginResult result = null;
		if(json != null) {
			result = new LoginResult();
			result.type = json.optString("type");
			result.user_token = json.optString("user_token");
			result.user_name = json.optString("user_name");
			result.user_integral = json.optString("user_integral");
		}
		return result;
	}
	
}
