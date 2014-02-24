package com.example.youlian.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.youlian.R;
import com.example.youlian.mode.ActivitySign;

public class ActivitySignListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<ActivitySign> mList;
	private ImageLoader mImageLoader;
	
	public ActivitySignListAdapter(Context context, List<ActivitySign> list, ImageLoader imageLoader) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mImageLoader = imageLoader;
		mList = list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
//		return 10;
	}

	@Override
	public ActivitySign getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_activity_sign, null);
			holder.iconImageView = (NetworkImageView) convertView.findViewById(R.id.iv_icon);
			holder.titleTextView = (TextView) convertView.findViewById(R.id.tv_title);
			holder.contentTextView = (TextView) convertView.findViewById(R.id.tv_content);
			holder.shopNameTextView = (TextView) convertView.findViewById(R.id.tv_shop_name);
			holder.validDateTextView = (TextView) convertView.findViewById(R.id.tv_valid_date);
			holder.distanceTextView = (TextView) convertView.findViewById(R.id.tv_distance);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		setData(holder, mList.get(position));
		
		return convertView;
	}

	private void setData(ViewHolder holder, ActivitySign activitySign) {
		if(holder != null && activitySign != null) {
			if(TextUtils.isEmpty(activitySign.logo)) {
				holder.iconImageView.setImageResource(R.drawable.default_img);
			} else {
				holder.iconImageView.setImageUrl(activitySign.logo, mImageLoader);
			}
			holder.titleTextView.setText(activitySign.title);
			holder.contentTextView.setText(activitySign.content);
			holder.shopNameTextView.setText(activitySign.customerName);
			holder.validDateTextView.setText("有效期：" + activitySign.begtime.substring(0, 10) + "\n至" + activitySign.endtime.substring(0, 10));
		}
	}

	static class ViewHolder {
		NetworkImageView iconImageView;
		TextView titleTextView, contentTextView, shopNameTextView, validDateTextView, distanceTextView; 
	}
}
