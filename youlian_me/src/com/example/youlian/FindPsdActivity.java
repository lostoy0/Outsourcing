package com.example.youlian;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.util.YlLogger;
import com.example.youlian.util.YlUtils;
import com.example.youlian.view.SimpleProgressDialog;

public class FindPsdActivity extends BaseActivity implements OnClickListener {
	private YlLogger mLogger = YlLogger.getLogger(this.getClass().getSimpleName());
	
	private ImageButton mBackButton;
	private Button mSubmitButton;
	private EditText mLoginIdEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_find_psd);
		
		initView();
		initListener();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		mBackButton = (ImageButton) findViewById(R.id.back);
		mSubmitButton = (Button) findViewById(R.id.ok);
		mLoginIdEditText = (EditText) findViewById(R.id.login_id);
	}

	/**
	 * 事件监听
	 */
	private void initListener() {
		mBackButton.setOnClickListener(this);
		mSubmitButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();

			break;
		case R.id.ok:
			seekPassword();
			break;

		}
	}

	private void seekPassword() {
		if(!TextUtils.isEmpty(mLoginIdEditText.getText().toString()) && YlUtils.isMobileValid(mLoginIdEditText.getText().toString().trim())) {
			SimpleProgressDialog.show(this);
			YouLianHttpApi.seekPassword(mLoginIdEditText.getText().toString().trim(), createSeekSuccessListener(), createSeekErrorListener());
		} else {
			showToast("请输入有效手机号码");
		}
	}
	
	private Response.Listener<String> createSeekSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
				}
			}
		};
	}
	
	private Response.ErrorListener createSeekErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	SimpleProgressDialog.dismiss();
            	mLogger.e(error.getMessage());
            }
        };
    }
}
