package com.example.youlian.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.youlian.R;
import com.example.youlian.mode.MsgCenterResult;
import com.example.youlian.util.Utils;

public class MsgCenterAdapter extends BaseAdapter{
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<MsgCenterResult> resultList;

	public MsgCenterAdapter(Context context, ArrayList<MsgCenterResult> reuslts){
		mContext = context;
		resultList = reuslts;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public MsgCenterResult getItem(int arg0) {
		return resultList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewCache vc;
		if (convertView == null) {
			vc = new ViewCache();
			convertView = mInflater.inflate(R.layout.more_msg_center_listview_item, null);
			vc.titile_text = (TextView)convertView.findViewById(R.id.titile_text);
			vc.date_text = (TextView)convertView.findViewById(R.id.date_text);
			vc.cotent_text = (TextView)convertView.findViewById(R.id.cotent_text);
			vc.det_text = (TextView)convertView.findViewById(R.id.det_text);
			convertView.setTag(vc);
		}else {
			vc = (ViewCache) convertView.getTag();
		}
		
		final MsgCenterResult result = this.getItem(position);
		
		vc.titile_text.setText(result.title);
		vc.date_text.setText(result.time);
		vc.cotent_text.setText(result.introduction);
		vc.det_text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		return convertView;
	}
	
	static class ViewCache {
		public TextView titile_text,date_text,cotent_text,det_text;
	}
	
}
