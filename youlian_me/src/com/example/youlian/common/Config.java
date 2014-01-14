package com.example.youlian.common;

import com.example.youlian.util.Utils;




public class Config {

	public static boolean DEBUG = true;
	
	/**下载的图片存放路径**/
	public static String imagesPath ="/youlian/images";
	
	public static String youlianPhone = "09914542288";
	
	/**********软件更新*********/
	/** NOTIFICATION_ID **/
	public static final String downappPath = Utils.getSdCardDir()+ "/youlian/download/app/";
	public static final int UPDATE_ACTIVITY_FINISH = 1234;	//强制更新
	public static final int UPDATE_APP = 1235;		//普通更新
}
