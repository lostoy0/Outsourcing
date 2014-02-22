package com.example.youlian.adapter;

import java.util.ArrayList;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.youlian.CouponListActivity;
import com.example.youlian.R;
import com.example.youlian.mode.YouhuiQuan;
import com.example.youlian.util.Utils;

public class CouponListAdapter extends BaseAdapter {

	private CouponListActivity mContext;
	private LayoutInflater mInflater;
	private ArrayList<YouhuiQuan> mCouponList;
	private ImageLoader mImageLoader;
	
	public CouponListAdapter(CouponListActivity context, ArrayList<YouhuiQuan> cards, ImageLoader imageLoader) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mCouponList = cards;
		mImageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return mCouponList.size();
//		return 5;
	}

	@Override
	public YouhuiQuan getItem(int position) {
		return mCouponList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_coupon_list, null);
			holder.iconImageView = (NetworkImageView) convertView.findViewById(R.id.coupon_item_iv_icon);
			holder.deleteButton = (ImageButton) convertView.findViewById(R.id.coupon_item_ib_delete);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.coupon_item_tv_name);
			holder.priceTextView = (TextView) convertView.findViewById(R.id.coupon_item_tv_price);
			holder.validDateTextView = (TextView) convertView.findViewById(R.id.coupon_item_tv_validdate);
			holder.presentImageView = (ImageView) convertView.findViewById(R.id.coupon_item_iv_present);
			convertView.setTag(holder);
		}
		
		if(mContext.getEditState()) {
			holder.deleteButton.setVisibility(View.VISIBLE);
			holder.deleteButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mCouponList.remove(position);
					notifyDataSetChanged();
				}
			});
		} else {
			holder.deleteButton.setVisibility(View.GONE);
		}
		
		setData(holder, mCouponList.get(position));
		
		return convertView;
	}
	
	private void setData(ViewHolder holder, YouhuiQuan item) {
		if(item != null) {
			String logoUrl = null;
			if(Utils.notNull(item.fav_id)){
				logoUrl = item.activatedPic;
			}else{
				logoUrl = item.nonactivatedPic;
			}
			if(!TextUtils.isEmpty(logoUrl)){
				holder.iconImageView.setImageUrl(logoUrl, mImageLoader);
			}else{
				holder.iconImageView.setImageResource(R.drawable.default_img);
			}
			
			holder.nameTextView.setText(item.fav_name);
			holder.priceTextView.setText("NO." + item.fav_id);
			holder.validDateTextView.setText("有效期："+ item.apply_date_from.substring(0,10)+"\n"+"至"+item.apply_date_to.substring(0,10));
			if("0".equals(item.sellingPrice)){
				holder.presentImageView.setVisibility(View.VISIBLE);
			}else{
				holder.presentImageView.setVisibility(View.GONE);
			}
		}
	}

	static class ViewHolder {
		NetworkImageView iconImageView;
		ImageButton deleteButton;
		TextView nameTextView, priceTextView, validDateTextView;
		ImageView presentImageView;
	}

}
