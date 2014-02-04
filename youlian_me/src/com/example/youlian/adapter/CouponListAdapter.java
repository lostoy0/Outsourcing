package com.example.youlian.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youlian.CouponListActivity;
import com.example.youlian.R;
import com.example.youlian.mode.Card;
import com.example.youlian.mode.YouhuiQuan;

public class CouponListAdapter extends BaseAdapter {

	private CouponListActivity mContext;
	private LayoutInflater mInflater;
	private ArrayList<YouhuiQuan> mCouponList;
	
	public CouponListAdapter(CouponListActivity context, ArrayList<YouhuiQuan> cards) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mCouponList = cards;
	}

	@Override
	public int getCount() {
//		return mCards.size();
		return 5;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_coupon_list, null);
			holder.iconImageView = (ImageView) convertView.findViewById(R.id.coupon_item_iv_icon);
			holder.deleteButton = (ImageButton) convertView.findViewById(R.id.coupon_item_ib_delete);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.coupon_item_tv_name);
			holder.numberTextView = (TextView) convertView.findViewById(R.id.coupon_item_tv_number);
			holder.priceTextView = (TextView) convertView.findViewById(R.id.coupon_item_tv_price);
			holder.validDateTextView = (TextView) convertView.findViewById(R.id.coupon_item_tv_validdate);
			convertView.setTag(holder);
		}
		
		if(mContext.getEditState()) {
			holder.deleteButton.setVisibility(View.VISIBLE);
			holder.deleteButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
		} else {
			holder.deleteButton.setVisibility(View.GONE);
		}
		
//		setData(holder, getItem(position));
		
		return convertView;
	}
	
	private void setData(ViewHolder holder, Card item) {
		if(item != null) {
			
		}
	}

	static class ViewHolder {
		ImageView iconImageView;
		ImageButton deleteButton;
		TextView nameTextView, numberTextView, priceTextView, validDateTextView;
	}

}
