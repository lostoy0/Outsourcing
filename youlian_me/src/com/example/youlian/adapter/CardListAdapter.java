package com.example.youlian.adapter;

import java.util.ArrayList;

import com.example.youlian.R;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class CardListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Card> mCards;
	
	public CardListAdapter(Context context, ArrayList<Card> cards) {
		mInflater = LayoutInflater.from(context);
		mCards = cards;
	}

	@Override
	public int getCount() {
//		return mCards.size();
		return 5;
	}

	@Override
	public Card getItem(int position) {
		return mCards.get(position);
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
			convertView = mInflater.inflate(R.layout.item_card_list, null);
			holder.iconImageView = (ImageView) convertView.findViewById(R.id.card_item_iv_icon);
			holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar1);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.card_item_tv_name);
			holder.typeTextView = (TextView) convertView.findViewById(R.id.card_item_tv_type);
			holder.couponTextView = (TextView) convertView.findViewById(R.id.card_item_tv_coupon);
			convertView.setTag(holder);
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
		RatingBar ratingBar;
		TextView nameTextView, typeTextView, couponTextView;
	}

}
