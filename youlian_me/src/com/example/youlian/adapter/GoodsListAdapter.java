package com.example.youlian.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.youlian.R;
import com.example.youlian.TabShopCart;
import com.example.youlian.mode.Goods;

public class GoodsListAdapter extends BaseAdapter {

	private TabShopCart mContext;
	private LayoutInflater mInflater;
	private ArrayList<Goods> mGoodsList;
	private HashMap<String, Boolean> mStateMap;
	private ImageLoader mImageLoader;
	
	public GoodsListAdapter(Context context, ArrayList<Goods> goodsList, HashMap<String, Boolean> stateMap, ImageLoader imageLoader) {
		mContext = (TabShopCart) context;
		mInflater = LayoutInflater.from(context);
		mGoodsList = goodsList;
		mImageLoader = imageLoader;
		mStateMap = stateMap;
	}
	
	@Override
	public int getCount() {
		return mGoodsList.size();
//		return 5;
	}

	@Override
	public Goods getItem(int position) {
		return mGoodsList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_goods_list, null);
			holder.iconImageView = (NetworkImageView) convertView.findViewById(R.id.goods_item_iv_icon);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.goods_item_tv_name);
			holder.amountTextView = (TextView) convertView.findViewById(R.id.goods_item_tv_amount);
			holder.priceTextView = (TextView) convertView.findViewById(R.id.goods_item_tv_price);
			holder.changeAmountPanel = convertView.findViewById(R.id.goods_item_change_amount_panel);
			holder.changeAmountTextView = (TextView) convertView.findViewById(R.id.goods_item_tv_amount_change);
			holder.minusButton = (ImageButton) convertView.findViewById(R.id.goods_item_ib_minus);
			holder.plusButton = (ImageButton) convertView.findViewById(R.id.goods_item_ib_plus);
			holder.unselectButton = (ImageButton) convertView.findViewById(R.id.goods_item_ib_unselect);
			holder.selectButton = (ImageButton) convertView.findViewById(R.id.goods_item_ib_selected);
			convertView.setTag(holder);
		}
		
		setData(holder, getItem(position), position);
		
		return convertView;
	}
	
	private void setData(final ViewHolder holder, final Goods item, final int position) {
		if(item != null) {
			if(!TextUtils.isEmpty(item.goodsPicUrl)){
				holder.iconImageView.setImageUrl(item.goodsPicUrl, mImageLoader);
			}else{
				holder.iconImageView.setImageResource(R.drawable.default_img);
			}
			
			holder.nameTextView.setText(item.goodsName);
			holder.amountTextView.setText("数量：" + item.quantity);
			holder.priceTextView.setText("单价：" + item.goodsPrice + "元");
			
			holder.selectButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mStateMap.put(item.goodsId, false);
					unselect(holder);
					resetData();
				}
			});
			
			holder.unselectButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mStateMap.put(item.goodsId, true);
					select(holder);
					resetData();
				}
			});
			
			if(mStateMap.get(item.goodsId)) {
				select(holder);
			} else {
				unselect(holder);
			}
			
			if(mContext.isEditing()) {
				holder.changeAmountPanel.setVisibility(View.VISIBLE);
				holder.changeAmountTextView.setText("" + item.quantity);
				holder.minusButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int amount = Integer.parseInt(holder.changeAmountTextView.getText().toString().trim());
						if(amount > 0) {
							amount --;
							holder.changeAmountTextView.setText(amount + "");
							holder.amountTextView.setText(amount + "");
							if(mStateMap.get(item.goodsId)) mContext.resetQuantityAndMoney();
						}
					}
				});
				holder.plusButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int amount = Integer.parseInt(holder.changeAmountTextView.getText().toString().trim());
						amount ++;
						holder.changeAmountTextView.setText(amount + "");
						holder.amountTextView.setText(amount + "");
						if(mStateMap.get(item.goodsId)) mContext.resetQuantityAndMoney();
					}
				});
			} else {
				holder.changeAmountPanel.setVisibility(View.GONE);
			}
		}
	}

	private void unselect(final ViewHolder holder) {
		holder.selectButton.setVisibility(View.GONE);
		holder.unselectButton.setEnabled(true);
	}

	private void select(final ViewHolder holder) {
		holder.selectButton.setVisibility(View.VISIBLE);
		holder.unselectButton.setEnabled(false);
	}
	
	private void resetData() {
		mContext.resetQuantityAndMoney();
		if(mContext.isEditing()) {
			mContext.resetDeleteButton();
		}
	}

	static class ViewHolder {
		NetworkImageView iconImageView;
		TextView nameTextView, amountTextView, priceTextView, changeAmountTextView;
		ImageButton minusButton, plusButton, unselectButton, selectButton;
		View changeAmountPanel;
	}

}
