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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
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
import com.example.youlian.mode.Card;
import com.example.youlian.mode.Customer;
import com.example.youlian.mode.YouhuiQuan;

public class ShangjiaActivity extends Activity implements OnClickListener {

	protected static final String TAG = "ShangjiaActivity";
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
	
	private List<Customer> mCustomers = new ArrayList<Customer>();
	ImageLoader  mImageLoader;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_membership);

		initViews();

		YouLianHttpApi.searchCustomer(null, createMyReqSuccessListener(), createMyReqErrorListener());
		
	}

	private void initViews() {
		mImageLoader = MyVolley.getImageLoader();
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.shangjia);
		inflater = LayoutInflater.from(getApplicationContext());
		listview = (ListView) this.findViewById(R.id.listview);
		adapter = new MyAdapter(getApplicationContext());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent in = new Intent(getApplicationContext(), ShangjiaDetailActivity.class);
				Customer c = mCustomers.get(arg2);
				in.putExtra("customer", c);
				startActivity(in);
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
//			exChange();
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
			return mCustomers.size();
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
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_shangjia,
						parent, false);
				holder = new ViewHolder();
				holder.iv_icon = (NetworkImageView) convertView
						.findViewById(R.id.iv_icon);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.iv_star_one = (ImageView) convertView
						.findViewById(R.id.iv_star_one);
				holder.iv_star_two = (ImageView) convertView
						.findViewById(R.id.iv_star_two);
				holder.iv_star_three = (ImageView) convertView
						.findViewById(R.id.iv_star_three);
				holder.iv_star_four = (ImageView) convertView
						.findViewById(R.id.iv_star_four);
				holder.iv_star_five = (ImageView) convertView
						.findViewById(R.id.iv_star_five);
				holder.ivs.add(holder.iv_star_one);
				holder.ivs.add(holder.iv_star_two);
				holder.ivs.add(holder.iv_star_three);
				holder.ivs.add(holder.iv_star_four);
				holder.ivs.add(holder.iv_star_five);
				holder.iv_yi_chong = (ImageView) convertView
						.findViewById(R.id.iv_yi_chong);
				
				
				holder.tv_cardname = (TextView) convertView
						.findViewById(R.id.tv_cardname);
				holder.tv_desc = (TextView) convertView
						.findViewById(R.id.tv_desc);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			setValue(holder, position);

			return convertView;
		}

		public void setValue(ViewHolder holder, int position) {
			Customer c = mCustomers.get(position);
			if(c.logo != null){
				holder.iv_icon.setDefaultImageResId(R.drawable.guanggao);
				holder.iv_icon.setImageUrl(c.logo, mImageLoader);
			}else{
				holder.iv_icon.setImageResource(R.drawable.guanggao);
			}
			
			holder.tv_title.setText(c.name);
			refreshStar(c.starLevel, holder);
			
			holder.tv_cardname.setText(c.favEntityCount+"张优惠券");
			holder.tv_desc.setText(c.cardEntityCount + "张会员卡");
			
		
			if(Integer.parseInt(c.canBestpay) == 1){
				holder.iv_yi_chong.setVisibility(View.VISIBLE);
			}else{
				holder.iv_yi_chong.setVisibility(View.GONE);
			}
		
		}
		
		
		
		
		private void refreshStar(String star_level, ViewHolder holder) {
			float numF = Float.parseFloat(star_level);
			System.out.println("numb:" + numF);
			int numI = (int) numF;
			boolean isHalf = false;
			if(numF-numI>0){
				isHalf = true;
			}
			
			for(int i=0; i<5; i++){
				ImageView iv = holder.ivs.get(i);
				if(i<numI){
					iv.setImageResource(R.drawable.star_red_card);
				}else{
					iv.setImageResource(R.drawable.star_huise_card);
				}
			}
			if(isHalf){
				if(numI < 5){
					holder.ivs.get(numI).setImageResource(R.drawable.star_half_card);
				}
			}
			
		}
		

		class ViewHolder {
			public NetworkImageView iv_icon;
			public TextView tv_title;
			public ImageView iv_star_one, iv_star_two, iv_star_three,
					iv_star_four, iv_star_five;
			public List<ImageView> ivs = new ArrayList<ImageView>();
			public ImageView iv_yi_chong;
			TextView tv_cardname, tv_desc;
		}
	}

	private class MyAdapterAll extends BaseAdapter {

		public MyAdapterAll(Context context) {
		}

		@Override
		public int getCount() {
			return 1;
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
						if (status == 1) {
							mCustomers.addAll(Customer.parseList(o));
							adapter.notifyDataSetChanged();
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

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, "error");
			}
		};
	}

}
