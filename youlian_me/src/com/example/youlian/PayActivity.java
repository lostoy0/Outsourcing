package com.example.youlian;

import org.apache.http.impl.cookie.BestMatchSpec;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 支付界面
 * @author raymond
 *
 */
public class PayActivity extends BaseActivity implements OnClickListener {
	
	private static final int YIPAY = 1;
	private static final int UNIONPAY = 2;
	private static final int ALIPAY_CLIENT = 3;
	private static final int ALIPAY = 4;
	private static final int UCOIN = 5;
	
	private TextView mShopNameTextView, mGoodsNameTextView, mPriceTextView, mAmountTextView, mTotalMoneyTextView, mCheckAlipayClienTextView;
	private ImageButton mSelectYiPayButton, mSelectUnionButton, mSelectAlipayClientButton, mSelectAlipayButton, mSelectUcoinButton;
	private Button mPayButton;
	
	private int mSelected = YIPAY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		
		initViews();
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
			
			break;
		}
	}

	private void initViews() {
		((TextView) findViewById(R.id.tv_title)).setText("结算");
		findViewById(R.id.back).setOnClickListener(this);
		
		mShopNameTextView = (TextView) findViewById(R.id.pay_tv_shopname);
		mGoodsNameTextView = (TextView) findViewById(R.id.pay_tv_goodsname);
		mPriceTextView = (TextView) findViewById(R.id.pay_tv_price);
		mAmountTextView = (TextView) findViewById(R.id.pay_tv_amount);
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

}
