package com.example.youlian;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.example.youlian.common.Configure;
import com.example.youlian.common.Constants;


@SuppressWarnings("deprecation")
public class TabHome extends TabActivity implements OnClickListener,
		OnTabChangeListener {
	
	// tab id
	private static final String TAB_PIE = "pie";
	private static final String TAB_TIER = "tier";
	private static final String TAB_PIC = "pic";
	private static final String TAB_SEARCH = "search";
	private static final String TAB_MORE = "more";
	private static final String TAG = "TabHome";

	public static TabHome sTabHome = null;

	private Button mPieButton;
	private Button mTierButton;
	private Button mPicButton;
	private Button mWiGameButton;
	private Button mMoreButton;

	View mTabLayout = null;

	private ImageView mFooterLight;

	private int mScreenWidth;
	private int mFooterLeft;

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(Constants.ACTION_SIGN_FROM_PERSONALCENTER.equals(action)) {
				getTabHost().setCurrentTabByTag(TAB_TIER);
				setSelect(R.id.btn_tier);
			}
		}
	};
	int defaultPosition = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.i(TAG, "TabHome:" + this);
		
		if(savedInstanceState != null) {
			defaultPosition = savedInstanceState.getInt("pos");
		} else {
			Intent intent = getIntent();
			if(intent != null){
				defaultPosition = intent.getIntExtra("postion", 0);
			}
		}
		
		registerBroadcast();
		
		sTabHome = this;
		setContentView(R.layout.activity_tabhome);
		
		Configure.init(this);

		// init views
		initViews();

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("pos", defaultPosition);
	}
	
	private void registerBroadcast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_SIGN_FROM_PERSONALCENTER);
		registerReceiver(mReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	

	private void refreshTopLayout() {
		
	}

	private void initViews() {
		mTabLayout = findViewById(R.id.tab_btn_layout);
		refreshTopLayout();

		TabHost tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);
		tabHost.addTab(tabHost
				.newTabSpec(TAB_PIE)
				.setIndicator("")
				.setContent(
						new Intent(this, TabFirstPage.class)));
		tabHost.addTab(tabHost
				.newTabSpec(TAB_TIER)
				.setIndicator("")
				.setContent(
						new Intent(this, TabSign.class)));
		tabHost.addTab(tabHost
				.newTabSpec(TAB_PIC)
				.setIndicator("")
				.setContent(
						new Intent(this, TabShopCart.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
		tabHost.addTab(tabHost
				.newTabSpec(TAB_SEARCH)
				.setIndicator("")
				.setContent(
						new Intent(this, TabMe.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
		tabHost.addTab(tabHost
				.newTabSpec(TAB_MORE)
				.setIndicator("")
				.setContent(
						new Intent(this, TabMore.class)));

		// set tab buttons
		mPieButton = (Button) findViewById(R.id.btn_pie);
		mPieButton.setOnClickListener(this);

		mTierButton = (Button) findViewById(R.id.btn_tier);
		mTierButton.setOnClickListener(this);

		mPicButton = (Button) findViewById(R.id.btn_pic);
		mPicButton.setOnClickListener(this);

		mWiGameButton = (Button) findViewById(R.id.btn_wigame);
		mWiGameButton.setOnClickListener(this);

		mMoreButton = (Button) findViewById(R.id.btn_more);
		mMoreButton.setOnClickListener(this);

		mFooterLight = (ImageView) findViewById(R.id.footer_light);
		mFooterLeft = mFooterLight.getLeft();
		mScreenWidth = getResources().getDisplayMetrics().widthPixels;
		
		
		switch (defaultPosition) {
		case 0:
			getTabHost().setCurrentTabByTag(TAB_PIE);
			setSelect(R.id.btn_pie);
			break;
		case 1:
			getTabHost().setCurrentTabByTag(TAB_TIER);
			setSelect(R.id.btn_tier);
			break;
		case 2:
			getTabHost().setCurrentTabByTag(TAB_PIC);
			setSelect(R.id.btn_pic);
			break;
		case 3:
			getTabHost().setCurrentTabByTag(TAB_SEARCH);
			setSelect(R.id.btn_wigame);
			break;
		case 4:
			getTabHost().setCurrentTabByTag(TAB_MORE);
			setSelect(R.id.btn_more);
			break;

		default:
			break;
		}
		
		
	}

	public void onClick(View v) {
		
		if(TextUtils.isEmpty(Global.getUserToken(this)) 
				&& (v.getId() == R.id.btn_pic || v.getId() == R.id.btn_wigame)) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivityForResult(intent, v.getId());
			return;
		}
		
		// 1.5 Compatible
		// if target is 1.6, can use new setIndicator method
		switch (v.getId()) {
		case R.id.btn_pie:
			getTabHost().setCurrentTabByTag(TAB_PIE);
			break;
		case R.id.btn_tier:
			getTabHost().setCurrentTabByTag(TAB_TIER);
			break;
		case R.id.btn_pic:
			getTabHost().setCurrentTabByTag(TAB_PIC);
			break;
		case R.id.btn_wigame:
			getTabHost().setCurrentTabByTag(TAB_SEARCH);
			break;
		case R.id.btn_more:
			getTabHost().setCurrentTabByTag(TAB_MORE);
			break;
		}

		setSelect(v.getId());
	}

	private void setSelect(int id) {
		int index = 0;
		if (id == R.id.btn_pie) {
			index = 1;
			mPieButton.setSelected(true);
		} else {
			mPieButton.setSelected(false);
		}

		if (id == R.id.btn_tier) {
			index = 2;
			mTierButton.setSelected(true);
		} else {
			mTierButton.setSelected(false);
		}

		if (id == R.id.btn_pic) {
			index = 3;
			mPicButton.setSelected(true);
		} else {
			mPicButton.setSelected(false);
		}
		
		if (id == R.id.btn_wigame) {
			index = 4;
			mWiGameButton.setSelected(true);
		} else {
			mWiGameButton.setSelected(false);
		}

		if (id == R.id.btn_more) {
			index = 5;
			mMoreButton.setSelected(true);
		} else {
			mMoreButton.setSelected(false);
		}

		int toLeft = mScreenWidth / 5 * (index - 1);

		TranslateAnimation ta = new TranslateAnimation(mFooterLeft, toLeft, 0,
				0);
		ta.setFillAfter(true);
		ta.setDuration(300);
		mFooterLight.startAnimation(ta);
		mFooterLeft = toLeft;
		defaultPosition = index-1;
	}
	
	
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK) {
			switch (requestCode) {
			case R.id.btn_pic:
				getTabHost().setCurrentTabByTag(TAB_PIC);
				break;
			case R.id.btn_wigame:
				getTabHost().setCurrentTabByTag(TAB_SEARCH);
				break;
			}
			
			setSelect(requestCode);
		}

	}
	
}