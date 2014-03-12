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
import com.example.youlian.AllSellerDetailActivity;
import com.example.youlian.R;
import com.example.youlian.ShangjiaDetailActivity;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Act;
import com.example.youlian.mode.Customer;

public class ListviewAct extends FrameLayout {
	public static final String TAG = "CutomListview";

	/**
	 * Context
	 */
	private Context mContext = null;
	
	private ListView mListView;

	private List<Act> mActs;
	
	private HomeAdapter adapter;
	
	ImageLoader  mImageLoader;;

	public ListviewAct(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public ListviewAct(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public ListviewAct(Context context, AttributeSet attrs) {
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
	
	public void setData(List<Act> acts ){
		mActs = acts;
		adapter.notifyDataSetChanged();
	}
	
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent in = new Intent(mContext, AllSellerDetailActivity.class);
			Act c = mActs.get(arg2);
			in.putExtra("actid", c.id);
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
			return mActs == null ? 0 : mActs.size();
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
				convertView = mInflater.inflate(R.layout.item_act_search, null);
				vc.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
				vc.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				convertView.setTag(vc);
			}else {
				vc = (ViewCache) convertView.getTag();
			}

			setValue(position, vc);
			return convertView;
		}
		
		
		private void setValue(int position, ViewCache holder) {
			Act customer = mActs.get(position);
			ImageLoader imageLoader = MyVolley.getImageLoader();
	        imageLoader.get(customer.pic, 
	                       ImageLoader.getImageListener(holder.iv_icon, 
	                                                     0, 
	                                                     0));
	        holder.tv_title.setText(customer.title);
		}


		private class ViewCache {
			public TextView tv_title;
			public ImageView iv_icon;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
}
