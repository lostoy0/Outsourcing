package com.example.youlian;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.example.youlian.app.MyVolley;
import com.example.youlian.util.YlUtils;

public class YouLianHttpApi {
	public static final String URL_ZHENSHI = "http://www.younion.com.cn/younionmember/router/router.action";
	public static final String URL_TEST = "http://test.younion.cn/younionmember/router.do";
	// public static final String URL_TEST =
	// "http://118.244.146.145:8080/younionmember/router.do";
	//

	public static final String KEY_SERVER = "service";
	public static final String KEY_CLIENT_TYPE = "client_type";
	public static final String KEY_CLIENT = "client";
	private static final String TAG = "YouLianHttpApi";

	/**
	 * 首页广告
	 * 
	 * @param zone
	 * @param successListener
	 * @param errorListener
	 */
	public static void getAdvertisement(String zone,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.advertisement.get";
		String url = getUrl(KEY_SERVER, server, "zone", zone, KEY_CLIENT,
				"android");
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 主题活动
	 * 
	 * @param id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getSubjectActivity(
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.subject.active.get";
		String url = getUrl(KEY_SERVER, server, KEY_CLIENT_TYPE, "android");
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 所有商家活动
	 * 
	 * @param successListener
	 * @param errorListener
	 */
	public static void getAllactivitys(
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.allactivitys.get";
		String url = getUrl(KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 会员卡实体列表
	 * 
	 * @param userToken
	 * @param cityId
	 * @param successListener
	 * @param errorListener
	 */
	public static void getMemberCard(String userToken, String cityId,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.cards.get";
		String url = getUrl("user_token", userToken, "city_id", cityId,
				KEY_SERVER, server, KEY_CLIENT_TYPE, "android");
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 检查版本
	 * 
	 * @param successListener
	 * @param errorListener
	 */
	public static void checkVersion(Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.app.update";
		String url = getUrl(KEY_SERVER, server, KEY_CLIENT_TYPE, "android");
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 服务条约
	 * 
	 * @param successListener
	 * @param errorListener
	 */
	public static void getService(Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.service.term";
		String url = getUrl(KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 关于
	 * 
	 * @param successListener
	 * @param errorListener
	 */
	public static void getAbout(Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.about.get";
		String url = getUrl(KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 用户反馈
	 * 
	 * @param user_token
	 * @param content
	 * @param successListener
	 * @param errorListener
	 */
	public static void feedBack(String user_token, String content,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.message.add";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"content", content);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 优惠券列表
	 * 
	 * @param user_token
	 * @param req_type
	 * @param successListener
	 * @param errorListener
	 */
	public static void getYouhuiQuan(String user_token, String req_type,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.favents.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"req_type", req_type);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 优惠券详情页
	 * 
	 * @param user_token
	 * @param fav_ent_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getYouhuiQuanDetail(String user_token,
			String fav_ent_id, Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.favent.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"fav_ent_id", fav_ent_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 申请优惠券
	 * 
	 * @param user_token
	 * @param fav_ent_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void applyYouhuiQuan(String user_token, String fav_ent_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.fav.apply";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"fav_ent_id", fav_ent_id, KEY_CLIENT_TYPE, "android");
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 点评列表
	 * 
	 * @param customer_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getComment(String customer_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.comment.on.get";
		String url = getUrl(KEY_SERVER, server, "customer_id", customer_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 添加评论
	 * 
	 * @param customer_id
	 * @param user_token
	 * @param content
	 * @param star_level
	 * @param user_lng
	 * @param user_lat
	 * @param sign_type
	 * @param activity_id
	 * @param shop_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void comment(String customer_id, String user_token,
			String content, String star_level, String user_lng,
			String user_lat, String sign_type, String activity_id,
			String shop_id, Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.comment.on.add";
		String url = getUrl(KEY_SERVER, server, "customer_id", customer_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 首页城市接口
	 * @param successListener
	 * @param errorListener
	 */
	public static void getArea(Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.app.area.get";
		String url = getUrl(KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 三级区域接口
	 * @param province_id
	 * @param city_id
	 * @param district_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getAreaByProvinceIdCid(
			String province_id,
			String city_id,
			String district_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.area.get";
		String url = getUrl(KEY_SERVER, server, "province_id", province_id, "city_id", city_id, "district_id", district_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	
	
	/* 注册
	 * @param login_id 登录名（手机号码）
	 * @param password 密码（需md5加密）
	 * @param code验证码
	 * @param successListener
	 * @param errorListener
	 */
	public static void register(String login_id, String password, String code, 
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.register";
		String url = getUrl(KEY_SERVER, server, "login_id", login_id, "password", YlUtils.md5(password), "code", code);
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 登录
	 * @param login_id登录名（手机号码）
	 * @param password密码
	 * @param successListener
	 * @param errorListener
	 */
	public static void login(String login_id, String password, 
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.login";
		String url = getUrl(KEY_SERVER, server, "login_id", login_id, "password", YlUtils.md5(password));
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 获取验证码
	 * @param mobile 手机号码
	 * @param type类型（0：短信验证码 1：IVR语音验证码）
	 * @param successListener
	 * @param errorListener
	 */
	public static void getVerifyCode(String mobile, String type,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.validator.code.get";
		String url = getUrl(KEY_SERVER, server, "mobile", mobile, "type", type);
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 找回密码
	 * @param login_id登录名（手机号码）
	 * @param successListener
	 * @param errorListener
	 */
	public static void seekPassword(String login_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.user.password.seek";
		String url = getUrl(KEY_SERVER, server, "login_id", login_id);
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 修改密码
	 * @param user_token
	 * @param old_password
	 * @param new_password
	 * @param successListener
	 * @param errorListener
	 */
	public static void modifyPassword(String user_token, String old_password, String new_password,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.user.password.modify";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token, 
				"old_password", YlUtils.md5(old_password), "new_password", YlUtils.md5(new_password));
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	

	public static String getUrl(String... keyValues) {
		// get base url
		StringBuilder buf = new StringBuilder(URL_TEST);
		Map<String, String> params = new HashMap<String, String>();
		// api_key=87d5d0947f5cac0f96109353e64c1688&api_sign=63bd0a3d5c4e0fab882afdb994ea696c
		params.put("api_key", "87d5d0947f5cac0f96109353e64c1688");
		params.put("api_sign", "63bd0a3d5c4e0fab882afdb994ea696c");
		
		// add parameters
		int count = keyValues.length;
		for (int i = 0; i < count; i += 2) {
			if (!TextUtils.isEmpty(keyValues[i + 1]))
				params.put(keyValues[i], keyValues[i + 1]);
		}
		// append to url
		boolean first = true;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (first) {
				appendFirstEncodedParams(buf, entry.getKey(), entry.getValue());
				first = false;
			} else {
				appendEncodedParams(buf, entry.getKey(), entry.getValue());
			}
		}
		return buf.toString();
	}

	/**
	 * Utility method for assembling a parameter string
	 */
	private static void appendFirstEncodedParams(StringBuilder param,
			String key, String val) {
		try {
			if (val != null && val.length() > 0) {
				if (param.length() > 0)
					param.append('?');
				param.append(key).append("=")
						.append(URLEncoder.encode(val, "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Utility method for assembling a parameter string
	 */
	private static void appendEncodedParams(StringBuilder param, String key,
			String val) {
		try {
			if (val != null && val.length() > 0) {
				if (param.length() > 0)
					param.append('&');
				param.append(key).append("=")
						.append(URLEncoder.encode(val, "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
