package com.example.youlian.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.youlian.R;
import com.example.youlian.mode.Card;

public class CardListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Card> mCards;
	private ImageLoader mImageLoader;
	
	public CardListAdapter(Context context, ArrayList<Card> cards, ImageLoader imageLoader) {
		mInflater = LayoutInflater.from(context);
		mCards = cards;
		mImageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return mCards.size();
//		return 5;
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
			holder.iconImageView = (NetworkImageView) convertView.findViewById(R.id.card_item_iv_icon);
			holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar1);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.card_item_tv_name);
			holder.typeTextView = (TextView) convertView.findViewById(R.id.card_item_tv_type);
			holder.couponTextView = (TextView) convertView.findViewById(R.id.card_item_tv_coupon);
			holder.onlinePayImageView = (ImageView) convertView.findViewById(R.id.iv_online_pay);
			holder.yiPayImageView = (ImageView) convertView.findViewById(R.id.iv_yi_pay);
			convertView.setTag(holder);
		}
		
		setData(holder, getItem(position));
		
		return convertView;
	}
	
	private void setData(ViewHolder holder, Card item) {
		if(item != null) {
			if(item.nonactivatedPic != null){
				holder.iconImageView.setImageUrl(item.nonactivatedPic, mImageLoader);
			}else{
				holder.iconImageView.setImageResource(R.drawable.default_img);
			}
			
			holder.nameTextView.setText(item.card_name);
			
			if(!TextUtils.isEmpty(item.starLevel)) {
				float startLvl = Float.parseFloat(item.starLevel);
				holder.ratingBar.setRating(startLvl);
			} else {
				holder.ratingBar.setRating(0.0f);
			}
			
			holder.typeTextView.setText(item.cardLevel);
			holder.couponTextView.setText("(" + item.card_content + ")");
			if(Integer.parseInt(item.canOnlinePay) == 1){
				holder.onlinePayImageView.setVisibility(View.VISIBLE);
			}else{
				holder.onlinePayImageView.setVisibility(View.GONE);
			}
			if(Integer.parseInt(item.canBestPay) == 1){
				holder.yiPayImageView.setVisibility(View.VISIBLE);
			}else{
				holder.yiPayImageView.setVisibility(View.GONE);
			}
		}
	}

	static class ViewHolder {
		NetworkImageView iconImageView;
		RatingBar ratingBar;
		TextView nameTextView, typeTextView, couponTextView;
		ImageView onlinePayImageView, yiPayImageView;
	}

}
