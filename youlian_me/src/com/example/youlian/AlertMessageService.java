package com.example.youlian;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.mode.Ad;
import com.example.youlian.util.YlUtils;

public class AlertMessageService extends Service {

	public static int NOTIFICATION_ID = 999114;
	protected static final String TAG = "AlertMessageService";
	private NotificationManager mNM;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		YouLianHttpApi.getPushMsg(getDeviceId(),
				createGetAdSuccessListener(), createGetAdErrorListener());
		return super.onStartCommand(intent, flags, startId);
	}
	
	public String getDeviceId(){
		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
	    String deviceId =  tm.getDeviceId();
	    Log.i(TAG, "deviceId :" + deviceId);
	    return YlUtils.md5(deviceId);
	}

	private void sendNotification(Ad ad) {
		Intent intent = null;
		if (isRunningInBackgroud()) {// running in backgroud
			int type = Integer.parseInt(ad.linkType);
			switch (type) {
			case 0:
				intent = new Intent(getApplicationContext(),
						MemberShipDetail.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("cardid", ad.linkId);
				break;
			case 1:
				intent = new Intent(getApplicationContext(),
						YouhuiQuanDetail.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("fav_ent_id", ad.linkId);
				break;
			case 2:
				intent = new Intent(getApplicationContext(),
						ShangjiaDetailActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("customerid", ad.linkId);
				break;
			case 3:
				Intent i = new Intent(getApplicationContext(),
						AllSellerDetailActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra("actid", ad.linkId);
				break;
			case 4:// 站外

				break;
			default:
				break;
			}

		} else {
			intent = new Intent(AlertMessageService.this, MainActivity.class);
		}
		// isBackground();
		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.ic_launcher,
				ad.message, System.currentTimeMillis());
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		PendingIntent ci = PendingIntent.getActivity(getApplicationContext(),
				0, intent, 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(AlertMessageService.this,
				getResources().getString(R.string.app_name), ad.message, ci);

		// We use a layout id because it is a unique number. We use it later to
		// cancel.

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// mNM.cancel(NOTIFICATION_ID);
		mNM.notify(NOTIFICATION_ID++, notification);
	}

	public boolean isRunningInBackgroud() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(10);
		int size = tasks.size();
		Log.i(TAG, "tasks size:" + size);
		for (int i = 0; i < size; i++) {
			RunningTaskInfo info = tasks.get(i);
			ComponentName topActivity = info.topActivity;
			Log.i(TAG, "topActivity:" + topActivity.getPackageName() + ", "
					+ topActivity.getClassName());
			if (topActivity.getPackageName().equals(this.getPackageName())) {
				return true;
			}

		}
		return false;
	}

	private Response.Listener<String> createGetAdSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							Ad ad = new Ad();
							ad.linkType = "1";
							ad.message = "ddddddd";
							sendNotification(ad);
						} else {
							String msg = o.optString("msg");
							Toast.makeText(getApplicationContext(), msg,
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	private Response.ErrorListener createGetAdErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, "error");

				AlertMessageService.this.stopSelf();
			}
		};
	}

}
