package com.example.youlian;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.common.Configure;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.LoginResult;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;
import com.example.youlian.util.YlUtils;
import com.example.youlian.view.SimpleProgressDialog;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "LoginActivity";

	private YlLogger mLogger = YlLogger.getLogger(this.getClass().getSimpleName());

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

		mAutoLoginButton.setImageResource(R.drawable.remember_psd_b);
		PreferencesUtils.addConfigInfo(this,
				Configure.SESSION_USER_IS_REMPSD, true);
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
		if(TextUtils.isEmpty(mLoginIdEditText.getText().toString()) || TextUtils.isEmpty(mPasswordEditText.getText().toString())) {
			showToast("请输入用户名和密码");
			return;
		}
		
		SimpleProgressDialog.show(this);
		YouLianHttpApi.login(mLoginIdEditText.getText().toString().trim(), mPasswordEditText.getText().toString().trim(),
				createLoginSuccessListener(), createLoginErrorListener());
	}

	private Response.Listener<String> createLoginSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					parseAndSaveLoginInfo(response);
				}
			}
		};
	}
	
	private Response.ErrorListener createLoginErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	SimpleProgressDialog.dismiss();
            	mLogger.e(error.getMessage());
            }
        };
    }
	
	private void parseAndSaveLoginInfo(String response) {
		try {
			JSONObject jsonObject = new JSONObject(response.trim());
			if("1".equals(jsonObject.opt(Constants.key_status))) {
				LoginResult result = LoginResult.from(jsonObject.optJSONObject(Constants.key_result));
				if(result != null) {//登录成功，保存登录信息
					PreferencesUtils.saveSessionUser(LoginActivity.this, result);
					SimpleProgressDialog.show(LoginActivity.this);
					YouLianHttpApi.bindDeviceId(Global.getUserToken(getApplicationContext()), 
							getDeviceId(), createSuccessListener(), createErrorListener());
				}
			} else {
				Utils.showToast(LoginActivity.this, "登录失败");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getDeviceId(){
		final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	    String deviceId =  tm.getDeviceId();
	    Log.i(TAG, "deviceId :" + deviceId);
	    return YlUtils.md5(deviceId);
	}
	
	
	private Response.Listener<String> createSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				Log.i(TAG, "response :" + response);
				showToast("登录成功");
				setResult(RESULT_OK);
				finish();
			}
		};
	}
	
	private Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	SimpleProgressDialog.dismiss();
            	Log.i(TAG, "response :" + error);
            	setResult(RESULT_OK);
				finish();
            }
        };
    }
}
