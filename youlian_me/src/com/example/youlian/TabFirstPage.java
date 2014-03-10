package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Ad;
import com.example.youlian.mode.RegioninfoVO;
import com.example.youlian.mode.SubjectActivity;
import com.example.youlian.view.SimpleProgressDialog;
import com.example.youlian.view.TemplateFive;
import com.example.youlian.view.TemplateFour;
import com.example.youlian.view.TemplateOne;
import com.example.youlian.view.TemplateSix;
import com.example.youlian.view.TemplateThree;
import com.example.youlian.view.TemplateTwo;

public class TabFirstPage extends Activity implements OnClickListener {

	protected static final String TAG = "TabPie";
	private LinearLayout container;
	private Button bt_membercard;
	private Button bt_youhuiquan;
	private Gallery image_wall_gallery;
	private ImageAdapter adapter;
	private LinearLayout pointLinear;
	private int indicatorPositon;
	private LinearLayout linear_act;
	private LinearLayout linear_seller;
	private TextView search_edit;
	private Button bt_hotbuy;
	private LinearLayout linear_area;
	private RegioninfoVO mProvince;
	private RegioninfoVO mCity;
	private RegioninfoVO mDistrict;
	private TextView tv_area;
	private Button mScanRQCodeButton;

	private static final int REQ_ADDR = 0x1000;
	private List<Ad> ads = new ArrayList<Ad>();
	
	double latitude = 0.0;
	double longitude = 0.0;
	
	String address;
	
	private static final int what = 2;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == what) {
				String add = (String)msg.obj;
				tv_area.setText(add);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_tab_pie);

		initViews();
		
		SimpleProgressDialog.show(this);
		// 获取广告轮播图
		YouLianHttpApi.getAdvertisement("0", createGetAdSuccessListener(),
				createGetAdErrorListener());
		// 活动主题
		YouLianHttpApi.getSubjectActivity(createMyReqSuccessListener(),
				createMyReqErrorListener());
		
		
	}

	private void initViews() {
		mScanRQCodeButton = (Button) findViewById(R.id.btn_scan_rqcode);
		mScanRQCodeButton.setOnClickListener(this);
		
		search_edit = (TextView) this.findViewById(R.id.search_edit);
		search_edit.setOnClickListener(this);

		linear_area = (LinearLayout) this.findViewById(R.id.linear_area);
		linear_area.setOnClickListener(this);
		tv_area = (TextView) this.findViewById(R.id.tv_area);

		container = (LinearLayout) this.findViewById(R.id.container);
		bt_membercard = (Button) this.findViewById(R.id.bt_membercard);
		bt_membercard.setOnClickListener(this);

		bt_youhuiquan = (Button) this.findViewById(R.id.bt_youhuiquan);
		bt_youhuiquan.setOnClickListener(this);

		bt_hotbuy = (Button) this.findViewById(R.id.bt_hotbuy);
		bt_hotbuy.setOnClickListener(this);

		image_wall_gallery = (Gallery) this
				.findViewById(R.id.image_wall_gallery);
		adapter = new ImageAdapter(getApplicationContext());
		image_wall_gallery.setAdapter(adapter);

		image_wall_gallery
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						changePointView(arg2);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		image_wall_gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int nPosition = arg2;
				Log.i("nPosition", nPosition + "");
				Ad ad = ads.get(nPosition);
				int type = Integer.parseInt(ad.linkType);
				switch (type) {
				case 0:
					Intent in = new Intent(getApplicationContext(), MemberShipDetail.class);
					in.putExtra("cardid", ad.linkId);
					startActivity(in);
					break;
				case 1:
					Intent intent = new Intent(getApplicationContext(), YouhuiQuanDetail.class);
					intent.putExtra("fav_ent_id", ad.linkId);
					startActivity(intent);
					break;
				case 2:
					 in = new Intent(getApplicationContext(), ShangjiaDetailActivity.class);
					in.putExtra("customerid", ad.linkId);
					startActivity(in);
					break;
				case 3:
					Intent i = new Intent(getApplicationContext(), AllSellerDetailActivity.class);
					i.putExtra("actid", ad.linkId);
					startActivity(i);
					break;
				case 4:// 站外
					
					break;

				default:
					break;
				}
			}
		});

		pointLinear = (LinearLayout) this
				.findViewById(R.id.gallery_point_linear);

		linear_act = (LinearLayout) this.findViewById(R.id.linear_act);
		linear_act.setOnClickListener(this);
		linear_seller = (LinearLayout) this.findViewById(R.id.linear_seller);
		linear_seller.setOnClickListener(this);

	}

	public void changePointView(int cur) {
		View view = pointLinear.getChildAt(indicatorPositon);
		View curView = pointLinear.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.screen_indicator_off);
			curPointView.setBackgroundResource(R.drawable.screen_indicator_on);
			indicatorPositon = cur;
		}
	}
	
	LocationListener locationListener;
	@Override
	protected void onResume() {
		super.onResume();
		getLocation();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(locationListener != null){
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.removeUpdates(locationListener);
		}
	}
	public void getLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(locationListener != null){
			locationManager.removeUpdates(locationListener);
		}
		locationListener = new LocationListener() {
			// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

			}

			// Provider被enable时触发此函数，比如GPS被打开
			@Override
			public void onProviderEnabled(String provider) {

			}

			// Provider被disable时触发此函数，比如GPS被关闭
			@Override
			public void onProviderDisabled(String provider) {

			}

			// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发  
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					Log.i(TAG,
							"Location changed : Lat: "
									+ location.getLatitude() + " Lng: "
									+ location.getLongitude());
					getAddress();
					
				}
			}
		};
		Location location  = null;
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Log.i(TAG,"GPS_PROVIDER");
			location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					1000, 0, locationListener);
		} else {
			Log.i(TAG,"network_provider");
			locationManager
					.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
							1000, 0, locationListener);
			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			
		}
		
		if (location != null) {
			latitude = location.getLatitude(); // 经度
			longitude = location.getLongitude(); // 纬度
			getAddress();
			Log.i(TAG,"latitude:" + latitude + ", longitude:" + longitude);
		}else{
			Log.i(TAG,"	 location is null");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linear_area:
			Intent i = new Intent(this, AreaProvinceActivity.class);
			startActivityForResult(i, REQ_ADDR);
			break;
		case R.id.bt_membercard:// 会员卡
			i = new Intent(getApplicationContext(), MembershipActivity.class);
			startActivity(i);
			break;
		case R.id.bt_youhuiquan:// 优惠券
			i = new Intent(getApplicationContext(), YouhuiQuanActivity.class);
			i.putExtra("type", 0);
			startActivity(i);
			break;

		case R.id.bt_hotbuy:// 热狗
			i = new Intent(getApplicationContext(), YouhuiQuanActivity.class);
			i.putExtra("type", 1);
			startActivity(i);
			break;
		case R.id.linear_act:// 活动
			i = new Intent(getApplicationContext(), AllSellerActivity.class);
			startActivity(i);
			break;
		case R.id.linear_seller:// 商家
			i = new Intent(getApplicationContext(), ShangjiaActivity.class);
			startActivity(i);
			break;
		case R.id.search_edit:
			i = new Intent(getApplicationContext(), SearchActivity.class);
			startActivity(i);
			break;
			
		case R.id.btn_scan_rqcode:
			Intent mCaptureActivity = new Intent(this, CaptureActivity.class);
			startActivityForResult(mCaptureActivity,0);
			break;

		default:
			break;
		}
	}

	class ImageAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public ImageAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return ads.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_gallery, null);
				vh = new ViewHolder();
				vh.imageView = (ImageView) convertView
						.findViewById(R.id.gallery_image);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			setValue(vh, position);
			return convertView;
		}

		public void add(List<Ad> ds) {
			ads.addAll(ds);
			notifyDataSetChanged();
		}

		private void setValue(ViewHolder vh, int position) {
			Ad ad = ads.get(position);
			if (!TextUtils.isEmpty(ad.pic)) {
				ImageLoader imageLoader = MyVolley.getImageLoader();
				imageLoader
						.get(ad.pic, ImageLoader.getImageListener(vh.imageView,
								R.drawable.guanggao, R.drawable.guanggao));
			}

			changePointView(position);
		}

		protected class ViewHolder {
			int tag;
			ImageView imageView;
		}
	}

	private Response.Listener<String> createGetAdSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success guanggao:" + response);
				try {
					List<Ad> ads = Ad.parse(response);
					adapter.add(ads);

					addPoint(ads.size());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
	}

	private void addPoint(int size) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 0, 0, 0);
		for (int i = 0; i < size; i++) {
			ImageView pointView = new ImageView(getApplicationContext());
			pointView.setLayoutParams(params);
			if (i == 0) {
				pointView.setBackgroundResource(R.drawable.screen_indicator_on);
			} else
				pointView
						.setBackgroundResource(R.drawable.screen_indicator_off);
			pointLinear.addView(pointView);
		}
	}

	private Response.ErrorListener createGetAdErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, "error");
			}
		};
	}

	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				SimpleProgressDialog.dismiss();
				try {
					List<SubjectActivity> list = SubjectActivity
							.parse(response);
					if (list != null)
						for (int i = 0; i < list.size(); i++) {
							SubjectActivity sub = list.get(i);
							switch (sub.activeTemplate) {
							case Global.TEMPLATE_ONE:
								TemplateOne one = new TemplateOne(
										getApplicationContext());
								container.addView(one);
								one.setData(sub);
								break;
							case Global.TEMPLATE_TWO:
								TemplateTwo two = new TemplateTwo(
										getApplicationContext());
								container.addView(two);
								two.setData(sub);
								break;
							case Global.TEMPLATE_THREE:
								TemplateThree three = new TemplateThree(
										getApplicationContext());
								container.addView(three);
								three.setData(sub);
								break;
							case Global.TEMPLATE_FOUR:
								TemplateFour four = new TemplateFour(
										getApplicationContext());
								container.addView(four);
								four.setData(sub);
								break;
							case Global.TEMPLATE_FIVE:
								TemplateFive five = new TemplateFive(
										getApplicationContext());
								container.addView(five);
								five.setData(sub);
								break;
							case Global.TEMPLATE_SIX:
								TemplateSix six = new TemplateSix(
										getApplicationContext());
								container.addView(six);
								break;

							default:
								break;
							}

						}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				SimpleProgressDialog.dismiss();
				Log.i(TAG, "error");
			}
		};
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQ_ADDR && resultCode == RESULT_OK) {
			mProvince = (RegioninfoVO) data
					.getSerializableExtra(AreaProvinceActivity.key_province);
			mCity = (RegioninfoVO) data
					.getSerializableExtra(AreaProvinceActivity.key_city);
			mDistrict = (RegioninfoVO) data
					.getSerializableExtra(AreaProvinceActivity.key_district);
			tv_area.setText(mDistrict.areaName);
		}
	}
	
	
	private void getAddress() {
		if(address == null){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					HttpGet request = new HttpGet(getAddressUrl());
					HttpResponse response;
					try {
						response = new DefaultHttpClient().execute(request);
						if (response.getStatusLine().getStatusCode() == 200) {
							String rpstr = EntityUtils.toString(response.getEntity());
							JSONObject result = new JSONObject(rpstr);
							Log.d(TAG, "chengshi:" + result.toString());
							// 将结果JSONObject中的results转换成JSONArray
							JSONArray ja = (JSONArray) result.get("results");
							for (int i = 0; i < ja.length(); i++) {
								JSONObject js = ja.getJSONObject(i);
								JSONArray jaa = (JSONArray) js.get("types");
								String ss = jaa.getString(0);
								// 根据types中的street_address获取address
								if ("route".equalsIgnoreCase(ss)) {
									address = js.getString("formatted_address");
									Message msg = handler.obtainMessage();
									msg.what = what;
									msg.obj = address;
									handler.sendMessage(msg);
									Log.d(TAG, "address:" + address);
								}

							}

						}
					} catch (Exception e) {
						Log.i("MainActivity", e.toString());
					}
				}
			}).start();
		}
	}
	
	private String getAddressUrl() {
		return "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude
				+ "," + longitude + "&language=zh-CN&sensor=true";
	}
}
