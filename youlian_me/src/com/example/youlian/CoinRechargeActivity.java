package com.example.youlian;

import java.math.BigDecimal;

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
import com.example.youlian.util.YlLogger;
import com.example.youlian.util.YlUtils;

/**
 * 充值
 * @author raymond
 *
 */
public class CoinRechargeActivity extends BaseActivity {
	private static YlLogger mLogger = YlLogger.getLogger(CoinRechargeActivity.class.getSimpleName());
	
	private EditText mRechargeUcoinCountEditText;
	private TextView mCostMoneyTextView, mExchangeRuleTextView;
	
	private UCoinRule mRule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coin_recharge);
		
		((TextView) findViewById(R.id.tv_title)).setText("U币充值");
		
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mRechargeUcoinCountEditText = (EditText) findViewById(R.id.et_ucoin_count);
		mRechargeUcoinCountEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				resetCostMoney();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				resetCostMoney();
			}
		});
		
		mCostMoneyTextView = (TextView) findViewById(R.id.tv_need_money);
		mExchangeRuleTextView = (TextView) findViewById(R.id.tv_exchange_rule);
		
		mRechargeUcoinCountEditText.setText("0");
		mRechargeUcoinCountEditText.setSelection(1);
		mCostMoneyTextView.setText("0元");
		
		findViewById(R.id.btn_recharge).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		YouLianHttpApi.getCoinRule(createGetRuleSuccessListener(), createGetRuleErrorListener());
	}

	@Override
	protected void onDestroy() {
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
							mExchangeRuleTextView.setText("注：1U币等于人民币" + mRule.youcoinPrice + "元");
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
	
	private void resetCostMoney() {
		if(mRule != null) {
			long rechargeCount = 0;
			if(!TextUtils.isEmpty(mRechargeUcoinCountEditText.getText().toString().trim())) {
				rechargeCount = Long.valueOf(mRechargeUcoinCountEditText.getText().toString().trim());
			}
			double money = YlUtils.round(rechargeCount*mRule.youcoinPrice, 2, BigDecimal.ROUND_HALF_UP);
			mCostMoneyTextView.setText(money + "元");
		}
	}
	
}
