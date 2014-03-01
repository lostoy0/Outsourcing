package com.example.youlian.view;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.youlian.R;
import com.example.youlian.ShangjiaDetailActivity;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Customer;

public class CutomListview extends FrameLayout {
	public static final String TAG = "CutomListview";

	/**
	 * Context
	 */
	private Context mContext = null;
	
	private ListView mListView;

	private List<Customer> mCustomers;
	
	private HomeAdapter adapter;
	
	ImageLoader  mImageLoader;;

	public CutomListview(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public CutomListview(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public CutomListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) mLayoutInflater.inflate(
				R.layout.listview, null);

		addView(view);
		mImageLoader = MyVolley.getImageLoader();
		mListView = (ListView) this.findViewById(R.id.myListView);
		adapter = new HomeAdapter();
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(onItemClickListener);
	}
	
	public void setData(List<Customer> customers){
		mCustomers = customers;
		adapter.notifyDataSetChanged();
	}
	
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent in = new Intent(mContext, ShangjiaDetailActivity.class);
			Customer c = mCustomers.get(arg2);
			in.putExtra("customerid", c.id);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(in);
		}
	};
	
	
	class HomeAdapter extends BaseAdapter{
		private LayoutInflater mInflater;

		public HomeAdapter(){
			mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return mCustomers == null ? 0 : mCustomers.size();
		}

		
		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.i(TAG, "position:" + position);
			final ViewCache vc;
			if (convertView == null) {
				vc = new ViewCache();
				convertView = mInflater.inflate(R.layout.home_listview_item, null);
				vc.stroe_img = (NetworkImageView)convertView.findViewById(R.id.stroe_img);
				vc.is_store = (ImageView)convertView.findViewById(R.id.is_store);
				vc.follow_active = (ImageView)convertView.findViewById(R.id.follow_active);
//				vc.follow_card = (ImageView)convertView.findViewById(R.id.follow_card);
				vc.follow_interest = (ImageView)convertView.findViewById(R.id.follow_interest);
				vc.follow_union = (ImageView)convertView.findViewById(R.id.follow_union);
				vc.stroe_name_text = (TextView)convertView.findViewById(R.id.card_name_text);
				vc.card_content_text = (TextView)convertView.findViewById(R.id.card_content_text);
				vc.is_have_fad = (ImageView)convertView.findViewById(R.id.is_have_fad);
				vc.is_rem = (ImageView)convertView.findViewById(R.id.is_rem);
				convertView.setTag(vc);
			}else {
				vc = (ViewCache) convertView.getTag();
			}

			setValue(position, vc);
			return convertView;
		}
		
		
		private void setValue(int position, ViewCache holder) {
			Customer customer = mCustomers.get(position);
			if(customer.logo != null){
				holder.stroe_img.setDefaultImageResId(R.drawable.guanggao);
				holder.stroe_img.setImageUrl(customer.logo, mImageLoader);
			}else{
				holder.stroe_img.setImageResource(R.drawable.guanggao);
			}
			
			holder.stroe_name_text.setText(customer.name);
			holder.card_content_text.setText(customer.introduce);
			if(Integer.parseInt(customer.isRecommend) == 1){
				holder.is_rem.setVisibility(View.VISIBLE);
			}else{
				holder.is_rem.setVisibility(View.GONE);
			}
			
		}


		private class ViewCache {
			public TextView stroe_name_text;
			public TextView card_content_text;
			public ImageView is_store,follow_active,follow_union,follow_interest,is_have_fad,is_rem;
			public NetworkImageView stroe_img;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
}
