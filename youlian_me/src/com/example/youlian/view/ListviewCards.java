package com.example.youlian.view;

import java.util.ArrayList;
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
import com.example.youlian.MemberShipDetail;
import com.example.youlian.R;
import com.example.youlian.ShangjiaDetailActivity;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Card;
import com.example.youlian.mode.Customer;

public class ListviewCards extends FrameLayout {
	public static final String TAG = "CutomListview";

	/**
	 * Context
	 */
	private Context mContext = null;
	
	private ListView mListView;

	private List<Card> mCards;
	
	private MyAdapter adapter;
	
	ImageLoader  mImageLoader;;

	public ListviewCards(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public ListviewCards(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public ListviewCards(Context context, AttributeSet attrs) {
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
		adapter = new MyAdapter(mContext);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(onItemClickListener);
	}
	
	public void setData(List<Card> customers){
		mCards = customers;
		adapter.notifyDataSetChanged();
	}
	
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent in = new Intent(mContext, MemberShipDetail.class);
			Card c = mCards.get(arg2);
			in.putExtra("cardid", c.card_id);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(in);
		}
	};
	
	
	private class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		public MyAdapter(Context context) {
			mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return mCards == null ? 0 : mCards.size();
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
				convertView = mInflater.inflate(R.layout.item_membership,
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
				
				holder.iv_online_chong = (ImageView) convertView
						.findViewById(R.id.iv_online_chong);
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
			Card c = mCards.get(position);
			if(c.nonactivatedPic != null){
				holder.iv_icon.setDefaultImageResId(R.drawable.guanggao);
				holder.iv_icon.setImageUrl(c.nonactivatedPic, mImageLoader);
			}else{
				holder.iv_icon.setImageResource(R.drawable.guanggao);
			}
			holder.tv_title.setText(c.card_name);
			refreshStar(c.starLevel, holder);
			holder.tv_cardname.setText(c.cardLevel);
			holder.tv_desc.setText("(" + c.card_content + ")");
			if(Integer.parseInt(c.canOnlinePay) == 1){
				holder.iv_online_chong.setVisibility(View.VISIBLE);
			}else{
				holder.iv_online_chong.setVisibility(View.GONE);
			}
			if(Integer.parseInt(c.canBestPay) == 1){
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
			public ImageView iv_online_chong;
			public ImageView iv_yi_chong;
			TextView tv_cardname, tv_desc;
		}
	}
	
}
