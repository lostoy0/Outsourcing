package com.example.youlian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
	 * 后去推送信息
	 * @param device_token
	 * @param successListener
	 * @param errorListener
	 */
	public static void getPushMsg(String device_token,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.app.android.push";
		String url = getUrl(KEY_SERVER, server, "device_token", device_token);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		myReq.setShouldCache(false);
		queue.add(myReq);
	}
	
	public static void bindDeviceId(String user_token,
			String device_token,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.app.iphone.device.add";
		String url = getUrl(KEY_SERVER, server, "device_token", device_token
				,"user_token", user_token, KEY_CLIENT_TYPE, "android");
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}
	

	/**
	 * 应用推荐
	 * @param successListener
	 * @param errorListener
	 */
	public static void getRecommandApp(
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.app.mark";
		String url = getUrl(KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	public static void getInitInfo(String resolution,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		// resolution=480_800

		String server = "younion.app.init";
		String url = getUrl(KEY_SERVER, server, "resolution", resolution);
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
		myReq.setShouldCache(false);
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
		myReq.setShouldCache(false);
		queue.add(myReq);
	}

	public static void getActDetail(String id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.activity.get";
		String url = getUrl(KEY_SERVER, server, "id", id);
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
		myReq.setShouldCache(false);
		queue.add(myReq);
	}

	public static void getMemberCardDetail(String user_token, String card_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.card.get";
		String url = getUrl("user_token", user_token, "card_id", card_id,
				KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		myReq.setShouldCache(false);
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
		myReq.setShouldCache(false);
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
		myReq.setShouldCache(false);
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
		myReq.setShouldCache(false);
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
	 *            （1：签到 2：只评论）
	 * @param activity_id
	 * @param shop_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void comment(final String user_token,
			final String customer_id, final String content,
			final String star_level, final String user_lng,
			final String user_lat, final String sign_type,
			final String activity_id, final String shop_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		final String server = "younion.comment.on.add";
		String url = getUrl();
		// String url = getUrl(KEY_SERVER, server,
		// "user_token", user_token,
		// "customer_id", customer_id,
		// "content", content,
		// "star_level", star_level,
		// "user_lng", user_lng,
		// "user_lat", user_lat,
		// KEY_CLIENT_TYPE, "android",
		// "sign_type", sign_type,
		// "activity_id", activity_id,
		// "shop_id", shop_id
		// );
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.POST, url.toString(),
				successListener, errorListener) {

			@Override
			public String getBodyContentType() {
				return "multipart/form-data";
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("api_key", "87d5d0947f5cac0f96109353e64c1688");
				params.put("api_sign", "63bd0a3d5c4e0fab882afdb994ea696c");
				params.put(KEY_SERVER, server);
				params.put("user_token", user_token);
				params.put("customer_id", customer_id);
				params.put("content", content);
				params.put("star_level", star_level);
				params.put("user_lng", user_lng);
				params.put("user_lat", user_lat);
				params.put(KEY_CLIENT_TYPE, "android");
				params.put("sign_type", sign_type);
				params.put("activity_id", activity_id);
				params.put("shop_id", shop_id);
				return params;
			}
		};
		myReq.setShouldCache(false);
		queue.add(myReq);
	}

	public static void comment2(final String user_token,
			final String customer_id, final String content,
			final String star_level, final String user_lng,
			final String user_lat, final String sign_type,
			final String activity_id, final String shop_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		final String server = "younion.comment.on.add";
		String url = getUrl();
		Log.i(TAG, "url:" + url);
		// RequestQueue queue = MyVolley.getRequestQueue();
		// StringRequest myReq = new StringRequest(Method.POST, url.toString(),
		// successListener, errorListener){
		// @Override
		// protected Map<String, String> getParams()
		// throws AuthFailureError {
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("api_key", "87d5d0947f5cac0f96109353e64c1688");
		// params.put("api_sign", "63bd0a3d5c4e0fab882afdb994ea696c");
		// params.put(KEY_SERVER, server);
		// params.put("user_token", user_token);
		// params.put("customer_id", customer_id);
		// params.put("content", content);
		// params.put("star_level", star_level);
		// params.put("user_lng", user_lng);
		// params.put("user_lat", user_lat);
		// params.put(KEY_CLIENT_TYPE, "android");
		// params.put("sign_type", sign_type);
		// params.put("activity_id", activity_id);
		// params.put("shop_id", shop_id);
		// return params;
		// }
		//
		// };
		// queue.add(myReq);

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(url);
		MultipartEntity reqEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			reqEntity.addPart("api_key", new StringBody(
					"87d5d0947f5cac0f96109353e64c1688"));
			reqEntity.addPart("api_sign", new StringBody(
					"63bd0a3d5c4e0fab882afdb994ea696c"));
			reqEntity.addPart(KEY_SERVER, new StringBody(server));

			reqEntity.addPart("user_token", new StringBody(user_token));
			reqEntity.addPart("customer_id", new StringBody(customer_id));
			reqEntity.addPart("content", new StringBody(content));
			reqEntity.addPart("star_level", new StringBody(star_level));

			reqEntity.addPart(KEY_CLIENT_TYPE, new StringBody("android"));
			reqEntity.addPart("sign_type", new StringBody(sign_type));
			// try{
			// ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// bitmap.compress(CompressFormat.JPEG, 75, bos);
			// byte[] data = bos.toByteArray();
			// ByteArrayBody bab = new ByteArrayBody(data, "kfc.jpg");
			// reqEntity.addPart("image", bab);
			// }
			// catch(Exception e){
			// reqEntity.addPart("image", new StringBody("image error"));
			// }
			postRequest.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(postRequest);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String sResponse;
			StringBuilder s = new StringBuilder();
			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
			}
			Log.i(TAG, s.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 首页城市接口
	 * 
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
	 * 
	 * @param province_id
	 * @param city_id
	 * @param district_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getAreaByProvinceIdCid(String province_id,
			String city_id, String district_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.area.get";
		String url = getUrl(KEY_SERVER, server, "province_id", province_id,
				"city_id", city_id, "district_id", district_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 注册
	 * 
	 * @param login_id
	 *            登录名（手机号码）
	 * @param password
	 *            密码（需md5加密）
	 * @param code验证码
	 * @param successListener
	 * @param errorListener
	 */
	public static void register(String login_id, String password, String code,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.register";
		String url = getUrl(KEY_SERVER, server, "login_id", login_id,
				"password", YlUtils.md5(password), "code", code);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 登录
	 * 
	 * @param login_id登录名
	 *            （手机号码）
	 * @param password密码
	 * @param successListener
	 * @param errorListener
	 */
	public static void login(String login_id, String password,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.login";
		String url = getUrl(KEY_SERVER, server, "login_id", login_id,
				"password", YlUtils.md5(password));
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 获取验证码
	 * 
	 * @param mobile
	 *            手机号码
	 * @param type类型
	 *            （0：短信验证码 1：IVR语音验证码）
	 * @param successListener
	 * @param errorListener
	 */
	public static void getVerifyCode(String mobile, String type,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.validator.code.get";
		String url = getUrl(KEY_SERVER, server, "mobile", mobile, "type", type);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 找回密码
	 * 
	 * @param login_id登录名
	 *            （手机号码）
	 * @param successListener
	 * @param errorListener
	 */
	public static void seekPassword(String login_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.user.password.seek";
		String url = getUrl(KEY_SERVER, server, "login_id", login_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 修改密码
	 * 
	 * @param user_token
	 * @param old_password
	 * @param new_password
	 * @param successListener
	 * @param errorListener
	 */
	public static void modifyPassword(String user_token, String old_password,
			String new_password, Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.user.password.modify";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"old_password", YlUtils.md5(old_password), "new_password",
				YlUtils.md5(new_password));
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 
	 * 获取“我的”页面信息
	 * 
	 * @Title: modifyPassword
	 * @param user_token
	 * @param successListener
	 * @param errorListener
	 * @return void
	 * @date 2014-1-24 下午8:44:37
	 */
	public static void getMyInfo(String user_token,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.my.info.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		myReq.setShouldCache(false);
		queue.add(myReq);
	}

	/**
	 * 
	 * @param user_token
	 * @param favour_id
	 * @param type
	 *            关注的类型 1：卡 2: 优惠券 3：商家
	 * @param successListener
	 * @param errorListener
	 */
	public static void addFav(String user_token, String favour_id, String type,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.favorites.add";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"favour_id", favour_id, "type", type);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	public static void delFav(String user_token, String favour_id, String type,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.favorites.cancel";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"favour_id", favour_id, "type", type);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 注册用户资料获取接口
	 * 
	 * @param user_token
	 * @param successListener
	 * @param errorListener
	 */
	public static void getUserInfo(String user_token,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.user.info.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 注册用户资料修改接口
	 * 
	 * @param user_token
	 * @param userName
	 * @param phone
	 * @param email
	 * @param province_id
	 * @param city_id
	 * @param district_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void updateUserInfo(String user_token, String userName,
			String phone, String email, String province_id, String city_id,
			String district_id, Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.user.info.update";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"userName", userName, "phone", phone, "email", email,
				"province_id", province_id, "city_id", city_id, "district_id",
				district_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 获取收藏列表
	 * 
	 * @param user_token
	 * @param successListener
	 * @param errorListener
	 */
	public static void getFavouriteList(String user_token,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.favorites.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		myReq.setShouldCache(false);
		queue.add(myReq);
	}

	/**
	 * 我的卡包会员卡列表
	 * 
	 * @param user_token
	 * @param successListener
	 * @param errorListener
	 */
	public static void getCardList(String user_token,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.cards.mine.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		myReq.setShouldCache(false);
		queue.add(myReq);
	}

	/**
	 * 我的卡包会员卡详情
	 * 
	 * @param card_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getCardDetail(String card_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.card.mine.get";
		String url = getUrl(KEY_SERVER, server, "card_id", card_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 会员卡资料获取接口
	 * 
	 * @param card_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getCardInfo(String card_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.mycard.info.get";
		String url = getUrl(KEY_SERVER, server, "card_id", card_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * @param card_id
	 *            实例卡ID
	 * @param memberName
	 *            名字
	 * @param memberSex
	 *            性别 1-男 0-女
	 * @param memberBirth
	 *            生日
	 * @param memberPhone
	 *            手机号码
	 * @param memberEmail
	 *            Email
	 * @param memberAddress
	 *            联系地址
	 * @param memberQq
	 *            QQ
	 * @param membeIdCard
	 *            身份证号
	 * @param isAllowPush
	 *            是否允许推送（1：允许 0：不允许）
	 * @param successListener
	 * @param errorListener
	 */
	public static void editCardInfo(String card_id, String memberName,
			String memberSex, String memberBirth, String memberPhone,
			String memberEmail, String memberAddress, String memberQq,
			String membeIdCard, String isAllowPush,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.mycard.info.edit";
		String url = getUrl(KEY_SERVER, server, "card_id", card_id,
				"memberName", memberName, "memberSex", memberSex,
				"memberBirth", memberBirth, "memberPhone", memberPhone,
				"memberEmail", memberEmail, "memberAddress", memberAddress,
				"memberQq", memberQq, "membeIdCard", membeIdCard,
				"isAllowPush", isAllowPush);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 我的卡包优惠券列表
	 * 
	 * @param user_token
	 * @param successListener
	 * @param errorListener
	 */
	public static void getCouponList(String user_token,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.favs.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		myReq.setShouldCache(false);
		queue.add(myReq);
	}

	/**
	 * 我的卡包优惠券详情
	 * 
	 * @param user_token
	 * @param fav_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getCouponDetail(String user_token, String fav_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.fav.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"fav_id", fav_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 移除卡包接口
	 * 
	 * @param id
	 * @param type
	 *            类型（1：将会员卡移除卡包 2：将优惠券移除卡包）
	 * @param successListener
	 * @param errorListener
	 */
	public static void deleteCard(String id, String type,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.mycard.delete";
		String url = getUrl(KEY_SERVER, server, "id", id, "type", type);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 会员卡消费接口
	 * 
	 * @param card_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void costCard(String card_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.mycard.cost";
		String url = getUrl(KEY_SERVER, server, "card_id", card_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * U点U币相关规则
	 * 
	 * @param successListener
	 * @param errorListener
	 */
	public static void getCoinRule(Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.dotcoin.rule.get";
		String url = getUrl(KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * U点兑换U币
	 * 
	 * @param user_token
	 * @param youcoin_count兑换U币数量
	 * @param successListener
	 * @param errorListener
	 */
	public static void exchangeCoin(String user_token, String youcoin_count,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.exchange.coin";
		String url = getUrl(KEY_SERVER, server, "client_type", "android",
				"user_token", user_token, "youcoin_count", youcoin_count);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 加入购物车
	 * 
	 * @param user_token
	 * @param p_id
	 *            有价券ID
	 * @param quantity
	 *            加入购物券数量
	 * @param successListener
	 * @param errorListener
	 */
	public static void add2ShoppingCart(String user_token, String p_id,
			String quantity, Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.shopping.cart.add";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"p_id", p_id, "quantity", quantity);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 我的购物车
	 * 
	 * @param user_token
	 * @param successListener
	 * @param errorListener
	 */
	public static void getShoppingCart(String user_token,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.shopping.cart.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 修改购物车
	 * 
	 * @param ids_quantity
	 *            id:数量[多个以逗号隔开，如：1:12,2:5,3:6]
	 * @param successListener
	 * @param errorListener
	 */
	public static void updateShoppingCart(String ids_quantity,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.shopping.cart.update";
		String url = getUrl(KEY_SERVER, server, "ids_quantity", ids_quantity);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 移除购物车
	 * 
	 * @param ids
	 *            多个以逗号隔开，如：1,2,3
	 * @param successListener
	 * @param errorListener
	 */
	public static void deleteShoppingCart(String ids,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.shopping.cart.delete";
		String url = getUrl(KEY_SERVER, server, "ids", ids);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 创建订单（包含充值创建订单）
	 * 
	 * @param user_token
	 * @param cart_ids
	 *            购物车IDS多个逗号隔开；如果是充值订单传0
	 * @param uCoinCount
	 *            值U币数量，如果是充值订单的时候需要传递此参数
	 * @param successListener
	 * @param errorListener
	 */
	public static void addOrder(String user_token, String cart_ids,
			int uCoinCount, Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.order.add";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token,
				"cart_ids", cart_ids, "client_type", "android", "uCoinCount",
				uCoinCount + "");
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 取消订单
	 * 
	 * @param order_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void cancelOrder(String order_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.order.cancel";
		String url = getUrl(KEY_SERVER, server, "order_id", order_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 我的订单
	 * 
	 * @param user_token
	 * @param successListener
	 * @param errorListener
	 */
	public static void getOrder(String user_token,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.order.get";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 订单支付
	 * 
	 * @param user_token
	 * @param id
	 *            订单ID
	 * @param payType
	 *            支付方式(0：U币支付 1:WAP支付宝 2:app支付宝)
	 * @param successListener
	 * @param errorListener
	 */
	public static void payOrder(String user_token, String id, int payType,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.order.pay";
		String url = getUrl(KEY_SERVER, server, "user_token", user_token, "id",
				id, "payType", payType + "");
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 订单结算
	 * 
	 * @param order_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void orderSettle(String order_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.order.settle";
		String url = getUrl(KEY_SERVER, server, "order_id", order_id);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 
	 * @param search_key
	 * @param successListener
	 * @param errorListener
	 */
	public static void searchCustomer(String search_key,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.customers.get";
		String url = getUrl(KEY_SERVER, server, "search_key", search_key);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 商家详情
	 * 
	 * @param user_token
	 * @param customer_id
	 * @param successListener
	 * @param errorListener
	 */
	public static void getCustomerDetail(String user_token, String customer_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.customer.get";
		String url = getUrl("user_token", user_token, "customer_id",
				customer_id, KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		queue.add(myReq);
	}

	/**
	 * 
	 * 获取签到活动列表
	 * 
	 * @Title: getSignActivitys
	 * @param city_id
	 *            非必须
	 * @param successListener
	 * @param errorListener
	 * @return void
	 * @date 2014-2-21 下午8:51:23
	 */
	public static void getSignActivitys(String city_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.sign.activity.get";
		String url = getUrl("city_id", city_id, KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		myReq.setShouldCache(false);
		queue.add(myReq);
	}

	/**
	 * 
	 * 所有商家活动接口
	 * 
	 * @Title: getSubjectActivitys
	 * @param city_id
	 *            非必须
	 * @param successListener
	 * @param errorListener
	 * @return void
	 * @date 2014-2-21 下午8:53:06
	 */
	public static void getAllActivitys(String city_id,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {
		String server = "younion.allactivitys.get";
		String url = getUrl("city_id", city_id, KEY_SERVER, server);
		Log.i(TAG, "url:" + url);
		RequestQueue queue = MyVolley.getRequestQueue();
		StringRequest myReq = new StringRequest(Method.GET, url.toString(),
				successListener, errorListener);
		myReq.setShouldCache(false);
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
