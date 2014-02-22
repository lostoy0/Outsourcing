package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.City;
import com.example.youlian.mode.YouhuiQuan;

public class YouhuiQuanActivity extends Activity implements OnClickListener {

	protected static final String TAG = "MembershipActivity";
	private ImageButton back;
	private TextView tv_title;
	private ListView listview;
	private MyAdapter adapter;
	private LayoutInflater inflater;
	private LinearLayout linear_all_area;
	private LinearLayout linear_all_sort;
	private LinearLayout linear_all_hot;
	private ListView listview_all;
	private MyAdapterAll adapterAll;
	private static final int allarea = 1;
	private static final int allsort = 2;
	private static final int hot = 3;
	private int type = allarea;
	
	public List<YouhuiQuan> youhuiQuans = new ArrayList<YouhuiQuan>();
	
	public List<City> cities = new ArrayList<City>();
	ImageLoader  mImageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_youhuiquan);

		initViews();

		YouLianHttpApi.getYouhuiQuan(Global.getUserToken(getApplicationContext()), null, createMyReqSuccessListener(),createMyReqErrorListener());
	
		YouLianHttpApi.getAreaByProvinceIdCid(null, null, null, creategetAreaByProvinceIdCidSuccessListener(), createMyReqErrorListener());
	}

	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		
		
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		int type = getIntent().getIntExtra("type", 0);
		if(type == 0){
			tv_title.setText(R.string.youhuiquan);
		}else{
			tv_title.setText("热购");
		}
		
		
		inflater = LayoutInflater.from(getApplicationContext());
		listview = (ListView) this.findViewById(R.id.listview);
		adapter = new MyAdapter(getApplicationContext());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				YouhuiQuan quan = youhuiQuans.get(position);
				Intent intent = new Intent(getApplicationContext(), YouhuiQuanDetail.class);
				intent.putExtra("fav_ent_id", quan.fav_ent_id);
				startActivity(intent);
			}
		});

		linear_all_area = (LinearLayout) this
				.findViewById(R.id.linear_all_area);
		linear_all_sort = (LinearLayout) this
				.findViewById(R.id.linear_all_sort);
		linear_all_hot = (LinearLayout) this.findViewById(R.id.linear_all_hot);
		linear_all_area.setOnClickListener(this);
		linear_all_sort.setOnClickListener(this);
		linear_all_hot.setOnClickListener(this);

		listview_all = (ListView) this.findViewById(R.id.listview_all);
		adapterAll = new MyAdapterAll(getApplicationContext());
		listview_all.setAdapter(adapterAll);
		listview_all.setVisibility(View.GONE);
		
		mImageLoader = MyVolley.getImageLoader();
	}

	boolean exChange = true;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.linear_all_area:
			type = allarea;
			exChange();
			adapterAll.notifyDataSetChanged();
			break;
		case R.id.linear_all_sort:
			type = allsort;
			adapterAll.notifyDataSetChanged();
			break;
		case R.id.linear_all_hot:
			type = hot;
			adapterAll.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

	private void exChange() {
		if (exChange) {
			listview.setVisibility(View.GONE);
			listview_all.setVisibility(View.VISIBLE);
		} else {
			listview.setVisibility(View.VISIBLE);
			listview_all.setVisibility(View.GONE);
		}
		exChange = !exChange;
	}

	private class MyAdapter extends BaseAdapter {

		public MyAdapter(Context context) {
		}

		@Override
		public int getCount() {
			return youhuiQuans.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_youhuiquan,
						parent, false);
				holder = new ViewHolder();
				holder.iv_icon = (NetworkImageView) convertView
						.findViewById(R.id.iv_icon);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tv_desc = (TextView) convertView
						.findViewById(R.id.tv_desc);
				
				holder.iv_gou = (ImageView)convertView
						.findViewById(R.id.iv_gou);
				holder.iv_qiang = (ImageView)convertView
						.findViewById(R.id.iv_qiang);
				holder.iv_xian = (ImageView)convertView
						.findViewById(R.id.iv_xian);
				holder.iv_zhe = (ImageView)convertView
						.findViewById(R.id.iv_zhe);
				
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			setValue(holder, position);

			return convertView;
		}

		public void setValue(ViewHolder holder, int position) {
			YouhuiQuan quan = youhuiQuans.get(position);
			if(TextUtils.isEmpty(quan.fav_id)){
				if(quan.nonactivatedPic != null){
					holder.iv_icon.setDefaultImageResId(R.drawable.guanggao);
					holder.iv_icon.setImageUrl(quan.nonactivatedPic, mImageLoader);
				}else{
					holder.iv_icon.setImageResource(R.drawable.guanggao);
				}
			}else{
				if(quan.activatedPic != null){
					holder.iv_icon.setDefaultImageResId(R.drawable.guanggao);
					holder.iv_icon.setImageUrl(quan.activatedPic, mImageLoader);
				}else{
					holder.iv_icon.setImageResource(R.drawable.guanggao);
				}
			}
			holder.tv_title.setText(quan.fav_ent_name);
			holder.tv_desc.setText(quan.simple_description);
			
			
			if(quan.isLimitQuantity.equals("1")){
				holder.iv_xian.setVisibility(View.VISIBLE);
			}else{
				holder.iv_xian.setVisibility(View.GONE);
			}
			if(quan.isBuy.equals("1")){
				holder.iv_gou.setVisibility(View.VISIBLE);
			}else{
				holder.iv_gou.setVisibility(View.GONE);
			}
			if(quan.isAgio.equals("1")){
				holder.iv_zhe.setVisibility(View.VISIBLE);
			}else{
				holder.iv_zhe.setVisibility(View.GONE);
			}
			if(quan.isHotBuy.equals("1")){
				holder.iv_qiang.setVisibility(View.VISIBLE);
			}else{
				holder.iv_qiang.setVisibility(View.GONE);
			}
		}

		class ViewHolder {
			public NetworkImageView iv_icon;
			public TextView tv_title;
			TextView  tv_desc;
			
			public ImageView iv_gou;
			public ImageView iv_qiang;
			public ImageView iv_xian;
			public ImageView iv_zhe;
			
		}
	}

	private class MyAdapterAll extends BaseAdapter {

		public MyAdapterAll(Context context) {
		}

		@Override
		public int getCount() {
			return cities.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.item_all, parent, false);
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv_icon);
			TextView tv = (TextView) convertView.findViewById(R.id.tv_title);
			switch (type) {
			case allarea:
				iv.setVisibility(View.GONE);
				City city = cities.get(position);
				tv.setText(city.areaName);
				break;
			case allsort:
				iv.setVisibility(View.GONE);
				
				break;
			case hot:
				tv.setVisibility(View.GONE);
				break;
			}
			
			
			return convertView;
		}

		class ViewHolder {
			public ImageView iv_icon;
			public TextView tv_title;
			public ImageView iv_star_one, iv_star_two, iv_star_three,
					iv_star_four, iv_star_five;
			public ImageView iv_online_chong;
			TextView tv_cardname, tv_desc;
		}
	}

	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if(status == 1){
							JSONArray array = o.optJSONArray("result");
							int len = array.length();
							for(int i=0; i<len; i++){
								JSONObject oo = array.getJSONObject(i);
								youhuiQuans.add(YouhuiQuan.parse(oo));
							}
							adapter.notifyDataSetChanged();
						}else{
							String msg = o.optString("msg");
							Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			}
		};
	}
	
	
	private Response.Listener<String> creategetAreaByProvinceIdCidSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if(status == 1){
							JSONArray array = o.optJSONArray("result");
							int len = array.length();
							for(int i=0; i<len; i++){
								JSONObject oo = array.getJSONObject(i);
								cities.add(City.parse(oo));
							}
							adapterAll.notifyDataSetChanged();
						}else{
							String msg = o.optString("msg");
							Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, "error");
			}
		};
	}

}
