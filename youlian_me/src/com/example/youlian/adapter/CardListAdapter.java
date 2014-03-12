package com.example.youlian.adapter;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.youlian.CardListActivity;
import com.example.youlian.R;
import com.example.youlian.YouLianHttpApi;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.Card;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;

public class CardListAdapter extends BaseAdapter {
	private YlLogger mLogger = YlLogger.getLogger(CardListAdapter.class.getSimpleName());

	private LayoutInflater mInflater;
	private ArrayList<Card> mCards;
	private ImageLoader mImageLoader;
	private CardListActivity mContext;
	
	public CardListAdapter(CardListActivity context, ArrayList<Card> cards, ImageLoader imageLoader) {
		mContext = context;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_card_list, null);
			holder.deleteButton = (ImageButton) convertView.findViewById(R.id.card_item_ib_delete);
			holder.iconImageView = (NetworkImageView) convertView.findViewById(R.id.card_item_iv_icon);
			holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar1);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.card_item_tv_name);
			holder.typeTextView = (TextView) convertView.findViewById(R.id.card_item_tv_type);
			holder.couponTextView = (TextView) convertView.findViewById(R.id.card_item_tv_coupon);
			holder.onlinePayImageView = (ImageView) convertView.findViewById(R.id.iv_online_pay);
			holder.yiPayImageView = (ImageView) convertView.findViewById(R.id.iv_yi_pay);
			convertView.setTag(holder);
		}
		
		final Card card = getItem(position);
		
		if(mContext.isEditing()) {
			holder.deleteButton.setVisibility(View.VISIBLE);
			holder.deleteButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(card != null) {
						YouLianHttpApi.deleteCard(card.card_id, "1", delSuccessListener(position), delErrorListener());
					}
				}
			});
		} else {
			holder.deleteButton.setVisibility(View.GONE);
		}
		
		setData(holder, card);
		
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
		ImageButton deleteButton;
	}
	
	private Response.Listener<String> delSuccessListener(final int position) {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					try {
						JSONObject object = new JSONObject(response);
						if("1".equals(object.optString(Constants.key_status))) {
							mCards.remove(position);
							notifyDataSetChanged();
						} else {
							Utils.showToast(mContext, "删除失败");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener delErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	Utils.showToast(mContext, "删除失败");
            }
        };
    }

}
