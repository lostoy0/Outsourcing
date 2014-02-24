package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.adapter.PayOrderListAdapter;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.Order;
import com.example.youlian.mode.OrderDetail;
import com.example.youlian.util.YlLogger;

/**
 * 支付界面
 * @author raymond
 *
 */
public class PayActivity extends BaseActivity implements OnClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(PayActivity.class.getSimpleName());
	
	private static final int YIPAY = 1;
	private static final int UNIONPAY = 2;
	private static final int ALIPAY_CLIENT = 3;
	private static final int ALIPAY = 4;
	private static final int UCOIN = 5;
	
	public static final String KEY_ORDER = "order";
	
	private TextView mShopNameTextView, mTotalMoneyTextView, mCheckAlipayClienTextView;
	private ImageButton mSelectYiPayButton, mSelectUnionButton, mSelectAlipayClientButton, mSelectAlipayButton, mSelectUcoinButton;
	private Button mPayButton;
	
	private ListView mListView;
	private PayOrderListAdapter mAdapter;
	private List<OrderDetail> mList;
	
	private int mSelected = YIPAY;
	
	private Order mOrder;
	private Order mSettleOrder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		
		mOrder = (Order) getIntent().getSerializableExtra(KEY_ORDER);
		if(mOrder == null) {
			mLogger.e("order is null");
			finish();
		}
		
		mList = new ArrayList<OrderDetail>();
		
		initViews();
		
		YouLianHttpApi.orderSettle(mOrder.id, createSettleOrderSuccessListener(), createSettleOrderErrorListener());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.back:
			finish();
			break;
			
		case R.id.pay_yi_panel:
		case R.id.pay_tv_yi:
			if(mSelected != YIPAY) {
				mSelected = YIPAY;
				resetSelectState(mSelected);
			}
			break;
			
		case R.id.pay_union_panel:
		case R.id.pay_tv_union:
			if(mSelected != UNIONPAY) {
				mSelected = UNIONPAY;
				resetSelectState(mSelected);
			}
			break;
			
		case R.id.pay_alipay_panel_client:
		case R.id.pay_tv_alipay_client:
			if(mSelected != ALIPAY_CLIENT) {
				mSelected = ALIPAY_CLIENT;
				resetSelectState(mSelected);
			}
			break;
			
		case R.id.pay_alipay_panel:
		case R.id.pay_tv_alipay:
			if(mSelected != ALIPAY) {
				mSelected = ALIPAY;
				resetSelectState(mSelected);
			}
			break;
			
		case R.id.pay_ucoin_panel:
		case R.id.pay_tv_ucoin:
			if(mSelected != UCOIN) {
				mSelected = UCOIN;
				resetSelectState(mSelected);
			}
			break;
			
		case R.id.pay_btn_pay:
			startPay();
			break;
		}
	}
	
	private void startPay() {
		switch(mSelected) {
		case YIPAY:
			yiPay();
			break;
			
		case ALIPAY:
			alipay();
			break;
			
		case ALIPAY_CLIENT:
			alipayClient();
			break;
			
		case UCOIN:
			ucoinPay();
			break;
			
		case UNIONPAY:
			unionPay();
			break;
		}
	}

	private void yiPay() {
		// TODO Auto-generated method stub
		
	}

	private void alipay() {
		// TODO Auto-generated method stub
		
	}

	private void alipayClient() {
		// TODO Auto-generated method stub
		
	}

	private void ucoinPay() {
		// TODO Auto-generated method stub
		
	}

	private void unionPay() {
		// TODO Auto-generated method stub
		
	}

	private void initViews() {
		((TextView) findViewById(R.id.tv_title)).setText("结算");
		findViewById(R.id.back).setOnClickListener(this);
		
		mShopNameTextView = (TextView) findViewById(R.id.pay_tv_shopname);
		mTotalMoneyTextView = (TextView) findViewById(R.id.pay_tv_money);
		mCheckAlipayClienTextView = (TextView) findViewById(R.id.pay_tv_alipay_client_check);
		
		mSelectAlipayButton = (ImageButton) findViewById(R.id.pay_ib_select_alipay);
		mSelectAlipayClientButton = (ImageButton) findViewById(R.id.pay_ib_select_alipay_client);
		mSelectYiPayButton = (ImageButton) findViewById(R.id.pay_ib_select_yi);
		mSelectUcoinButton = (ImageButton) findViewById(R.id.pay_ib_select_ucoin);
		mSelectUnionButton = (ImageButton) findViewById(R.id.pay_ib_select_union);
		
		mSelectAlipayButton.setOnClickListener(this);
		mSelectAlipayClientButton.setOnClickListener(this);
		mSelectUcoinButton.setOnClickListener(this);
		mSelectUnionButton.setOnClickListener(this);
		mSelectYiPayButton.setOnClickListener(this);
		
		resetSelectState(mSelected);
		
		mPayButton = (Button) findViewById(R.id.pay_btn_pay);
		mPayButton.setOnClickListener(this);
		
		findViewById(R.id.pay_yi_panel).setOnClickListener(this);
		findViewById(R.id.pay_union_panel).setOnClickListener(this);
		findViewById(R.id.pay_alipay_panel).setOnClickListener(this);
		findViewById(R.id.pay_alipay_panel_client).setOnClickListener(this);
		findViewById(R.id.pay_ucoin_panel).setOnClickListener(this);
		
		findViewById(R.id.pay_tv_yi).setOnClickListener(this);
		findViewById(R.id.pay_tv_union).setOnClickListener(this);
		findViewById(R.id.pay_tv_alipay).setOnClickListener(this);
		findViewById(R.id.pay_tv_alipay_client).setOnClickListener(this);
		findViewById(R.id.pay_tv_ucoin).setOnClickListener(this);
		
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new PayOrderListAdapter(this, mList);
		mListView.setAdapter(mAdapter);
	}
	
	private void resetSelectState(int selected) {
		switch(selected) {
		case UCOIN:
			mSelectUcoinButton.setVisibility(View.VISIBLE);
			mSelectUnionButton.setVisibility(View.GONE);
			mSelectYiPayButton.setVisibility(View.GONE);
			mSelectAlipayButton.setVisibility(View.GONE);
			mSelectAlipayClientButton.setVisibility(View.GONE);
			break;
			
		case UNIONPAY:
			mSelectUcoinButton.setVisibility(View.GONE);
			mSelectUnionButton.setVisibility(View.VISIBLE);
			mSelectYiPayButton.setVisibility(View.GONE);
			mSelectAlipayButton.setVisibility(View.GONE);
			mSelectAlipayClientButton.setVisibility(View.GONE);
			break;
			
		case ALIPAY:
			mSelectUcoinButton.setVisibility(View.GONE);
			mSelectUnionButton.setVisibility(View.GONE);
			mSelectYiPayButton.setVisibility(View.GONE);
			mSelectAlipayButton.setVisibility(View.VISIBLE);
			mSelectAlipayClientButton.setVisibility(View.GONE);
			break;
			
		case ALIPAY_CLIENT:
			mSelectUcoinButton.setVisibility(View.GONE);
			mSelectUnionButton.setVisibility(View.GONE);
			mSelectYiPayButton.setVisibility(View.GONE);
			mSelectAlipayButton.setVisibility(View.GONE);
			mSelectAlipayClientButton.setVisibility(View.VISIBLE);
			break;
			
		default:
			mSelectUcoinButton.setVisibility(View.GONE);
			mSelectUnionButton.setVisibility(View.GONE);
			mSelectYiPayButton.setVisibility(View.VISIBLE);
			mSelectAlipayButton.setVisibility(View.GONE);
			mSelectAlipayClientButton.setVisibility(View.GONE);
			break;
		}
	}
	
	private Response.Listener<String> createSettleOrderSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						Order order = Order.from(object.optJSONObject(Constants.key_result));
						if(order != null) {
							mSettleOrder = order;
							if(mSettleOrder.orderDetailList != null && mSettleOrder.orderDetailList.size() > 0) {
								mList.addAll(mSettleOrder.orderDetailList);
								mAdapter.notifyDataSetChanged();
								int cost = 0;
								for(OrderDetail detail : mList) {
									cost += detail.price*detail.quantity;
								}
								mTotalMoneyTextView.setText("总计：" + cost);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createSettleOrderErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }

}
