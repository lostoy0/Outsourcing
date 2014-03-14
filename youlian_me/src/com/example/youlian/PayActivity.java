package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.android.app.sdk.AliPay;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.Order;
import com.example.youlian.mode.OrderDetail;
import com.example.youlian.more.MsgCenterDetActivity;
import com.example.youlian.pay.alipay.Result;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.YlLogger;
import com.example.youlian.view.SimpleProgressDialog;
import com.youlian.view.dialog.HuzAlertDialog;

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
	
	public static final String KEY_ORDER = "order";
	
	private TextView mTotalMoneyTextView;
	private ImageButton mSelectYiPayButton, mSelectUnionButton, mSelectAlipayClientButton, mSelectAlipayButton, mSelectUcoinButton;
	private Button mPayButton;
	
	private LinearLayout mContainer;
	private List<OrderDetail> mList;
	
	private int mSelectedPayType = TYPE_ALIPAY_CLIENT;
	
	private Order mOrder;
	private Order mSettleOrder;
	
	public boolean isRechargeOrder() {
		if(mSettleOrder != null && mSettleOrder.orderNo != null && mSettleOrder.orderNo.startsWith("R")) {
			return true;
		}
		return false;
	}
	
	private PayHandler mHandler;
	
	@SuppressLint("HandlerLeak")
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
		
		mList = new ArrayList<OrderDetail>();
		mHandler = new PayHandler();
		
		initViews();
		
		SimpleProgressDialog.show(this);
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
		case TYPE_UNIONPAY:
			showToast("抱歉，该支付方式暂未开发完成，请使用其他支付方式支付");
			return;
		}
		
		if(mSelectedPayType == TYPE_ALIPAY_CLIENT) {
			SimpleProgressDialog.show(this);
			YouLianHttpApi.payOrder(Global.getUserToken(getApplicationContext()), mOrder.id, mSelectedPayType, 
					createPaySuccessListener(), createPayErrorListener());
		} else if(mSelectedPayType == TYPE_ALIPAY_WEB) {
			
			String url = YouLianHttpApi.getUrl("service", "younion.order.pay", "user_token", Global.getUserToken(this), "id",
					mOrder.id, "payType", TYPE_ALIPAY_WEB + "");
			
			Intent intent = new Intent(PayActivity.this, AliWapPayActivity.class);
			intent.putExtra("url", url);
			startActivityForResult(intent, REQ_ALIPAY_WAP);
		} else if(mSelectedPayType == TYPE_UCOIN) {
			SimpleProgressDialog.show(this);
			YouLianHttpApi.payOrder(Global.getUserToken(getApplicationContext()), mOrder.id, mSelectedPayType, 
					createPaySuccessListener(), createPayErrorListener());
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
		
		mContainer = (LinearLayout) findViewById(R.id.order_goods_container);
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
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						Order order = Order.from(object.optJSONObject(Constants.key_result));
						if(order != null) {
							mSettleOrder = order;
							if(isRechargeOrder()) {
								mTotalMoneyTextView.setText("总计：" + mSettleOrder.youcoinCount + "元");
							} else {
								mTotalMoneyTextView.setText("总计：" + mSettleOrder.youcoinCount + "U币");
							}
							if(mSettleOrder.orderDetailList != null && mSettleOrder.orderDetailList.size() > 0) {
								mList.addAll(mSettleOrder.orderDetailList);
								fillGoodsList();
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private void fillGoodsList() {
		if(mList != null && mList.size() > 0) {
			mContainer.removeAllViews();
			
			for(int i=0; i<mList.size(); i++) {
				OrderDetail detail = mList.get(i);
				if(detail != null) {
					mContainer.addView(createGoodsItemView(detail));
					if(i < mList.size()-1) {
						mContainer.addView(getLayoutInflater().inflate(R.layout.dashedline, null));
					}
				}
			}
		}
	}
	
	private View createGoodsItemView(OrderDetail orderDetail) {
		View goodsView = getLayoutInflater().inflate(R.layout.item_pay_order, null);
		TextView nameTextView = (TextView) goodsView.findViewById(R.id.pay_tv_goodsname);
		TextView pricetTextView = (TextView) goodsView.findViewById(R.id.pay_tv_price);
		TextView quantityTextView = (TextView) goodsView.findViewById(R.id.pay_tv_amount);
		
		nameTextView.setText("商品名称：" + orderDetail.productName);
		if(isRechargeOrder()) {
			pricetTextView.setText("单价：" + orderDetail.price + "元");
		} else {
			pricetTextView.setText("单价：" + orderDetail.price + "U币");
		}
		quantityTextView.setText("数量：" + orderDetail.quantity + "");
		
		return goodsView;
	}
	
	private Response.ErrorListener createSettleOrderErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	SimpleProgressDialog.dismiss();
            }
        };
    }
	
	private Response.Listener<String> createPaySuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					try {
						JSONObject object = new JSONObject(response);
						handlePay(object);
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
            	SimpleProgressDialog.dismiss();
            	mLogger.e(error.getMessage());
            	showToast("支付失败");
            }
        };
    }
	
	private void handlePay(JSONObject json) {
		if(mSelectedPayType != TYPE_UCOIN && 0 == json.optInt(Constants.key_status)) {
			showToast("支付失败");
			return;
		}
		
		switch(mSelectedPayType) {
		case TYPE_UCOIN:
			int resultCode = json.optInt(Constants.key_status);
			if(resultCode == 0) {
				showUcoinPayFailedDialog();
			} else if(resultCode == 1) {
				showToast("支付成功");
			}
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
	
	private void showUcoinPayFailedDialog() {
		Builder bd = new HuzAlertDialog.Builder(this);
		bd.setTitle("支付失败");
		bd.setMessage("您的U币账户余额不足，是否充值？");
		bd.setPositiveButton("是", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface d, int which) {
				Intent intent = new Intent(PayActivity.this, CoinRechargeActivity.class);
				startActivity(intent);
			}
		});
		bd.setNeutralButton("否", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface d, int which) {
				d.dismiss();
			}
		});
		bd.show();
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQ_ALIPAY_WAP) {
			finish();
		}
	}
}
