package com.example.youlian.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youlian.FavouriteListActivity;
import com.example.youlian.R;
import com.example.youlian.mode.Card;
import com.example.youlian.mode.Favourite;

public class FavouriteListAdapter extends BaseAdapter {

	private FavouriteListActivity mContext;
	private LayoutInflater mInflater;
	private ArrayList<Favourite> mList;
	
	public FavouriteListAdapter(FavouriteListActivity context, ArrayList<Favourite> cards) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mList = cards;
	}

	@Override
	public int getCount() {
//		return mCards.size();
		return 5;
	}

	@Override
	public Favourite getItem(int position) {
		return mList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_favourite_list, null);
			holder.iconImageView = (ImageView) convertView.findViewById(R.id.favourite_item_iv_icon);
			holder.deleteButton = (ImageButton) convertView.findViewById(R.id.favourite_item_ib_delete);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.favourite_item_tv_name);
			holder.numberTextView = (TextView) convertView.findViewById(R.id.favourite_item_tv_number);
			holder.priceTextView = (TextView) convertView.findViewById(R.id.favourite_item_tv_price);
			holder.validDateTextView = (TextView) convertView.findViewById(R.id.favourite_item_tv_validdate);
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
