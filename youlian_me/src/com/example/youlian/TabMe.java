package com.example.youlian;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class TabMe extends Activity {

	private Button mBtnSearch = null;
	
	private TextView mSearchTextView = null;
	
	private Button mBtnNearby = null;
	private Button mBtnNewest = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		

		setContentView(R.layout.activity_tab_search);
	}

	
}
