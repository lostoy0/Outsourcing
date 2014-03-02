/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.youlian.app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;
import com.example.youlian.AlertMessageService;

/**
 * Application class for the demo. Used to ensure that MyVolley is initialized. {@see MyVolley}
 * @author Ognyan Bankov
 *
 */
public class YouLianApp extends Application {
	protected static final String TAG = "YouLianApp";
	private PendingIntent sAlertSender;
	
	
	//百度MapAPI的管理类
		public BMapManager mBMapMan = null;
		public static YouLianApp instance = null;
		// 授权Key
		// TODO: 请输入您的Key,
		// 申请地址：http://dev.baidu.com/wiki/static/imap/key/
		public String mStrKey = "B7AE5B0D790891D510BA79BE15681A505B2C6441";
		public boolean m_bKeyRight = true;	// 授权Key正确，验证通过
		
		// 常用事件监听，用来处理通常的网络错误，授权验证错误等
		public static class MyGeneralListener implements MKGeneralListener {
			@Override
			public void onGetNetworkState(int iError) {
				Toast.makeText(YouLianApp.instance.getApplicationContext(), "您的网络出错啦！",
						Toast.LENGTH_LONG).show();
			}

			@Override
			public void onGetPermissionState(int iError) {
				if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
					// 授权Key错误：
					Toast.makeText(YouLianApp.instance.getApplicationContext(), 
							"请在BMapApiDemoApp.java文件输入正确的授权Key！",
							Toast.LENGTH_LONG).show();
					YouLianApp.instance.m_bKeyRight = false;
				}
			}
			
		}
		
    @Override
    public void onCreate() {
    	
        super.onCreate();
        instance = this;
        
        Log.i(TAG, "YouLianApp onCreate:" + YouLianApp.this);
        init();
    }


    private void init() {
        MyVolley.init(this);
        
        registerAlertMessage();
    }
    
    
    public void registerAlertMessage() {
    	sAlertSender = PendingIntent.getService(getApplicationContext(), 0,
				new Intent(getApplicationContext(), AlertMessageService.class), 0);
    	
		int interval = 1;
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC, System.currentTimeMillis()
				+ 15 * 1000, interval * 30 * 1000, sAlertSender);
	}
    
    
    public void cancelAlarm() {
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.cancel(sAlertSender);
	}
    
}
