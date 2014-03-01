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

import com.example.youlian.AlertMessageService;

/**
 * Application class for the demo. Used to ensure that MyVolley is initialized. {@see MyVolley}
 * @author Ognyan Bankov
 *
 */
public class YouLianApp extends Application {
	protected static final String TAG = "YouLianApp";
	private PendingIntent sAlertSender;
    @Override
    public void onCreate() {
    	
        super.onCreate();
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
				+ 5 * 1000, interval * 15 * 1000, sAlertSender);
	}
    
    
    public void cancelAlarm() {
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.cancel(sAlertSender);
	}
    
}
