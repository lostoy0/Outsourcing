package com.example.youlian.util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.youlian.common.Configure;
import com.example.youlian.mode.LoginResult;


/**
 * @author simon
 * @proName vipshop
 * @version 1.0
 * @Data 2012-7-25 上午11:20:18
 *
   <b>SharedPreferences Manager</b>
 */
public class PreferencesUtils {

	private static SharedPreferences mShareConfig;


	/**
	 * 判断是否登录
	 * @return
	 */
	public static boolean isLogin(Context context) {
		mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		if (Utils.notNull(mShareConfig)){
			if (Utils.notNull(mShareConfig.getString(Configure.SESSION_USER_TOKEN, ""))) {
				return true;
			}
		}
		return false;
	}


	/**
	 * 添加购物车图片信息
	 * @param key
	 * @param value
	 */
	public static void addCartProductImage(Context context,String key,String value)
	{
//		if(!StringUtils.isNull(key) && !StringUtils.isNull(value))
//		{
			mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.putString(key, value);
			conEdit.commit();
//		}
	}

	/**
	 * 添加公共信息
	 * @param key
	 * @param value
	 */
	public static void addConfigInfo(Context context,String key,String value){
		if (Utils.notNull(key)){
			mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.putString(key.trim(), value.trim());
			conEdit.commit();
		}
	}
	


	public static void addConfigLongInfo(Context context, String key, Long value) {
		if (Utils.notNull(key)){
			mShareConfig = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.putLong(key, value);
			conEdit.commit();
		}
	}

	/**
	 * 添加公共信息
	 * @param key
	 * @param value
	 */
	public static void addConfigInfo(Context context,String key,boolean value)
	{
		if (Utils.notNull(key)){
			mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.putBoolean(key, value);
			conEdit.commit();
		}
	}
	/**
	 * 添加公共信息
	 * @param key
	 * @param value
	 */
	public static void addConfigInfo(Context context,String key,Long value)
	{
		if (Utils.notNull(key)){
			mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
			Editor conEdit = mShareConfig.edit();
			conEdit.putLong(key, value);
			conEdit.commit();
		}
	}

	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static String getStringByKey(Context context,String key)
	{
		String value = null;
		if (Utils.notNull(key)){
			mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = mShareConfig.getString(key, "");
			}
		}
		return value;
	}

	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static Long getPreferenValue(Context context,String key)
	{
		Long value = null;
		if (Utils.notNull(key)){
			mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = mShareConfig.getLong(key,0);
			}
		}
		return value;
	}

	/**
	 * 根据key得到信息
	 * @param key
	 * @param value
	 */
	public static boolean getBooleanByKey(Context context,String key)
	{
		boolean value = false;
		if (Utils.notNull(key)){
			mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
			if(null != mShareConfig){
				value = mShareConfig.getBoolean(key, false);
			}
		}
		return value;
	}
	
//	/**
//	 * 根据key得到信息
//	 * @param key
//	 * @param value
//	 */
//	public static Long getLongByKey(Context context,String key)
//	{
//		Long value;
//		if (Utils.notNull(key)){
//			mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
//			if(null != mShareConfig){
//				value = mShareConfig.getLong(key, false);
//			}
//		}
//		return value;
//	}

	/**
	 * 得到预售Map信息
	 * @param key
	 * @param value
	 */
	public static Map<String,String> getShareMap(Context context , String sharePackage)
	{
		mShareConfig = context.getSharedPreferences(sharePackage, Context.MODE_PRIVATE);
		return (Map<String, String>) mShareConfig.getAll();
	}
	/**
	 * 清楚预售Map信息
	 * @param key
	 * @param value
	 */
	public static void clearShareMap(Context context)
	{
		mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		if (Utils.notNull(mShareConfig)){
			Editor edit = mShareConfig.edit();
			edit.clear();
			edit.commit();
		}
	}

	/**
	 * 保存会话中用户信息
	 * @param context
	 * @return
	 */
	public static void saveSessionUser(Context context,LoginResult user){
		mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		if (Utils.notNull(mShareConfig)){
			Editor edit = mShareConfig.edit();
			edit.putString(Configure.SESSION_USER_TYPE, user.getType());
			System.out.println("user_token:"+user.getUser_token());
			edit.putString(Configure.SESSION_USER_TOKEN, user.getUser_token());
			
			edit.putString(Configure.SESSION_USER_NAME, user.getUser_name());
			edit.putString(Configure.SESSION_USER_INTEGRAL,user.getUser_integral());
			edit.commit();
		}
		System.out.println("user_token  2:"+getUserToken(context));
	}

	/**
	 * 得到会话中用户信息
	 * @param context
	 * @return
	 */
	public static LoginResult getSessionUser(Context context)
	{
		LoginResult user = new LoginResult();
		if (Utils.notNull(mShareConfig)){
			mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		}
		    String user_type = mShareConfig.getString(Configure.SESSION_USER_TYPE,"");	
		
			String user_token = mShareConfig.getString(Configure.SESSION_USER_TOKEN,"");

			String user_name = mShareConfig.getString(Configure.SESSION_USER_NAME,"");
			
			String user_integral = mShareConfig.getString(Configure.SESSION_USER_INTEGRAL,"");

			if (Utils.notNull(user_type))user.setType(user_type);
			if (Utils.notNull(user_token))user.setUser_token(user_token);
			if (Utils.notNull(user_name)) user.setUser_name(user_name);
			if (Utils.notNull(user_integral)) user.setUser_integral(user_integral);
		return user;
	}

	/**
	 * 注销会话中用户信息
	 * @param context
	 * @return
	 */
	public static void clearSessionUser(Context context){
		mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		if (Utils.notNull(mShareConfig)){
			Editor edit = mShareConfig.edit();
			edit.putString(Configure.SESSION_USER_TYPE, "");
			edit.putString(Configure.SESSION_USER_TOKEN, "");
			edit.putString(Configure.SESSION_USER_NAME, "");
			edit.putString(Configure.SESSION_USER_INTEGRAL, "");
			edit.putBoolean(Configure.SESSION_USER_IS_REMPSD, false);
			edit.putBoolean(Configure.SESSION_USER_IS_MSG, false);
			edit.commit();
		}
	}

	/**
	 *
	 * @param context
	 * @return
	 */
	public static String getUserToken(Context context){
		mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		if (Utils.notNull(mShareConfig)){
			Editor edit = mShareConfig.edit();
				return getStringByKey(context, Configure.SESSION_USER_TOKEN);
		}
		return null;
	}
	/**
	 * 保存会话地址信息
	 * @param context
	 * @param address
	 */
	public static void saveSessionUserInfo(Context context,String userName,String userCall,
			String userEmail,String userProvinec,String userCity,String userDistrict)
	{
		mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		if (Utils.notNull(mShareConfig))
		{
			Editor edit = mShareConfig.edit();
			edit.putString(Configure.SESSION_NAME, userName);
			edit.putString(Configure.SESSION_CALL, userCall);
			edit.putString(Configure.SESSION_EMAIL, userEmail);
			edit.putString(Configure.SESSION_PROVINEC, userProvinec);
			edit.putString(Configure.SESSION_CITY, userCity);
			edit.putString(Configure.SESSION_DISTRICT,userDistrict);
			edit.commit();
		}
	}
//
//	/**
//	 * 得到会话地址信息
//	 * @param context
//	 * @param address
//	 */
//	public static AddressResult getSessionAddress(Context context)
//	{
//		AddressResult address = null;
//		mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
//		if (Utils.notNull(mShareConfig)){
//			address = new AddressResult();
//			address.setAddress_id(mShareConfig.getString(Configure.SESSION_ADDRESS_ID,""));
//			address.setConsignee(mShareConfig.getString(Configure.SESSION_ADDRESS_CONSIGNEE,""));
//			address.setAddress(mShareConfig.getString(Configure.SESSION_ADDRESS_INFO, ""));
//			address.setMobile(mShareConfig.getString(Configure.SESSION_ADDRESS_MOBILE, ""));
//			address.setPostcode(mShareConfig.getString(Configure.SESSION_ADDRESS_POSTCODE, ""));
//			address.setTel(mShareConfig.getString(Configure.SESSION_ADDRESS_TEL, ""));
//			address.setArea_id(mShareConfig.getString(Configure.SESSION_ADDRESS_AREAID, ""));
//			address.setIs_common(mShareConfig.getInt(Configure.SESSION_ADDRESS_IS_COMMON, 0));
//		}
//		return address;
//	}
//
//
//	/**
//	 * 注销会话中地址信息
//	 * @param context
//	 * @return
//	 */
//	public static void clearSessionAddress(Context context)
//	{
//		mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
//		if (Utils.notNull(mShareConfig)){
//			Editor edit = mShareConfig.edit();
//			edit.putString(Configure.SESSION_ADDRESS_ID, "");
//			edit.putString(Configure.SESSION_ADDRESS_CONSIGNEE, "");
//			edit.putString(Configure.SESSION_ADDRESS_INFO, "");
//			edit.putString(Configure.SESSION_ADDRESS_MOBILE, "");
//			edit.putString(Configure.SESSION_ADDRESS_POSTCODE, "");
//			edit.putString(Configure.SESSION_ADDRESS_TEL, "");
//			edit.putString(Configure.SESSION_ADDRESS_AREAID, "");
//			edit.putInt(Configure.SESSION_ADDRESS_IS_COMMON,0);
//			edit.commit();
//		}
//	}

	public static boolean remove(Context context,String key){
		mShareConfig = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return mShareConfig.edit().remove(key).commit();
	}

}
