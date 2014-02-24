package com.example.youlian.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.youlian.R;
import com.example.youlian.mode.CardCost;
import com.example.youlian.util.Utils;

public class MyCardCostAdapter extends BaseAdapter{
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<CardCost> resultList;

	public MyCardCostAdapter(Context context, ArrayList<CardCost> reuslts){
		mContext = context;
		resultList = new ArrayList<CardCost>();
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(Utils.notNull(reuslts)){
			resultList.addAll(reuslts);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultList.size();
	}

	@Override
	public CardCost getItem(int arg0) {
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
		final ViewCache vc;
		if (convertView == null) {
			vc = new ViewCache();
			convertView = mInflater.inflate(R.layout.cost_dialog_item, null);
			vc.name = (TextView)convertView.findViewById(R.id.name);
			vc.mark = (TextView)convertView.findViewById(R.id.mark);
			convertView.setTag(vc);
		}else {
			vc = (ViewCache) convertView.getTag();
		}
		final CardCost fadsResult = this.getItem(position);
		vc.name.setText(fadsResult.productName + "：");
		vc.mark.setText(fadsResult.count + "次");
		return convertView;
	}
	
	private class ViewCache {
		public TextView name,mark;
	}
	 
	public void addAllDataList(ArrayList<CardCost> data){
		if(resultList!=null	&& resultList.size()>0){
			resultList.clear();
		}else{
			resultList = new ArrayList<CardCost>();
		}
		resultList.addAll(data);
		this.notifyDataSetChanged();
	}
}
