package com.example.youlian.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.youlian.R;
import com.example.youlian.mode.OrderDetail;

public class PayOrderListAdapter extends BaseAdapter {
	
	private List<OrderDetail> mList;
	private LayoutInflater mInflater;

	public PayOrderListAdapter(Context context, List<OrderDetail> list) {
		mInflater = LayoutInflater.from(context);
		mList = list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public OrderDetail getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_pay_order, null);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.pay_tv_goodsname);
			holder.pricetTextView = (TextView) convertView.findViewById(R.id.pay_tv_price);
			holder.quantityTextView = (TextView) convertView.findViewById(R.id.pay_tv_amount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		setData(holder, mList.get(position));
		
		return convertView;
	}

	private void setData(ViewHolder holder, OrderDetail orderDetail) {
		if(holder != null && orderDetail != null) {
			holder.nameTextView.setText(orderDetail.productName);
			holder.pricetTextView.setText(orderDetail.price + "");
			holder.quantityTextView.setText(orderDetail.quantity + "");
		}
	}

	static class ViewHolder {
		TextView nameTextView, pricetTextView, quantityTextView;
	}
}
