package com.example.youlian;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.app.MyVolley;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.MyInfo;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.YlLogger;
import com.example.youlian.view.SimpleProgressDialog;

public class TabMe extends BaseActivity implements OnClickListener {
	private YlLogger mLogger = YlLogger.getLogger(this.getClass().getSimpleName());
	
	private static final int REQ_CODE_LOGIN = 0x1000;
	
	private ImageButton mSettingButton;
	private ImageView mIconImageView;
	private TextView mUsernameTextView, mUcoinTextView, mUdotTextView;
	private ImageButton mMsgButton;
	private Button mSignButton, mExchangeScoreButton, mRechargeButton;
	private TextView mVipCountTextView, mVipHasBalanceCountTextView;
	private TextView mCouponCountTextView, mCouponWillExpCountTextView;
	private TextView mOrderCountTextView, mOrderUnpaidCountTextView;
	private TextView mFavouriteCountTextView;
	
	private MyInfo mInfo = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tab_individual);
		
		initViews();
		
		if(!PreferencesUtils.isLogin(this)) {
			login();
		} else {
			loadData();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQ_CODE_LOGIN && resultCode == RESULT_OK) {
			//登录成功，并成功返回
			if(!TextUtils.isEmpty(Global.getUserToken(this))) {
				//usertoken is valid, then load data
				loadData();
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch(v.getId()) {
		case R.id.my_ib_setting:
			intent = new Intent(this, ModifyUserInfoActivity.class);
			startActivity(intent);
			break;
			
		case R.id.my_ib_msg:
			
			break;
			
		case R.id.my_ib_sign:
			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction(Constants.ACTION_SIGN_FROM_PERSONALCENTER);
			sendBroadcast(broadcastIntent);
			break;
			
		case R.id.my_ib_exchange_score:
			intent = new Intent(this, CoinExchangeActivity.class);
			intent.putExtra("udot", mInfo==null?0:mInfo.youdot);
			startActivity(intent);
			break;
			
		case R.id.my_ib_recharge:
			intent = new Intent(this, CoinRechargeActivity.class);
			startActivity(intent);
			break;
			
		case R.id.my_vip_panel:
		case R.id.my_tv_vip:
			intent = new Intent(this, CardListActivity.class);
			startActivity(intent);
			break;
			
		case R.id.my_coupon_panel:
		case R.id.my_tv_coupon:
			intent = new Intent(this, CouponListActivity.class);
			startActivity(intent);
			break;
			
		case R.id.my_order_panel:
		case R.id.my_tv_order:
			intent = new Intent(this, MyOrderActivity.class);
			startActivity(intent);
			break;
			
		case R.id.my_favourite_panel:
		case R.id.my_tv_favourite:
			intent = new Intent(this, FavouriteListActivity.class);
			startActivity(intent);
			break;
		}
	}

	private void initViews() {
		mSettingButton = (ImageButton) findViewById(R.id.my_ib_setting);
		mSettingButton.setOnClickListener(this);
		
		mSignButton = (Button) findViewById(R.id.my_ib_sign);
		mSignButton.setOnClickListener(this);
		mExchangeScoreButton = (Button) findViewById(R.id.my_ib_exchange_score);
		mExchangeScoreButton.setOnClickListener(this);
		mRechargeButton = (Button) findViewById(R.id.my_ib_recharge);
		mRechargeButton.setOnClickListener(this);
		
		mMsgButton = (ImageButton) findViewById(R.id.my_ib_msg);
		mMsgButton.setOnClickListener(this);
		
		mIconImageView = (ImageView) findViewById(R.id.my_iv_icon);
		
		mUsernameTextView = (TextView) findViewById(R.id.my_tv_username);
		mUcoinTextView = (TextView) findViewById(R.id.my_tv_ucoin);
		mUdotTextView = (TextView) findViewById(R.id.my_tv_udot);
		
		mVipCountTextView = (TextView) findViewById(R.id.my_tv_vip_num);
		mVipHasBalanceCountTextView = (TextView) findViewById(R.id.my_tv_vip_info);
		
		mCouponCountTextView = (TextView) findViewById(R.id.my_tv_coupon_num);
		mCouponWillExpCountTextView = (TextView) findViewById(R.id.my_tv_coupon_info);
		
		mOrderCountTextView = (TextView) findViewById(R.id.my_tv_order_num);
		mOrderUnpaidCountTextView = (TextView) findViewById(R.id.my_tv_order_info);
		
		mFavouriteCountTextView = (TextView) findViewById(R.id.my_tv_favourite_num);
		
		findViewById(R.id.my_vip_panel).setOnClickListener(this);
		findViewById(R.id.my_tv_vip).setOnClickListener(this);
		findViewById(R.id.my_coupon_panel).setOnClickListener(this);
		findViewById(R.id.my_tv_coupon).setOnClickListener(this);
		findViewById(R.id.my_favourite_panel).setOnClickListener(this);
		findViewById(R.id.my_tv_favourite).setOnClickListener(this);
		findViewById(R.id.my_order_panel).setOnClickListener(this);
		findViewById(R.id.my_tv_order).setOnClickListener(this);
	}
	
	private void login() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivityForResult(intent, REQ_CODE_LOGIN);
	}

	private void loadData() {
		SimpleProgressDialog.show(this);
		YouLianHttpApi.getMyInfo(Global.getUserToken(this), createGetMyInfoSuccessListener(), createGetMyInfoErrorListener());
	}

	private Response.Listener<String> createGetMyInfoSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						parseDataAndShow(response);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private void parseDataAndShow(String response) throws JSONException {
		JSONObject object = new JSONObject(response);
		if("1".equals(object.opt(Constants.key_status))) {
			mInfo = MyInfo.from(object.optJSONObject(Constants.key_result));
			if(mInfo != null) {
				setData();
			}
		} 
	}
	
	private void setData() {
		if(mInfo != null) {
			MyVolley.getImageLoader().get(mInfo.logo, ImageLoader.getImageListener(mIconImageView, 0, 0));
			
			if(!TextUtils.isEmpty(mInfo.userName)) {
				mUsernameTextView.setText(mInfo.userName);
			} else {
				mUsernameTextView.setText(mInfo.loginId);
			}
			
			mUcoinTextView.setText("U币: " + mInfo.youcoin);
			mUdotTextView.setText("U点: " + mInfo.youdot);
			
			mVipCountTextView.setText(mInfo.cardCount + "张");
			mVipHasBalanceCountTextView.setText(mInfo.hasBlanceCardCount + "张有余额");
			
			mCouponCountTextView.setText(mInfo.favourableCount + "张");
			mCouponWillExpCountTextView.setText(mInfo.willExpFavourableCount + "张即将过期");
			
			mOrderCountTextView.setText(mInfo.orderCount + "个");
			mOrderUnpaidCountTextView.setText(mInfo.unpaidCount + "个未支付");
			
			mFavouriteCountTextView.setText(mInfo.favouritesCount + "个");
		}
	}

	private Response.ErrorListener createGetMyInfoErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	SimpleProgressDialog.dismiss();
            	mLogger.e(error.getMessage());
            }
        };
    }

}
