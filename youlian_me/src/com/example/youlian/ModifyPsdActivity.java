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
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;

public class ModifyPsdActivity extends BaseActivity implements OnClickListener {
	private YlLogger mLogger = YlLogger.getLogger(this.getClass().getSimpleName());
	
	private final static int MODIFY_PASSWORD = 1;
	
	private ImageButton mBackButton;
	private Button mSubmitButton;

	private EditText mOldPswdEditText, mNewPswdEditText, mNewPswdAgainEditText;

	private String mOldPswd, mNewPswd, mNewPswdAgain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_modify_psd);

		initView();
		initListener();

	}

	private void initView() {
		mBackButton = (ImageButton) findViewById(R.id.back);
		mSubmitButton = (Button) findViewById(R.id.ok);
		mOldPswdEditText = (EditText) findViewById(R.id.old_psd);
		mNewPswdEditText = (EditText) findViewById(R.id.new_psd);
		mNewPswdAgainEditText = (EditText) findViewById(R.id.agin_psd);
	}

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
			mOldPswd = mOldPswdEditText.getText().toString();
			mNewPswd = mNewPswdEditText.getText().toString();
			mNewPswdAgain = mNewPswdAgainEditText.getText().toString();
			
			if (TextUtils.isEmpty(mOldPswd)) {
				Utils.showToast(this, "请输入旧密码");
				return;
			}
			
			if (TextUtils.isEmpty(mNewPswd)) {
				Utils.showToast(this, "请输入新密码");
				return;
			}
			
			if (TextUtils.isEmpty(mNewPswdAgain)) {
				Utils.showToast(this, "请再次输入密码");
				return;
			}
			
			if (!mNewPswd.equals(mNewPswdAgain)) {
				Utils.showToast(this, "新密码前后不一致");
			} else {
				modifyPassword(mOldPswd, mNewPswd);
			}
			break;
		}
	}

	private void modifyPassword(String old_password, String new_password) {
		YouLianHttpApi.modifyPassword(PreferencesUtils.getUserToken(this), old_password, new_password, 
				createModifySuccessListener(), createModifyErrorListener());
	}
	
	private Response.Listener<String> createModifySuccessListener() {
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
	
	private Response.ErrorListener createModifyErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
}
