package com.example.youlian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class TabMore extends Activity {

	private Button mBtnAccount = null;
	private Button mBtnSound = null;
	private Button mBtnMessage = null;
	private Button mBtnTime = null;
	private Button mBtnFeedback = null;
	private Button mBtnAbout = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_tab_more);
	}

	
}
