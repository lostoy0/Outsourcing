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
import com.example.youlian.mode.Customer;
import com.example.youlian.mode.YouhuiQuan;
import com.example.youlian.view.SimpleProgressDialog;
import com.example.youlian.ShangjiaActivity;

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
	
	
	private int type_from = 0;
	
	public List<YouhuiQuan> youhuiQuans = new ArrayList<YouhuiQuan>();
	
	
	public List<YouhuiQuan> handleYouhuiQuans = new ArrayList<YouhuiQuan>();
	public List<City> cities = new ArrayList<City>();
	public List<String> hots = new ArrayList<String>();
	public List<String> parts = new ArrayList<String>();
	
	ImageLoader  mImageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_youhuiquan);

		initViews();

		SimpleProgressDialog.show(this);
		YouLianHttpApi.getYouhuiQuan(Global.getLocCityId(getApplicationContext()), Global.getUserToken(getApplicationContext()), null, createMyReqSuccessListener(),createMyReqErrorListener());
	
		YouLianHttpApi.getAreaByProvinceIdCid(null, Global.getLocCityId(getApplicationContext()), null, creategetAreaByProvinceIdCidSuccessListener(), createGetAreaErrorListener());
	}
	
	private TextView tv_all_area;
	private TextView tv_all_sort;
	private TextView tv_hot;;

	private void initViews() {
		tv_all_area = (TextView)this.findViewById(R.id.tv_all_area);
		tv_all_sort = (TextView)this.findViewById(R.id.tv_all_sort);
		tv_hot = (TextView)this.findViewById(R.id.tv_hot);
		
		
		
		
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		type_from = getIntent().getIntExtra("type", 0);
		if(type_from == 0){
			tv_title.setText(R.string.youhuiquan);
		}else if(type_from == 1){
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
				YouhuiQuan quan = handleYouhuiQuans.get(position);
				Intent intent = new Intent(getApplicationContext(), YouhuiQuanDetail.class);
				intent.putExtra("fav_ent_id", quan.fav_ent_id);
				intent.putExtra("type_from", type_from);
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
		
		listview_all.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				switch (YouhuiQuanActivity.this.type) {
				case allarea:
					if(position < cities.size()){
						if(position == 0) {
							tv_all_area.setText(R.string.all_area);
							handleYouhuiQuans.clear();
							handleYouhuiQuans.addAll(youhuiQuans);
							adapter.notifyDataSetChanged();
							
							setListviewVisible();
							return;
						}
						City c = cities.get(position);
						initCity(c);
					}
					break;
				case allsort:
					if(position < parts.size()){
						handleYouhuiQuans.clear();
						switch (position) {
						case 0:
							tv_all_sort.setText(getString(R.string.all_size, size));
							handleYouhuiQuans.addAll(youhuiQuans);
							break;
						case ShangjiaActivity.shenghuo_service:
							tv_all_sort.setText(getString(R.string.living_service, shenghuoService));
							initShenghuoList(ShangjiaActivity.shenghuo_service);
							break;
						case ShangjiaActivity.meili_liren:
							tv_all_sort.setText(getString(R.string.meili_liren, meiliLiren));
							initShenghuoList(ShangjiaActivity.meili_liren);
							break;
						case ShangjiaActivity.xiuxian_yule:
							tv_all_sort.setText(getString(R.string.xiuxian_yulei, xiuxianYule));
							initShenghuoList(ShangjiaActivity.xiuxian_yule);
							break;
						case ShangjiaActivity.canyin_meishi:
							tv_all_sort.setText(getString(R.string.canyin_meishi, canyinMeishi));
							initShenghuoList(ShangjiaActivity.canyin_meishi);
							break;
						case ShangjiaActivity.guangjie_gouwu:
							tv_all_sort.setText(getString(R.string.gouwu_buy, guangjieGouwu));
							initShenghuoList(ShangjiaActivity.guangjie_gouwu);
							break;
						case ShangjiaActivity.other:
							tv_all_sort.setText(getString(R.string.other, otherMe));
							initShenghuoList(ShangjiaActivity.other);
							break;
						default:
							break;
						}
						adapter.notifyDataSetChanged();
					}			
					break;
				case hot:
					if(position < hots.size()){
						String result = hots.get(position);
						if(result.equals(getString(R.string.all_of))){
							handleYouhuiQuans.clear();
							handleYouhuiQuans.addAll(youhuiQuans);
							adapter.notifyDataSetChanged();
						}else if(result.equals(getString(R.string.hot))){
							handleYouhuiQuans.clear();
							int size = youhuiQuans.size();
							for(int i=0; i<size; i++){
								YouhuiQuan y = youhuiQuans.get(i);
								if("1".equals(y.isHot)){
									handleYouhuiQuans.add(y);
								}
							}
							adapter.notifyDataSetChanged();
						}else if(result.equals(getString(R.string.nearby))){
							
						}else{
							
						}
					}	
					break;
					
				default:
					break;
				}
				setListviewVisible();
			}
		});
		
		mImageLoader = MyVolley.getImageLoader();
		
		
		hots.add(getString(R.string.all_of));
		hots.add(getString(R.string.hot));
		hots.add(getString(R.string.nearby));
		
	}

	boolean exChange = true;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.linear_all_area:
			setFucengVisible();
			type = allarea;
			adapterAll.notifyDataSetChanged();
			break;
		case R.id.linear_all_sort:
			setFucengVisible();
			type = allsort;
			adapterAll.notifyDataSetChanged();
			break;
		case R.id.linear_all_hot:
			setFucengVisible();
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
	
	public void setFucengVisible(){
		listview.setVisibility(View.GONE);
		listview_all.setVisibility(View.VISIBLE);
	}
	
	
	public void setListviewVisible(){
		listview.setVisibility(View.VISIBLE);
		listview_all.setVisibility(View.GONE);
	}

	private class MyAdapter extends BaseAdapter {

		public MyAdapter(Context context) {
		}

		@Override
		public int getCount() {
			return handleYouhuiQuans.size();
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
			YouhuiQuan quan = handleYouhuiQuans.get(position);
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
			
			if(quan.isHotBuy.equals("1")){
				holder.iv_qiang.setVisibility(View.VISIBLE);
			}else{
				holder.iv_qiang.setVisibility(View.GONE);
			}
			if(quan.isAgio.equals("1")){
				holder.iv_zhe.setVisibility(View.VISIBLE);
			}else{
				holder.iv_zhe.setVisibility(View.GONE);
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
			return 10;
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
				if(position < parts.size()){
					tv.setText(parts.get(position));
					switch (position) {
					case 0:
						iv.setImageResource(R.drawable.iv_all);
						break;
					case 1:
						iv.setImageResource(R.drawable.living_service);				
						break;
					case 2:
						iv.setImageResource(R.drawable.meili_liren);
						break;
					case 3:
						iv.setImageResource(R.drawable.xiuxian_yule);
						break;
					case 4:
						iv.setImageResource(R.drawable.canyin_meishi);
						break;
					case 5:
						iv.setImageResource(R.drawable.guangjie_gouwu);
						break;
					case 6:
						iv.setImageResource(R.drawable.iv_other);
						break;

					default:
						break;
					}
					iv.setVisibility(View.VISIBLE);
				}else{
					iv.setVisibility(View.GONE);
				}
				
				break;
			case hot:
				iv.setVisibility(View.GONE);
				if(position < hots.size()){
					tv.setText(hots.get(position));
				}
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
				SimpleProgressDialog.dismiss();
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
								YouhuiQuan y = YouhuiQuan.parse(oo);
								if(type_from == 0){//优惠券
									youhuiQuans.add(y);
								}else {
//									if("1".equals(y.isBuy)){
										youhuiQuans.add(y);
//									}
								}
							}
							handleYouhuiQuans.addAll(youhuiQuans);
							
							initCategoryNum();
							
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
							City all = new City();
							all.areaName = getString(R.string.all_of);
							all.areaId = "10000";
							cities.add(all);
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
				SimpleProgressDialog.dismiss();
				Log.i(TAG, "error");
			}
		};
	}
	
	private Response.ErrorListener createGetAreaErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				SimpleProgressDialog.dismiss();
				Log.i(TAG, "error");
			}
		};
	}
	
	
	protected void initShenghuoList(int shenghuoService) {
		int size = youhuiQuans.size();
		for(int i=0;i<size; i++){
			YouhuiQuan c = youhuiQuans.get(i);
			if(shenghuoService == Integer.parseInt(c.cat_id)){
				handleYouhuiQuans.add(c);
			}
		}
	}
	protected void initCity(City c) {
		tv_all_area.setText(c.areaName);
		handleYouhuiQuans.clear();
		int size = youhuiQuans.size();
		for(int i=0; i<size; i++){
			YouhuiQuan cus = youhuiQuans.get(i);
			if(c.areaId.equals(cus.districtId)){
				handleYouhuiQuans.add(cus);
			}
		}
		adapter.notifyDataSetChanged();
	}
	
	int size;
	int shenghuoService;
	int meiliLiren;
	int xiuxianYule;
	int canyinMeishi;
	int guangjieGouwu;
	int otherMe;
	private void initCategoryNum() {
		size = youhuiQuans.size();
		for(int i=0;i<size; i++){
			YouhuiQuan c = youhuiQuans.get(i);
			int type = Integer.parseInt(c.cat_id);
			switch (type) {
			case ShangjiaActivity.shenghuo_service:
				shenghuoService++;
				break;
			case ShangjiaActivity.meili_liren:
				meiliLiren++;	
				break;
			case ShangjiaActivity.xiuxian_yule:
				xiuxianYule++;
				break;
			case ShangjiaActivity.canyin_meishi:
				canyinMeishi++;
				break;
			case ShangjiaActivity.guangjie_gouwu:
				guangjieGouwu++;
				break;
			case ShangjiaActivity.other:
				otherMe++;
				break;
			default:
				break;
			}
		}
		parts.add(getString(R.string.all_size, size));
		parts.add(getString(R.string.living_service, shenghuoService));
		parts.add(getString(R.string.meili_liren, meiliLiren));
		parts.add(getString(R.string.xiuxian_yulei, xiuxianYule));
		parts.add(getString(R.string.canyin_meishi, canyinMeishi));
		parts.add(getString(R.string.gouwu_buy, guangjieGouwu));
		parts.add(getString(R.string.other, otherMe));
	}
}
