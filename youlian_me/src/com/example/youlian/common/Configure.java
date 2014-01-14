package com.example.youlian.common;

import java.lang.reflect.Field;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 *
 * @author simon
 * @proName vipshop
 * @version 1.0
 * @Data 2012-7-25 ����01:19:57
 *
 * @Comment...
 */
public class Configure {
	private static Context mContext;

	/**
	 *
	 * @param context
	 */
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
	 * ���ݷ��سɹ������֤ 1 �ɹ�  0  ʧ��
	 */
	public static final String dataType = "1";
	
	/**
	 * ��ͨ�û����̻� 0 ��ͨ 1 �̻�
	 */
	public static final String userType = "0";
	
	/**
	 * �йػ�Ա��������
	 */
	public static final String cardType = "1";
	
	/**
	 * �йص�¼��ת  �ҵĿ��������ࡢ�����Ա����������ע 
	 */
	public static final String MyKabao = "mykaobao",More = "more",CardManger= "cardmanger";
	
	/**
	 * �������ȵ�¼���޸ĸ������ϡ��޸����롢�ҹ�ע���̼�
	 */
	public static final String UpUserInfo = "UpUserInfo",UpUserPsd = "UpUserPsd",MyFollowStore= "MyFollowStore";
	
	public static final int APPLY_CARD = 11,SHARE_CARD =22,FOLLOW_CARD = 33,MORE_LOGIN = 44,BAG_LOGIN=55,CARD_DET_LOGIN = 66,cityLoacl = 77
	,TWO_CODE_SEARCH= 88;
	/**
	 * �йط����app
	 */
	public static final String IS_SHARE_TX = "tx", IS_SHARE_RR = "rr",IS_SHARE_XL = "xl";
	
	public static  String USERINFO_RENREN , EXPIRES_IN ;
	
	
	public static final String USERINFO_SIAN = "USERINFO_SIAN",EXPIRES_IN_SIAN = "EXPIRES_IN_SINAN";;
	/**
	 * ���  ���ҳ,�б�ҳ
	 */
	public static final int zone_home = 0,zone_list = 1;
	/**
	 * ��ҳͼƬ��ǡ����б����ǡ����д�
	 */
	public static final String home_page = "home_page",home_city = "home_city",citys = "citys";
	
	/**
	 * ��Ϣ�����������Ƿ��Ѿ��鿴
	 */
	public static final String msgSum = "msg_sum",isReaded = "is_readed";
	/** �û��Ự��Ϣ **/

	/** �û����� **/
	public static final String SESSION_USER_TYPE = "session_user_type";
	/** �û�session **/
	public static final String SESSION_USER_TOKEN = "session_user_token";
	/** �û����� **/
	public static final String SESSION_USER_NAME = "session_user_name";
	/** �û����� **/
	public static final String SESSION_USER_INTEGRAL = "session_user_integral"; 
	/** �û��Ƿ�������Ϣ **/
	public static final String SESSION_USER_IS_MSG = "session_user_is_msg";
	/** �û��Ƿ��ס���� **/
	public static final String SESSION_USER_IS_REMPSD = "session_user_is_rem_psd";
	
	/** ��λ����  ���� **/
	public static final String SESSION_CITY_NAME = "session_city_name";
	
	/** ��λ���� id **/
	public static final String SESSION_CITY_ID = "session_city_id";
	public static int screenWidth=0, screenHeight=0, screenDensity=0;
	/**��Ա���� **/
	public static final String SESSION_NAME = "session_name";
	public static final String SESSION_CALL = "session_call";
	public static final String SESSION_EMAIL = "session_email";
	public static final String SESSION_PROVINEC = "session_provinec";
	public static final String SESSION_CITY = "session_city";
	public static final String SESSION_DISTRICT = "session_district";
	 //�ҵ�λ��
    public static double locallon;
	public static double locallat;
	public static int pxWidth,pxHeight;
	public static Bitmap NO_IMAGE=null;
	public static boolean isDatabaseOprating = false;
	public static Bitmap[] DetailWeiboImages=null;
	public static int statusBarHeight;	//״̬���߶�
	public static int headerHeight;	//�������߶�

}