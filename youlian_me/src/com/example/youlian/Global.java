package com.example.youlian;

import com.example.youlian.common.Configure;
import com.example.youlian.util.PreferencesUtils;

import android.content.Context;
import android.text.TextUtils;

/**
 * 全局公共类
 *
 */
public class Global {
	public static final int TEMPLATE_ONE = 1;
	public static final int TEMPLATE_TWO = 2;
	public static final int TEMPLATE_THREE = 3;
	public static final int TEMPLATE_FOUR = 4;
	public static final int TEMPLATE_FIVE = 5;
	public static final int TEMPLATE_SIX = 6;
	
	private static String sUserToken = null;
	
	/**
	 * 
	 * 获取userToken
	 * @Title: getUserToken
	 * @param context
	 * @return
	 * @return String
	 * @date 2014-1-25 上午10:41:48
	 */
	public static String getUserToken(Context context) {
		if(TextUtils.isEmpty(sUserToken)) {
			sUserToken = PreferencesUtils.getUserToken(context);
		}
		return sUserToken;
	}
	
	/**
	 * 
	 * 判断是否是自动登录
	 * @Title: isAutoLogin
	 * @param context
	 * @return
	 * @return boolean
	 * @date 2014-1-25 上午10:41:28
	 */
	public static boolean isAutoLogin(Context context) {
		return PreferencesUtils.getBooleanByKey(context, Configure.SESSION_USER_IS_REMPSD);
	}
	
	public static void destroy(Context context) {
		sUserToken = null;
		PreferencesUtils.clearSessionUser(context);
	}
}
