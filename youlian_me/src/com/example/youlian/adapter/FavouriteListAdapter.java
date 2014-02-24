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
import com.example.youlian.FavouriteListActivity;
import com.example.youlian.R;
import com.example.youlian.mode.Card;
import com.example.youlian.mode.Favourite;

public class FavouriteListAdapter extends BaseAdapter {

	private FavouriteListActivity mContext;
	private LayoutInflater mInflater;
	private ArrayList<Favourite> mList;
	private ImageLoader mImageLoader;
	
	public FavouriteListAdapter(FavouriteListActivity context, ArrayList<Favourite> cards, ImageLoader imageLoader) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mList = cards;
		mImageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return mList.size();
//		return 5;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_favourite_list, null);
			holder.iconImageView = (NetworkImageView) convertView.findViewById(R.id.favourite_item_iv_icon);
			holder.deleteButton = (ImageButton) convertView.findViewById(R.id.favourite_item_ib_delete);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.favourite_item_tv_name);
			holder.priceTextView = (TextView) convertView.findViewById(R.id.favourite_item_tv_price);
			holder.validDateTextView = (TextView) convertView.findViewById(R.id.favourite_item_tv_validdate);
			convertView.setTag(holder);
		}
		
		if(mContext.getEditState()) {
			holder.deleteButton.setVisibility(View.VISIBLE);
			holder.deleteButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mList.remove(position);
					notifyDataSetChanged();
				}
			});
		} else {
			holder.deleteButton.setVisibility(View.GONE);
		}
		
		setData(holder, getItem(position));
		
		return convertView;
	}
	
	private void setData(ViewHolder holder, Favourite item) {
		if(item != null) {
			if(!TextUtils.isEmpty(item.nonactivatedPic)) {
				holder.iconImageView.setImageUrl(item.nonactivatedPic, mImageLoader);
			} else if(!TextUtils.isEmpty(item.activatedPic)) {
				holder.iconImageView.setImageUrl(item.activatedPic, mImageLoader);
			} else {
				holder.iconImageView.setImageResource(R.drawable.default_img);
			}
			
			holder.nameTextView.setText(item.name);
			holder.priceTextView.setText("关注数量：" + item.amount);
			holder.validDateTextView.setText("");
		}
	}

	static class ViewHolder {
		NetworkImageView iconImageView;
		ImageButton deleteButton;
		TextView nameTextView, priceTextView, validDateTextView;
	}

}
