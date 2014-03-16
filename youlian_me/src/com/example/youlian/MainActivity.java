package com.example.youlian;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.StartPic;

public class MainActivity extends Activity {
	private static final int what = 1;
	private static final long delayMillis = 3000;
	protected static final String TAG = "MainActivity";

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == what) {
				Intent i = new Intent(getApplicationContext(), TabHome.class);
				startActivity(i);
				finish();
			}
		}
	};
	private ImageView loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		Global.saveLocCityId(getApplicationContext(), "106105101");
		
		loading = (ImageView) this.findViewById(R.id.loading);
		loading.setImageResource(R.drawable.loading);
		
		
		// resolution=480_495
		YouLianHttpApi.getInitInfo("480_495", createGetAdSuccessListener(),
				createGetAdErrorListener());
	}

	private Response.Listener<String> createGetAdSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				handler.sendEmptyMessageDelayed(what, delayMillis);
				Log.i(TAG, "success:" + response);

				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							JSONObject jsonObject = o.optJSONObject("result");
							JSONArray array = jsonObject
									.optJSONArray("startPics");
							int len = array.length();
							List<StartPic> list = new ArrayList<StartPic>();
							for (int i = 0; i < len; i++) {
								JSONObject oo = array.getJSONObject(i);
								list.add(StartPic.parse(oo));
							}
							setStartPic(list);
						} else {
							String msg = o.optString("msg");
							Log.i(TAG, msg);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

	private void setStartPic(List<StartPic> list) {
		int size = list.size();
		for (int i = 0; i < size; i++) {
			final StartPic startpic = list.get(i);
			ImageLoader imageLoader = MyVolley.getImageLoader();
			imageLoader.get(startpic.pic, new ImageListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
				}

				@Override
				public void onResponse(ImageContainer response,
						boolean isImmediate) {
					Bitmap bitmap = response.getBitmap();
					if (bitmap != null) {
						if (isMid(startpic.begTime, startpic.endTime)) {
							loading.setImageBitmap(bitmap);
						}
					}
				}
			});
		}

	}

	public boolean isMid(String begTime, String endTime) {
		boolean flag = false;
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = df2.parse(begTime);
			Date d2 = df2.parse(endTime);
			Date date = new Date();
			int numb1 = date.compareTo(d1);
			int numb2 = date.compareTo(d2);

			if (numb1 == 1 && numb2 == -1) {
				flag = true;
			}
			Log.i(TAG, "flag:" + flag + " numb1:" + numb1 + ", numb2:" + numb2);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.i(TAG, "flag:erro");
		}

		return flag;
	}

	private Response.ErrorListener createGetAdErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				handler.sendEmptyMessageDelayed(what, delayMillis);
				Log.i(TAG, "error");
			}
		};
	}

	
}
