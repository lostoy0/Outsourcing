package com.example.youlian.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.youlian.R;


public class ExceptionUtils {

	private static final String TAG = "ExceptionUtils";
	
	public static final int ERROR_CODE_CANNOT_RECORD = 610;
	public static final int ERROR_CODE_BEYOND_TIME = 611;
	public static final int ERROR_CODE_VIDEO_TIMES_BEYOND  = 612;
	public static final int ERROR_CODE_TIME_LENGTH_BEYOND = 613;
	public static final int ERROR_CODE_ENDTIME_MUST_GREATER = 614;
	public static final int ERROR_CODE_VIDEO_ITEM_HAS_EXIST = 615;

	public static String getErrorMsg(VolleyError error, Context context) {
		String errorMsg = context.getString(R.string.unknown_error);
    	if(error instanceof TimeoutError){// ConnectTimeoutException 超时，SocketTimeoutException超时
    		Log.i(TAG,"---TimeoutError");
    		errorMsg = context.getString(R.string.error_timeout);
    	}else if(error instanceof NoConnectionError){// 1,没有网络时的异常  2,ip地址写错
    		Log.i(TAG,"---NoConnectionError");
    		errorMsg = context.getString(R.string.error_noconnection);
    	}else if(error instanceof AuthFailureError){// 401 403 
    		Log.i(TAG,"---AuthFailureError");
    		if(error.networkResponse != null){
    			int statusCode = error.networkResponse.statusCode;
        		String msg = new String(error.networkResponse.data);
                Log.i(TAG,"--errorMsg:" + "statusCode:" + statusCode + ", msg:" + msg);
                errorMsg = parseErrorMsg(statusCode, msg, context);
    		}
    	}else if(error instanceof NetworkError){
    		Log.i(TAG,"---NetworkError");
    		errorMsg = context.getString(R.string.error_network);
    	}else if(error instanceof ServerError){//  1,请求参数出错 2, 请求路劲出错, 404
    		Log.i(TAG,"---ServerError");
    		if(error.networkResponse != null){
    			int statusCode = error.networkResponse.statusCode;
        		String msg = new String(error.networkResponse.data);
                Log.i(TAG,"--errorMsg:" + "statusCode:" + statusCode + ", msg:" + msg);
                errorMsg = parseErrorMsg(statusCode, msg, context);
    		}
    	}else{
    		errorMsg = context.getString(R.string.unknown_error);
    		Log.i(TAG,"---VolleyError");
    	}
    	Log.i(TAG,"errorMsg:" + errorMsg);
		return errorMsg;
	}
	
	
	private static String parseErrorMsg(int code, String msg, Context context) {
    	JSONObject jsonObject = null;;
		try {
			jsonObject = new JSONObject(msg);
			JSONObject errorObj = jsonObject.optJSONObject("error");
	    	String errorMsg = (String)errorObj.opt("message");
	    	return errorMsg;
		} catch (JSONException e) {
			e.printStackTrace(); 
		}
		return context.getString(R.string.unknown_error);
	}
}
