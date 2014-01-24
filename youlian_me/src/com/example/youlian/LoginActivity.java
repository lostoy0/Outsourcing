package com.example.youlian;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.common.Configure;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.YlLogger;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private YlLogger mLogger = YlLogger.getLogger(this.getClass().getSimpleName());

	private static final int LOGIN_ID = 1;
	private static final int ADD_DE = 2;

	private ImageButton mBackButton, mAutoLoginButton;;
	private EditText mLoginIdEditText, mPasswordEditText;
	private Button mForgetButton, mRegisterButton, mLoginButton;
	private String mLoginType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_login);
		
		mLoginType = getIntent().getStringExtra("loginType");
		
		initView();
		initListener();

	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		mBackButton = (ImageButton) findViewById(R.id.back);
		mAutoLoginButton = (ImageButton) findViewById(R.id.rem_psd);
		mForgetButton = (Button) findViewById(R.id.forget_psd);
		mRegisterButton = (Button) findViewById(R.id.register);
		mLoginButton = (Button) findViewById(R.id.login);
		mLoginIdEditText = (EditText) findViewById(R.id.login_id);
		mPasswordEditText = (EditText) findViewById(R.id.password);

		if (PreferencesUtils.getBooleanByKey(this,
				Configure.SESSION_USER_IS_REMPSD)) {
			mAutoLoginButton.setImageResource(R.drawable.remember_psd_b);
		}
	}

	/**
	 * 事件监听
	 */
	private void initListener() {
		mBackButton.setOnClickListener(this);
		mForgetButton.setOnClickListener(this);
		mRegisterButton.setOnClickListener(this);
		mLoginButton.setOnClickListener(this);
		mAutoLoginButton.setOnClickListener(this);
	}

//	private void loginSuccend(LoginResult user) {
//		PreferencesUtils.saveSessionUser(this, user);
//		if (Configure.userType.equals(user.getType())) { // 普通用户
//			Intent intent = new Intent(this, MoreActivity.class);
//			intent.putExtra("user_type", user.getType());
//			intent.putExtra("user_name", user.getUser_name());
//			intent.putExtra("user_integral", user.getUser_integral());
//			setResult(RESULT_OK, intent);
//		} else {
//			if (Configure.More.equals(loginType)) {
//				Intent intent = new Intent(this, YoulianActivity.class);
//				intent.putExtra("user_type", user.getType());
//				intent.putExtra("user_name", user.getUser_name());
//				intent.putExtra("user_integral", user.getUser_integral());
//				/* 将数据打包到aintent Bundle 的过程略 */
//				setResult(Configure.MORE_LOGIN, intent);
//			} else if (Configure.MyKabao.equals(loginType)) {
//				Intent intent = new Intent(this, YoulianActivity.class);
//				intent.putExtra("user_type", user.getType());
//				/* 将数据打包到aintent Bundle 的过程略 */
//				setResult(RESULT_OK, intent);
//
//			} else if (Configure.CardManger.equals(loginType)) {
//				Intent intent = new Intent(this, CardDetailedActivity.class);
//				intent.putExtra("user_type", user.getType());
//				/* 将数据打包到aintent Bundle 的过程略 */
//				setResult(RESULT_OK, intent);
//
//			}
//		}
//		finish();
//	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();

			break;
		case R.id.forget_psd:
			Intent mFindPsdActivity = new Intent(this, FindPsdActivity.class);
			startActivity(mFindPsdActivity);
			break;
			
		case R.id.register:
			Intent mRegisterActivity = new Intent(this, RegisterActivity.class);
			mRegisterActivity.putExtra("loginType", mLoginType);
			startActivity(mRegisterActivity);
			finish();
			break;
			
		case R.id.login:
			login();
			break;
			
		case R.id.rem_psd:
			if (PreferencesUtils.getBooleanByKey(this,
					Configure.SESSION_USER_IS_REMPSD)) {
				mAutoLoginButton.setImageResource(R.drawable.remember_psd_a);
				PreferencesUtils.addConfigInfo(this,
						Configure.SESSION_USER_IS_REMPSD, false);
			} else {
				mAutoLoginButton.setImageResource(R.drawable.remember_psd_b);
				PreferencesUtils.addConfigInfo(this,
						Configure.SESSION_USER_IS_REMPSD, true);
			}

			break;
		}
	}
	
	private void login() {
		YouLianHttpApi.login(mLoginIdEditText.getText().toString().trim(), mPasswordEditText.getText().toString().trim(),
				createLoginSuccessListener(), createLoginErrorListener());
	}

	private Response.Listener<String> createLoginSuccessListener() {
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
	
	private Response.ErrorListener createLoginErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
}
