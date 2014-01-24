package com.example.youlian.common;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 *
 * @author simon
 * @proName vipshop
 * @version 1.0
 * @Data 2012-7-25 下午01:19:57
 *
 * @Comment...
 */
public class Configure {
	private static Context mContext;

	public static void init(Activity context) {
		mContext = context;
		Display display= context.getWindowManager().getDefaultDisplay();
		Configure.screenWidth= display.getWidth();
		Configure.screenHeight = display.getHeight();
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		pxWidth = dm.widthPixels;
		pxHeight = dm.heightPixels;
        int height = dm.heightPixels;
		Configure.statusBarHeight = getStatusHeight(context);

	}

	public static int getStatusHeight(Activity context){
		int y=0;
		try {
			Class c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			y = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return y;
	}
	/**
	 * 数据返回成功与否验证 1 成功  0  失败
	 */
	public static final String dataType = "1";
	
	/**
	 * 普通用户与商户 0 普通 1 商户
	 */
	public static final String userType = "0";
	
	/**
	 * 有关会员卡的类型
	 */
	public static final String cardType = "1";
	
	/**
	 * 有关登录跳转  我的卡包、更多、申请会员卡、分享、关注 
	 */
	public static final String MyKabao = "mykaobao",More = "more",CardManger= "cardmanger";
	
	/**
	 * 更多中先登录：修改个人资料、修改密码、我关注的商家
	 */
	public static final String UpUserInfo = "UpUserInfo",UpUserPsd = "UpUserPsd",MyFollowStore= "MyFollowStore";
	
	public static final int APPLY_CARD = 11,SHARE_CARD =22,FOLLOW_CARD = 33,MORE_LOGIN = 44,BAG_LOGIN=55,CARD_DET_LOGIN = 66,cityLoacl = 77
	,TWO_CODE_SEARCH= 88;
	/**
	 * 有关分享的app
	 */
	public static final String IS_SHARE_TX = "tx", IS_SHARE_RR = "rr",IS_SHARE_XL = "xl";
	
	public static  String USERINFO_RENREN , EXPIRES_IN ;
	
	
	public static final String USERINFO_SIAN = "USERINFO_SIAN",EXPIRES_IN_SIAN = "EXPIRES_IN_SINAN";;
	/**
	 * 广告  活动首页,列表页
	 */
	public static final int zone_home = 0,zone_list = 1;
	/**
	 * 首页图片标记、城市变更标记、城市串
	 */
	public static final String home_page = "home_page",home_city = "home_city",citys = "citys";
	
	/**
	 * 信息中心条数、是否已经查看
	 */
	public static final String msgSum = "msg_sum",isReaded = "is_readed";
	/** 用户会话信息 **/

	/** 用户类型 **/
	public static final String SESSION_USER_TYPE = "session_user_type";
	/** 用户session **/
	public static final String SESSION_USER_TOKEN = "session_user_token";
	/** 用户名字 **/
	public static final String SESSION_USER_NAME = "session_user_name";
	/** 用户积分 **/
	public static final String SESSION_USER_INTEGRAL = "session_user_integral"; 
	/** 用户是否推送信息 **/
	public static final String SESSION_USER_IS_MSG = "session_user_is_msg";
	/** 用户是否记住密码 **/
	public static final String SESSION_USER_IS_REMPSD = "session_user_is_rem_psd";
	
	/** 定位城市  名字 **/
	public static final String SESSION_CITY_NAME = "session_city_name";
	
	/** 定位城市 id **/
	public static final String SESSION_CITY_ID = "session_city_id";
	public static int screenWidth=0, screenHeight=0, screenDensity=0;
	/**会员资料 **/
	public static final String SESSION_NAME = "session_name";
	public static final String SESSION_CALL = "session_call";
	public static final String SESSION_EMAIL = "session_email";
	public static final String SESSION_PROVINEC = "session_provinec";
	public static final String SESSION_CITY = "session_city";
	public static final String SESSION_DISTRICT = "session_district";
	 //我的位置
    public static double locallon;
	public static double locallat;
	public static int pxWidth,pxHeight;
	public static Bitmap NO_IMAGE=null;
	public static boolean isDatabaseOprating = false;
	public static Bitmap[] DetailWeiboImages=null;
	public static int statusBarHeight;	//状态栏高度
	public static int headerHeight;	//导航条高度

}