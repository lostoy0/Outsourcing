package com.example.youlian;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;
import com.example.youlian.util.YlUtils;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	private YlLogger mLogger = YlLogger.getLogger(this.getClass().getSimpleName());

	private static final int REGISTER_ID = 1;
	
	private ImageButton mBackButton;
	private EditText mLoginIdEditText, mPasswordEditText, mVerifyCodEditText;
	private Button mSubmitButton, mGetVerifyCodeButton;
	private TextView mRegisterAgreement;

	private String mLoginId;
	private String mPassword;
	private String mVerifyCode;
	
	private YlCountDownTimer mCountDownTimer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_register);

		initView();
		initListener();

	}

	@Override
	protected void onDestroy() {
		if(mCountDownTimer != null) mCountDownTimer.cancel();
		super.onDestroy();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		mBackButton = (ImageButton) findViewById(R.id.back);
		mSubmitButton = (Button) findViewById(R.id.submit);
		mLoginIdEditText = (EditText) findViewById(R.id.login_id);
		mPasswordEditText = (EditText) findViewById(R.id.password);
		mRegisterAgreement = (TextView) findViewById(R.id.register_pro);
		mRegisterAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
		mVerifyCodEditText = (EditText) findViewById(R.id.et_verifycode);
		mGetVerifyCodeButton = (Button) findViewById(R.id.btn_get_verifycode);
	}

	/**
	 * 事件监听
	 */
	private void initListener() {
		mBackButton.setOnClickListener(this);
		mSubmitButton.setOnClickListener(this);
		mRegisterAgreement.setOnClickListener(this);
		mGetVerifyCodeButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();

			break;
		case R.id.register_pro:
			Intent tremIntent = new Intent();
			tremIntent.setClass(this, WebViewActivity.class);
			tremIntent.putExtra(WebViewActivity.webType, 2);
			tremIntent.putExtra(WebViewActivity.TITLE, "服务条款");
			tremIntent.putExtra(WebViewActivity.BACK_GROUND, R.drawable.bg_title);
			startActivity(tremIntent);

			break;
		case R.id.submit:

			mLoginId = mLoginIdEditText.getText().toString().trim();
			mPassword = mPasswordEditText.getText().toString().trim();
			mVerifyCode = mVerifyCodEditText.getText().toString().trim();
			
			if (!TextUtils.isEmpty(mLoginId) && !TextUtils.isEmpty(mPassword) && !TextUtils.isEmpty(mVerifyCode)) {
				if (mLoginId.length() < 11) {
					String totalStr = "手机号不能小于11位";
					Utils.showToast(this, totalStr);
				} else {
					if (mLoginId.length() > 11) {
						String totalStr = "手机号不能大于11位";
						Utils.showToast(this, totalStr);
					} else {
						register();
					}
				}
			} else {
				String totalStr = "请把信息填写完整后提交";
				Utils.showToast(this, totalStr);
			}
			break;
			
		case R.id.btn_get_verifycode:
			getVerifyCode();
			break;

		}
	}
	
	private void getVerifyCode() {
		mLoginId = mLoginIdEditText.getText().toString().trim();
		if(TextUtils.isEmpty(mLoginId) || !YlUtils.isMobileValid(mLoginId)) {
			Utils.showToast(this, "请输入有效手机号码");
			return;
		}
		
		YouLianHttpApi.getVerifyCode(mLoginId, "0",  createVerifyCodeSuccessListener(), createVerifyCodeErrorListener());
	}
	
	private void register() {
		YouLianHttpApi.register(mLoginId, mPassword, mVerifyCode, createRegisterSuccessListener(), createRegisterErrorListener());
	}
	
	private Response.Listener<String> createVerifyCodeSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
				}
				
				mGetVerifyCodeButton.setEnabled(false);
				if(mCountDownTimer == null) mCountDownTimer = new YlCountDownTimer(30000, 1000);
				mCountDownTimer.start();
			}
		};
	}
	
	private Response.ErrorListener createVerifyCodeErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
	
	private Response.Listener<String> createRegisterSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
				}
			}
		};
	}
	
	private Response.ErrorListener createRegisterErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
	
	class YlCountDownTimer extends CountDownTimer {

		public YlCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mGetVerifyCodeButton.setText("剩余" + millisUntilFinished/1000 + "秒");
		}

		@Override
		public void onFinish() {
			mGetVerifyCodeButton.setText("获取验证码");
			mGetVerifyCodeButton.setEnabled(true);
		}
		
	}
}
