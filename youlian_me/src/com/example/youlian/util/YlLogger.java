package com.example.youlian.util;

import android.util.Log;

public class YlLogger {

	private String mTag = "YouLian";
	
	private YlLogger() {}
	
	private YlLogger(String tag) {
		mTag = tag;
	}
	
	public static YlLogger getLogger(String tag) {
		return new YlLogger(tag);
	}
	
	public void i(String msg) {
		Log.i(mTag, msg);
	}
	
	public void d(String msg) {
		Log.d(mTag, msg);
	}
	
	public void v(String msg) {
		Log.v(mTag, msg);
	}
	
	public void w(String msg) {
		Log.w(mTag, msg);
	}
	
	public void e(String msg) {
		Log.e(mTag, msg);
	}
}
