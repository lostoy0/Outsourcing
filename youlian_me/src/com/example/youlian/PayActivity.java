package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.android.app.sdk.AliPay;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.adapter.PayOrderListAdapter;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.Order;
import com.example.youlian.mode.OrderDetail;
import com.example.youlian.pay.alipay.Result;
import com.example.youlian.util.YlLogger;

/**
 * 支付界面
 * @author raymond
 *
 */
public class PayActivity extends BaseActivity implements OnClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(PayActivity.class.getSimpleName());
	
	private static final int REQ_ALIPAY_WAP = 1000;
	
	private static final int TYPE_YIPAY = 4;
	private static final int TYPE_UNIONPAY = 3;
	private static final int TYPE_ALIPAY_CLIENT = 2;
	private static final int TYPE_ALIPAY_WEB = 1;
	private static final int TYPE_UCOIN = 0;
	
	private static final int ORDER_TYPE_RECHARGE = 1;//充值订单
	private static final int ORDER_TYPE_GENERAL = 0;//普通订单
	
	public static final String KEY_ORDER = "order";
	
	private int mOrderType = ORDER_TYPE_GENERAL;
	
	private TextView mTotalMoneyTextView;
	private ImageButton mSelectYiPayButton, mSelectUnionButton, mSelectAlipayClientButton, mSelectAlipayButton, mSelectUcoinButton;
	private Button mPayButton;
	
	private ListView mListView;
	private PayOrderListAdapter mAdapter;
	private List<OrderDetail> mList;
	
	private int mSelectedPayType = TYPE_ALIPAY_CLIENT;
	
	private Order mOrder;
	private Order mSettleOrder;
	
	public boolean isRechargeOrder() {
		return mOrderType == ORDER_TYPE_RECHARGE;
	}
	
	private PayHandler mHandler;
	
	private final class PayHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what) {
			case TYPE_ALIPAY_CLIENT:
				Result result = new Result((String) msg.obj);
				result.parseResult();
				finish();
				break;
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		
		mOrder = (Order) getIntent().getSerializableExtra(KEY_ORDER);
		if(mOrder == null) {
			mLogger.e("order is null");
			finish();
		}
		
		String orderNoString = mOrder.orderNo;
		if(!TextUtils.isEmpty(orderNoString) && orderNoString.startsWith("R")) {
			mOrderType = ORDER_TYPE_RECHARGE;
		} else {
			mOrderType = ORDER_TYPE_GENERAL;
		}
		
		mList = new ArrayList<OrderDetail>();
		mHandler = new PayHandler();
		
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
			if(mSelectedPayType != TYPE_YIPAY) {
				mSelectedPayType = TYPE_YIPAY;
				resetSelectState(mSelectedPayType);
			}
			break;
			
		case R.id.pay_union_panel:
		case R.id.pay_tv_union:
			if(mSelectedPayType != TYPE_UNIONPAY) {
				mSelectedPayType = TYPE_UNIONPAY;
				resetSelectState(mSelectedPayType);
			}
			break;
			
		case R.id.pay_alipay_panel_client:
		case R.id.pay_tv_alipay_client:
			if(mSelectedPayType != TYPE_ALIPAY_CLIENT) {
				mSelectedPayType = TYPE_ALIPAY_CLIENT;
				resetSelectState(mSelectedPayType);
			}
			break;
			
		case R.id.pay_alipay_web_panel:
		case R.id.pay_tv_alipay_web:
			if(mSelectedPayType != TYPE_ALIPAY_WEB) {
				mSelectedPayType = TYPE_ALIPAY_WEB;
				resetSelectState(mSelectedPayType);
			}
			break;
			
		case R.id.pay_ucoin_panel:
		case R.id.pay_tv_ucoin:
			if(mSelectedPayType != TYPE_UCOIN) {
				mSelectedPayType = TYPE_UCOIN;
				resetSelectState(mSelectedPayType);
			}
			break;
			
		case R.id.pay_btn_pay:
			startPay();
			break;
		}
	}
	
	private void startPay() {
		switch(mSelectedPayType) {
		case TYPE_YIPAY:
		case TYPE_UCOIN:
		case TYPE_UNIONPAY:
			showToast("抱歉，该支付方式暂未开发完成，请使用其他支付方式支付");
			return;
		}
		
		if(mSelectedPayType == TYPE_ALIPAY_CLIENT) {
			YouLianHttpApi.payOrder(Global.getUserToken(getApplicationContext()), mOrder.id, mSelectedPayType, 
					createPaySuccessListener(), createPayErrorListener());
		} else if(mSelectedPayType == TYPE_ALIPAY_WEB) {
			
			String url = YouLianHttpApi.getUrl("service", "younion.order.pay", "user_token", Global.getUserToken(this), "id",
					mOrder.id, "payType", TYPE_ALIPAY_WEB + "");
			
//			Intent intent = new Intent(PayActivity.this, AliWapPayActivity.class);
//			intent.putExtra("url", url);
//			startActivityForResult(intent, REQ_ALIPAY_WAP);
			
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	}

	private void initViews() {
		((TextView) findViewById(R.id.tv_title)).setText("结算");
		findViewById(R.id.back).setOnClickListener(this);
		
		mTotalMoneyTextView = (TextView) findViewById(R.id.pay_tv_money);
		
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
		
		resetSelectState(mSelectedPayType);
		
		mPayButton = (Button) findViewById(R.id.pay_btn_pay);
		mPayButton.setOnClickListener(this);
		
		findViewById(R.id.pay_yi_panel).setOnClickListener(this);
		findViewById(R.id.pay_union_panel).setOnClickListener(this);
		findViewById(R.id.pay_alipay_web_panel).setOnClickListener(this);
		findViewById(R.id.pay_alipay_panel_client).setOnClickListener(this);
		findViewById(R.id.pay_ucoin_panel).setOnClickListener(this);
		
		findViewById(R.id.pay_tv_yi).setOnClickListener(this);
		findViewById(R.id.pay_tv_union).setOnClickListener(this);
		findViewById(R.id.pay_tv_alipay_web).setOnClickListener(this);
		findViewById(R.id.pay_tv_alipay_client).setOnClickListener(this);
		findViewById(R.id.pay_tv_ucoin).setOnClickListener(this);
		
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new PayOrderListAdapter(this, mList);
		mListView.setAdapter(mAdapter);
	}
	
	private void resetSelectState(int selected) {
		switch(selected) {
		case TYPE_UCOIN:
			mSelectUcoinButton.setVisibility(View.VISIBLE);
			mSelectUnionButton.setVisibility(View.GONE);
			mSelectYiPayButton.setVisibility(View.GONE);
			mSelectAlipayButton.setVisibility(View.GONE);
			mSelectAlipayClientButton.setVisibility(View.GONE);
			break;
			
		case TYPE_UNIONPAY:
			mSelectUcoinButton.setVisibility(View.GONE);
			mSelectUnionButton.setVisibility(View.VISIBLE);
			mSelectYiPayButton.setVisibility(View.GONE);
			mSelectAlipayButton.setVisibility(View.GONE);
			mSelectAlipayClientButton.setVisibility(View.GONE);
			break;
			
		case TYPE_ALIPAY_WEB:
			mSelectUcoinButton.setVisibility(View.GONE);
			mSelectUnionButton.setVisibility(View.GONE);
			mSelectYiPayButton.setVisibility(View.GONE);
			mSelectAlipayButton.setVisibility(View.VISIBLE);
			mSelectAlipayClientButton.setVisibility(View.GONE);
			break;
			
		case TYPE_ALIPAY_CLIENT:
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
								if(isRechargeOrder()) {
									mTotalMoneyTextView.setText("总计：" + mSettleOrder.youcoinCount + "元");
								} else {
									mTotalMoneyTextView.setText("总计：" + mSettleOrder.youcoinCount + "U币");
								}
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
	
	private Response.Listener<String> createPaySuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					/*if(mSelectedPayType == TYPE_ALIPAY_WEB) {
						Intent intent = new Intent(PayActivity.this, AliWapPayActivity.class);
						intent.putExtra("wap", response);
						startActivityForResult(intent, REQ_ALIPAY_WAP);
						return;
					}*/
					
					try {
						JSONObject object = new JSONObject(response);
						if(0 == object.optInt(Constants.key_status)) {
							showToast(object.optString(Constants.KEY_MSG));
						} else {
							handlePay(object);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createPayErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
	
	private void handlePay(JSONObject json) {
		switch(mSelectedPayType) {
		case TYPE_UCOIN:
			
			break;
			
		case TYPE_ALIPAY_WEB:
			
			break;
			
		case TYPE_ALIPAY_CLIENT:
			String orderInfo = json.optString(Constants.key_result);
			alipayClient(orderInfo);
			break;
			
		case TYPE_UNIONPAY:
			
			break;
			
		case TYPE_YIPAY:
			
			break;
		}
	}
	
	private void alipayClient(final String orderInfo) {
		if(TextUtils.isEmpty(orderInfo)) return;
		
		new Thread() {
			public void run() {
				AliPay alipay = new AliPay(PayActivity.this, mHandler);
				
				//设置为沙箱模式，不设置默认为线上环境
//				alipay.setSandBox(true);

				String result = alipay.pay(orderInfo);

				mLogger.i(result);
				Message msg = new Message();
				msg.what = TYPE_ALIPAY_CLIENT;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}.start();
	}
	
}
