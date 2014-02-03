package com.example.youlian;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.UCoinRule;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;

/**
 * 兑换积分
 * @author raymond
 *
 */
public class CoinExchangeActivity extends BaseActivity {
	private static YlLogger mLogger = YlLogger.getLogger(CoinRechargeActivity.class.getSimpleName());
	
	private EditText mExchangeUcoinCountEditText;
	private TextView mCostUdotTextView, mUdotCountTextView, mExchangeRuleTextView;
	
	private UCoinRule mRule;
	private int mUdotCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coin_exchange);
		
		mUdotCount = getIntent().getIntExtra("udot", 0);
		
		((TextView) findViewById(R.id.tv_title)).setText("兑换积分");
		
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mExchangeUcoinCountEditText = (EditText) findViewById(R.id.et_ucoin_count);
		mExchangeUcoinCountEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				resetCostDot();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				resetCostDot();
			}
		});
		
		mCostUdotTextView = (TextView) findViewById(R.id.tv_need_udot);
		mUdotCountTextView = (TextView) findViewById(R.id.tv_total_dot);
		mExchangeRuleTextView = (TextView) findViewById(R.id.tv_exchange_rule);
		
		mUdotCountTextView.setText("您目前拥有U点：" + mUdotCount + "点");
		mExchangeUcoinCountEditText.setText("0");
		mExchangeUcoinCountEditText.setSelection(1);
		mCostUdotTextView.setText("0U点");
		
		findViewById(R.id.btn_exchange).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String exCountString = mExchangeUcoinCountEditText.getText().toString().trim();
				if(!TextUtils.isEmpty(exCountString)) {
					long exCount = Long.valueOf(exCountString.trim());
					if(exCount*mRule.youdotToYoucoin > mUdotCount) {
						Utils.showToast(CoinExchangeActivity.this, "兑换额度过大，U点数不够");
					} else {
						YouLianHttpApi.exchangeCoin(Global.getUserToken(CoinExchangeActivity.this), exCountString, 
								createExchangeSuccessListener(), createExchangeErrorListener());
					}
				} else {
					Utils.showToast(CoinExchangeActivity.this, "请输入兑换数量");
				}
			}
		});
		
		YouLianHttpApi.getCoinRule(createGetRuleSuccessListener(), createGetRuleErrorListener());
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private Response.Listener<String> createGetRuleSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						if("1".equals(object.opt(Constants.key_status))) {
							UCoinRule rule = UCoinRule.from(object.optJSONObject(Constants.key_result));
							mRule = rule;
							mExchangeRuleTextView.setText("注：1U币等于" + mRule.youdotToYoucoin + "U点");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
	}
	
	private Response.ErrorListener createGetRuleErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
	
	private void resetCostDot() {
		if(mRule != null) {
			Long exchangeUcoin = 0L;
			if(!TextUtils.isEmpty(mExchangeUcoinCountEditText.getText().toString().trim())) {
				exchangeUcoin = Long.valueOf(mExchangeUcoinCountEditText.getText().toString().trim());
			}
			mCostUdotTextView.setText(exchangeUcoin*mRule.youdotToYoucoin + "U点");
		}
	}
	
	private Response.Listener<String> createExchangeSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						if("1".equals(object.opt(Constants.key_status))) {
							Utils.showToast(CoinExchangeActivity.this, "兑换成功");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				finish();
			}
		};
	}
	
	private Response.ErrorListener createExchangeErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	finish();
            }
        };
    }
	
}
