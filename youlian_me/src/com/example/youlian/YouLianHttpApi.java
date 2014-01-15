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

public class YouLianHttpApi {
	public static final String URL_ZHENSHI = "http://www.younion.com.cn/younionmember/router/router.action";
	public static final String URL_TEST = "http://test.younion.cn/younionmember/router.do";
//	public static final String URL_TEST = "http://118.244.146.145:8080/younionmember/router.do";
	// 
	
	
	public static final String KEY_SERVER = "service";
	public static final String KEY_CLIENT_TYPE = "client_type";
	private static final String TAG = "YouLianHttpApi";
	
	/**
	 * 首页广告
	 * @param zone
	 * @param successListener
	 * @param errorListener
	 */
	public static void getAdvertisement(String zone, Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.advertisement.get";
		String url = getUrl(KEY_SERVER, server, "zone",zone, KEY_CLIENT_TYPE,"android");
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 主题活动
	 * @param id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getSubjectActivity(Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.subject.active.get";
		String url = getUrl(KEY_SERVER, server, KEY_CLIENT_TYPE,"android");
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 所有商家活动
	 * @param successListener
	 * @param errorListener
	 */
	public static void getAllactivitys(Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.allactivitys.get";
		String url = getUrl(KEY_SERVER, server);
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	
	/**
	 * 会员卡实体列表
	 * @param userToken
	 * @param cityId
	 * @param successListener
	 * @param errorListener
	 */
	public static void getMemberCard(String userToken, String cityId, Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.cards.get";
		String url = getUrl("user_token", userToken, "city_id", cityId, KEY_SERVER, server, KEY_CLIENT_TYPE,"android");
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 检查版本
	 * @param successListener
	 * @param errorListener
	 */
	public static void checkVersion(Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.app.update";
		String url = getUrl(KEY_SERVER, server, KEY_CLIENT_TYPE,"android");
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	/**
	 * 服务条约
	 * @param successListener
	 * @param errorListener
	 */
	public static void getService(Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.service.term";
		String url = getUrl(KEY_SERVER, server);
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	
	/**
	 * 服务条约
	 * @param successListener
	 * @param errorListener
	 */
	public static void getAbout(Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.about.get";
		String url = getUrl(KEY_SERVER, server);
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	
	public static void feedBack(String user_token, String content, Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.message.add";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token, "content", content);
		Log.i(TAG, "url:" +  url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	
	
	public static void getYouhuiQuan(String user_token, String req_type, Response.Listener<String> successListener,
			Response.ErrorListener errorListener){
		String server = "younion.favents.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token, "req_type", req_type);
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
				appendFirstEncodedParams(buf, entry.getKey(),
						entry.getValue());
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
