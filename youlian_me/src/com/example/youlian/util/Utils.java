package com.example.youlian.util;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


/**
 *
 * @author simon
 *
 */
public class Utils {

	public static final String SYMBOL_LINE = "\n";

	public static final String SYMBOL_EMPTY = "";

	public static final String SYMBOL_DOCUMENT = "/";

	public static final String SYMBOL_SPACE = " ";

	private static final double EARTH_RADIUS = 6378137;
	/**
	 * sd卡地址
	 *
	 * @return
	 */
	public static String getSdCardDir() {
		File sdCardDir = android.os.Environment.getExternalStorageDirectory();
		return sdCardDir.getPath();
	}

	/**
	 * �ж��Ƿ���SD��
	 * @return
	 */
	public static boolean isSDCardAvaiable(){

        String status = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(status);

    }

	/**
	 * ������Ϣ��ʾ��
	 * @param context
	 * @param msg
	 * @param gravity
	 */
	public static void showToast(Context context,int msgid) {
		Toast toast = Toast.makeText(context, msgid, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}
	public static void showToast(Context context,CharSequence msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}
	public static void showToast(Context context,int stringid,Object... values) {
		String msg = format(context, stringid, values);
		showToast(context,msg);
	}
	/***
	 * ��ȡ��ʽ���ַ�
	 * @param context
	 * @param stringid
	 * @param values
	 * @return
	 */
	public static String format(Context context,int stringid,Object... values){
		return String.format(context.getResources().getString(stringid), values);
	}
	/***
	 * ��ʼһ�ζ���
	 * @param context
	 * @param view
	 * @param animid
	 */
	public static void showAnimation(Context context,View view,int animid){
		Animation anim = AnimationUtils.loadAnimation(context, animid);
		view.startAnimation(anim);
	}
	public static Drawable getDrawable(Context context,int drawableid){
		return context.getResources().getDrawable(drawableid);
	}

	/**
	 * ���?��,ȫ�ֹ㲥
	 * @param context
	 * @param shareMsg
	 * @param activityTitle
	 */
	public static void share(Context context,String shareMsg,String activityTitle)
	{
		Intent intent=new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, activityTitle);
		intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
		context.startActivity(Intent.createChooser(intent, activityTitle));
	}
	/**
	 * ����绰
	 * @param context
	 * @param shareMsg
	 * @param activityTitle
	 */
	public static void call(Context context,String number)
	{
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
		// ��ʼ����
		context.startActivity(intent);
	}


	/**
	 * ��ʾ������view
	 * @param view
	 */
	public static void toggleVisibility(View view){
		if(view.getVisibility()==View.GONE){
			view.setVisibility(View.VISIBLE);
		}else{
			view.setVisibility(View.GONE);
		}
	}

	/**
	 * ����ϵͳ���ܰ�װ����apk
	 */
	public static void installApk(Context context,File apk) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apk),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * ���г�
	 * @param mContext
	 */
	public void search(Context mContext){
		Uri uri = Uri.parse("market://search?q=pname:"+mContext.getPackageName());
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(it);
	}

	/**
	 * ���ַ�������һ��
	 * @param strings
	 * @return
	 */
	public static String joint(String...strings){
		StringBuffer sb = new StringBuffer();
		for(String str :strings){
			sb.append(str);
		}
		return sb.toString();
	}



	/**
	 * ����ת�ַ�
	 * @param num
	 * @return
	 */
	public static String toString(int num){
		return num+"";
	}
	public static String toString(long num){
		return num+"";
	}
	public static String toString(float num){
		return num+"";
	}
	public static String toString(double num){
		return num+"";
	}


	/**
	 * 判断网络是否畅通
	 *
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				int l = info.length;
				for (int i = 0; i < l; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * �ַ�ת����
	 * @param num
	 * @return
	 */
	public static int toInt(String num){
		return Integer.valueOf(num);
	}


	/**
	 * ����Ϊ��
	 * @param obj
	 * @return
	 */
	public static boolean notNull(Object obj){
		if(null != obj && obj!=""){
			return true;
		}
		return false;
	}

	public static boolean isNull(Object obj){
		if(null == obj || obj=="" || obj.equals("")){
			return true;
		}
		return false;
	}
	
	public static <E> boolean isArrayNotNull(ArrayList<E> obj){
		if(null != obj && obj.size()>0){
			return true;
		}
		return false;
	}
	/**
	 * InputStreamת��Ϊbyte[]����
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] openStream(InputStream is) throws IOException {

		if(is == null) return null;

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buff = new byte[1024]; //buff���ڴ��ѭ����ȡ����ʱ���

		int rc = 0;

		while ((rc = is.read(buff, 0, buff.length)) > 0) {

			os.write(buff, 0, rc);

		}

		buff = os.toByteArray();

		is.close();
		os.close();

		return buff;

	}

	/**
	 * ��ȡ�ַ���Դ
	 * @param context
	 * @param id
	 * @return
	 */
	public static String getStringById(Context context ,int id){
		return context.getResources().getString(id);
	}

	 //ת��dipΪpx
	public static int convertDipToPx(float density, int dip) {
	    return (int)(dip*density + 0.5f*(dip>=0?1:-1));
	}

	//ת��pxΪdip
	public static int convertPxToDip(float density, int px) {
	    return (int)(px/density + 0.5f*(px>=0?1:-1));
	}

	/**
	 * 截取日期
	 * @param con
	 * @return
	 */
	public static String subDate(String date)
	{
		if(notNull(date)){
		return	date.substring(0, date.indexOf(" "));
		}
		return date;
	}
	public static int getScreenWidth(Context con)
	{
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) con).getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        return width;
	}

	public static int getScreenHeight(Context con)
	{
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) con).getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;
        return height;
	}
	    private static double rad(double d)
	    {
	       return d * Math.PI / 180.0;
	    }
	    
	    /**
	     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	     * @param lng1
	     * @param lat1
	     * @param lng2
	     * @param lat2
	     * @return
	     */
	    public static double GetDistance(double lng1, double lat1, double lng2, double lat2)
	    {
	       double radLat1 = rad(lat1);
	       double radLat2 = rad(lat2);
	       double a = radLat1 - radLat2;
	       double b = rad(lng1) - rad(lng2);
	       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	       s = s * EARTH_RADIUS;
	       s = Math.round(s * 10000) / 10000;
	       return s;
	    }
	 // 计算两点距离
	public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
	       double radLat1 = (lat_a * Math.PI / 180.0);
	       double radLat2 = (lat_b * Math.PI / 180.0);
	       double a = radLat1 - radLat2;
	       double b = (lng_a - lng_b) * Math.PI / 180.0;
	       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
	              + Math.cos(radLat1) * Math.cos(radLat2)
	              * Math.pow(Math.sin(b / 2), 2)));
	       s = s * EARTH_RADIUS;
	       s = Math.round(s * 10000) / 10000;
	       return s;
	    }
	
	/**
	* deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
	*
	* 渠道标志为：
	* 1，andriod（a）
	*
	* 识别符来源标志：
	* 1， wifi mac地址（wifi）；
	* 2， IMEI（imei）；
	* 3， 序列号（sn）；
	* 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
	*
	* @param context
	* @return
	*/
//	public static String getDeviceId(Context context) {
//
//		StringBuilder deviceId = new StringBuilder();
//		// 渠道标志
//		deviceId.append("a");
//
//		try {
//
//			// wifi mac地址
//			WifiManager wifi = (WifiManager) context
//					.getSystemService(Context.WIFI_SERVICE);
//			WifiInfo info = wifi.getConnectionInfo();
//			String wifiMac = info.getMacAddress();
//			if (Utils.notNull(wifiMac)) {
//				deviceId.append("wifi");
//				deviceId.append(wifiMac);
//				System.out.println("getDeviceId : " + deviceId.toString());
//				return deviceId.toString();
//			}
//
//			// IMEI（imei）
//			TelephonyManager tm = (TelephonyManager) context
//					.getSystemService(Context.TELEPHONY_SERVICE);
//			String imei = tm.getDeviceId();
//			if (Utils.notNull(imei)) {
//				deviceId.append("imei");
//				deviceId.append(imei);
//				System.out.println("getDeviceId : " + deviceId.toString());
//				return deviceId.toString();
//			}
//
//			// 序列号（sn）
//			String sn = tm.getSimSerialNumber();
//			if (Utils.notNull(sn)) {
//				deviceId.append("sn");
//				deviceId.append(sn);
//				System.out.println("getDeviceId : " + deviceId.toString());
//				return deviceId.toString();
//			}
//
//			// 如果上面都没有， 则生成一个id：随机码
//			String uuid = getUUID(context);
//			if (Utils.notNull(uuid)) {
//				deviceId.append("id");
//				deviceId.append(uuid);
//				System.out.println("getDeviceId : " + deviceId.toString());
//				return deviceId.toString();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			deviceId.append("id").append(getUUID(context));
//		}
//
//		System.out.println("getDeviceId : " + deviceId.toString());
//
//		return deviceId.toString();
//
//	}
//
//	 
//
//
//	       /**
//	* 得到全局唯一UUID
//	*/
//	public static String getUUID(Context context){
//	SharedPreferences mShare = getSysShare(context, "sysCacheMap");
//	if(mShare != null){
//	uuid = mShare.getString("uuid", "");
//	}
//
//	if(isEmpty(uuid)){
//	uuid = UUID.randomUUID().toString();
//	saveSysMap(context, "sysCacheMap", "uuid", uuid);
//	}
//
//	PALog.e(tag, "getUUID : " + uuid);
//	return uuid;
//	}
	
	public static String getDeviceId(Context context) {

        final TelephonyManager tm = (TelephonyManager) context

                .getSystemService(Context.TELEPHONY_SERVICE);



        final String tmDevice, tmSerial, tmPhone, androidId;

        tmDevice = "" + tm.getDeviceId();

        tmSerial = "" + tm.getSimSerialNumber();

        androidId = ""

                + android.provider.Settings.Secure.getString(

                        context.getContentResolver(),

                        android.provider.Settings.Secure.ANDROID_ID);



        UUID deviceUuid = new UUID(androidId.hashCode(),

                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

        String uniqueId = deviceUuid.toString();

        return uniqueId;

    }
}
