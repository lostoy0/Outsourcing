package com.example.youlian;

import com.example.youlian.util.PreferencesUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class TabMe extends Activity {
	private static final int REQ_CODE_LOGIN = 0x1000;

	private Button mBtnSearch = null;
	
	private TextView mSearchTextView = null;
	
	private Button mBtnNearby = null;
	private Button mBtnNewest = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		

		setContentView(R.layout.activity_tab_search);
		
		if(!PreferencesUtils.isLogin(this)) {
			login();
		}
	}

	private void login() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivityForResult(intent, REQ_CODE_LOGIN);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQ_CODE_LOGIN && resultCode == RESULT_OK) {
			//登录成功，并成功返回
			
		}
	}

	
}
