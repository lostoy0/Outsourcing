package com.example.youlian.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.youlian.R;
import com.example.youlian.mode.RegioninfoVO;

public class AreaListAdapter extends BaseAdapter {
	
	private ArrayList<RegioninfoVO> mRegioninfoVOs;
	private LayoutInflater mInflater;

	public AreaListAdapter(Context context, ArrayList<RegioninfoVO> vos) {
		mInflater = LayoutInflater.from(context);
		mRegioninfoVOs = vos;
		if(mRegioninfoVOs == null) mRegioninfoVOs = new ArrayList<RegioninfoVO>();
	}
	
	@Override
	public int getCount() {
		return mRegioninfoVOs.size();
	}

	@Override
	public RegioninfoVO getItem(int position) {
		return mRegioninfoVOs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.item_area_list, null);
		((TextView)convertView).setText(mRegioninfoVOs.get(position).areaName);
		return convertView;
	}
	
}
