package com.example.youlian.mode;

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
	private String user_token;
	/**
	 * 用户昵称
	 */
	private String user_name;
	/**
	 * 用户积分
	 */
	private String user_integral;
	/**
	 * 用户类型
	 * 0普通用户1商家
	 */
	private String type;
	

	public String getUser_token() {
		return user_token;
	}

	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_integral() {
		return user_integral;
	}

	public void setUser_integral(String user_integral) {
		this.user_integral = user_integral;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
