package com.example.youlian.more;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.R;
import com.example.youlian.app.MyVolley;
import com.example.youlian.common.Configure;
import com.example.youlian.mode.AppRecResult;
import com.example.youlian.mode.Pic;
import com.example.youlian.util.Utils;

/**
 * @author rome
 * @proName youlian
 * @version 1.0
 * @Data 2012-9-28 ����12:08:46
 * 
 *       <b>Comment...</b>
 */
public class AppRemStoreAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<AppRecResult> resultList;
	private ImageLoader imageLoader;

	public AppRemStoreAdapter(Context mContext, ArrayList<AppRecResult> reuslts) {
		this.mContext = mContext;
		resultList = new ArrayList<AppRecResult>();
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = MyVolley.getImageLoader();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultList == null ? 0 : resultList.size();
	}

	@Override
	public AppRecResult getItem(int arg0) {
		// TODO Auto-generated method stub
		return resultList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewCache vc;
		if (convertView == null) {
			vc = new ViewCache();
			convertView = mInflater.inflate(R.layout.more_app_rem_item, null);
			vc.app_name = (TextView) convertView.findViewById(R.id.app_name);
			vc.app_content = (TextView) convertView
					.findViewById(R.id.app_content);
			vc.app_img = (ImageView) convertView.findViewById(R.id.app_img);
			convertView.setTag(vc);
		} else {
			vc = (ViewCache) convertView.getTag();
		}
		final AppRecResult result = this.getItem(position);
		vc.app_name.setText(result.getAppName());
		vc.app_content.setText(result.getIntroduction());
		String fileUrl = result.getPic();
		imageLoader.get(fileUrl, ImageLoader.getImageListener(vc.app_img,
				R.drawable.guanggao, R.drawable.guanggao));
		return convertView;
	}

	private class ViewCache {
		public TextView app_name, app_content;
		public ImageView app_img;
	}

	public void addAllDataList(ArrayList<AppRecResult> data) {
		if (resultList != null && resultList.size() > 0) {
			resultList.clear();
		} else {
			resultList = new ArrayList<AppRecResult>();
		}
		resultList.addAll(data);
		this.notifyDataSetChanged();
	}
}
