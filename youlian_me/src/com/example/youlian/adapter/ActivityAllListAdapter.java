package com.example.youlian.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.youlian.R;
import com.example.youlian.mode.ActivityAll;

public class ActivityAllListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<ActivityAll> mList;
	private ImageLoader mImageLoader;
	private int mScreenWidth;
	private int mImageWidth;
	
	public ActivityAllListAdapter(Context context, List<ActivityAll> list, ImageLoader imageLoader) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mImageLoader = imageLoader;
		mList = list;
		initImageParams();
	}

	private void initImageParams() {
		DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		mScreenWidth = metrics.widthPixels;
		int colSpacing = mContext.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
		mImageWidth = (mScreenWidth-colSpacing*3)/2;
	}

	@Override
	public int getCount() {
		return mList.size();
//		return 10;
	}

	@Override
	public ActivityAll getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_activity_all, null);
			holder.contentTextView = (TextView) convertView.findViewById(R.id.tv_content);
			holder.iconImageView = (NetworkImageView) convertView.findViewById(R.id.iv_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		setData(holder, mList.get(position));
		
		return convertView;
	}

	private void setData(ViewHolder holder, ActivityAll activityAll) {
		if(holder != null && activityAll != null) {
			holder.contentTextView.setText(activityAll.title);
			if(TextUtils.isEmpty(activityAll.pic)) {
				holder.iconImageView.setImageResource(R.drawable.default_img);
			} else {
				holder.iconImageView.setMinimumWidth(mImageWidth);
				int height = mImageWidth*activityAll.pic_height/activityAll.pic_width;
				holder.iconImageView.setMinimumHeight(height);
				holder.iconImageView.setImageUrl(activityAll.pic, mImageLoader);
			}
		}
	}

	static class ViewHolder {
		NetworkImageView iconImageView;
		TextView contentTextView;
	}
}
