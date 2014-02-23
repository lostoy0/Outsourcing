package com.example.youlian.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.youlian.MyOrderActivity;
import com.example.youlian.PayActivity;
import com.example.youlian.R;
import com.example.youlian.YouLianHttpApi;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.Order;
import com.example.youlian.mode.OrderDetail;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;

public class OrderListAdapter extends BaseAdapter {
	private static YlLogger mLogger = YlLogger.getLogger(OrderListAdapter.class.getSimpleName());
	
	private MyOrderActivity mActivity;
	private List<Order> mList;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;

	public OrderListAdapter(Context context, List<Order> list, ImageLoader imageLoader) {
		mActivity = (MyOrderActivity) context;
		mInflater = LayoutInflater.from(context);
		mList = list;
		mImageLoader = imageLoader;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Order getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_order, null);
			holder.container = (LinearLayout) convertView.findViewById(R.id.order_item_goods_container);
			holder.moneyTextView = (TextView) convertView.findViewById(R.id.order_item_tv_money);
			holder.quantitytTextView = (TextView) convertView.findViewById(R.id.order_item_tv_quantity);
			holder.statusButton = (Button) convertView.findViewById(R.id.order_item_btn_status);
			holder.cancelButton = (Button) convertView.findViewById(R.id.order_item_btn_cancel);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		setData(holder, mList.get(position));
		
		return convertView;
	}

	private void setData(ViewHolder holder, final Order order) {
		if(holder != null && order != null) {
			List<OrderDetail> details = order.orderDetailList;
			if(details != null && details.size() > 0) {
				float money = 0f;
				int quantity = 0;
				for(int i=0; i<details.size(); i++) {
					OrderDetail detail = details.get(i);
					if(detail != null) {
						money += detail.price*detail.quantity;
						quantity += detail.quantity;
						holder.container.addView(createGoodsItemView(detail));
					}
				}
				
				holder.quantitytTextView.setText("" + quantity);
				holder.moneyTextView.setText("￥" + money);
			}
		}
		
		holder.cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelOrder(order);
			}
		});
		
		// 0:已支付,1:未支付,2:已取消,3:已关闭
		switch(order.status) {
		case 0:
			holder.statusButton.setText("已支付");
			holder.statusButton.setEnabled(false);
			break;
			
		case 1:
			holder.statusButton.setText("未支付");
			holder.statusButton.setEnabled(true);
			holder.statusButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startPay(order);
				}
			});
			break;
			
		case 2:
			holder.statusButton.setText("已取消");
			holder.statusButton.setEnabled(false);
			break;
			
		case 3:
			holder.statusButton.setText("已关闭");
			holder.statusButton.setEnabled(false);
			break;
		}
		
	}
	
	private void startPay(Order order) {
		Intent intent = new Intent(mActivity, PayActivity.class);
		intent.putExtra(PayActivity.KEY_ORDER, order);
		mActivity.startActivity(intent);
	}
	
	private void cancelOrder(Order order) {
		mList.remove(order);
		notifyDataSetChanged();
		YouLianHttpApi.cancelOrder(order.id, createCancelOrderSuccessListener(), createCancelOrderErrorListener());
	}
	
	private Response.Listener<String> createCancelOrderSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						if(0 == object.optInt(Constants.key_status)) {
							Utils.showToast(mActivity, "取消失败");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createCancelOrderErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	Utils.showToast(mActivity, "取消失败");
            }
        };
    }
	
	private View createGoodsItemView(OrderDetail detail) {
		View goodsView = mInflater.inflate(R.layout.item_order_goods, null);
		NetworkImageView imageView = (NetworkImageView) goodsView.findViewById(R.id.goods_item_iv_icon);
		TextView nameTextView = (TextView) goodsView.findViewById(R.id.goods_item_tv_name);
		TextView quantityTextView = (TextView) goodsView.findViewById(R.id.goods_item_tv_amount);
		TextView pricetTextView = (TextView) goodsView.findViewById(R.id.goods_item_tv_price);
		TextView desctTextView = (TextView) goodsView.findViewById(R.id.goods_item_tv_desc);
		
		if(TextUtils.isEmpty(detail.picUrl)) {
			imageView.setImageResource(R.drawable.default_img);
		} else {
			imageView.setImageUrl(detail.picUrl, mImageLoader);
		}
		
		nameTextView.setText(detail.productName);
		quantityTextView.setText("x" + detail.quantity);
		pricetTextView.setText("￥" + detail.price);
		desctTextView.setText(detail.simpleDescription);
		
		return goodsView;
	}

	static class ViewHolder {
		LinearLayout container;
		TextView moneyTextView, quantitytTextView;
		Button statusButton, cancelButton;
	}
}
