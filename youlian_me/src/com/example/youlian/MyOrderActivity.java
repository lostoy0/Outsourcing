package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.adapter.OrderListAdapter;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Order;
import com.example.youlian.util.YlLogger;

public class MyOrderActivity extends BaseActivity {
	private static YlLogger mLogger = YlLogger.getLogger(MyOrderActivity.class.getSimpleName());
	
	private ListView mListView;
	private OrderListAdapter mAdapter;
	private List<Order> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		mList = new ArrayList<Order>();
		
		initViews();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		YouLianHttpApi.getOrder(Global.getUserToken(this), createGetOrderListSuccessListener(), createGetOrderListErrorListener());
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initViews() {
		((TextView)findViewById(R.id.tv_title)).setText("订单");
		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new OrderListAdapter(this, mList, MyVolley.getImageLoader());
		mListView.setAdapter(mAdapter);
	}
	
	private Response.Listener<String> createGetOrderListSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						List<Order> orders = Order.getOrders(object);
						if(orders != null && orders.size() > 0) {
							mList.addAll(orders);
							mAdapter.notifyDataSetChanged();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createGetOrderListErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
	
}
