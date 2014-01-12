package com.example.youlian;

import java.util.ArrayList;

import com.example.youlian.more.ShareSetActivity;
import com.example.youlian.view.MySlipSwitch;
import com.example.youlian.view.MySlipSwitch.OnSwitchListener;


import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TabMore extends Activity implements OnClickListener{

	
	
	private RelativeLayout messagePropelling;
	
	private RelativeLayout shareSet;
	
	private RelativeLayout about;
	
	private RelativeLayout service;
	
	private RelativeLayout help;
	
	private RelativeLayout feekback;
	
	private RelativeLayout appUpdata;
	
	private RelativeLayout app_recommend;
	
	private Button customServicePhone;
	
	private RelativeLayout more_clearcache;

	private RelativeLayout more_attention;

	private RelativeLayout more_recommend_youlian;

	private Button more_quit;

	private TextView tv_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_more);
		initView();
		initListener();
		
	}
	/**
	 * 初始化界面
	 */
	private void initView(){
		this.findViewById(R.id.back).setVisibility(View.GONE);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.category_more);
		
		
		
		shareSet = (RelativeLayout)findViewById(R.id.more_share_set);
		messagePropelling = (RelativeLayout)findViewById(R.id.more_message_propelling);
		more_clearcache = (RelativeLayout)findViewById(R.id.more_clearcache);
		appUpdata = (RelativeLayout)findViewById(R.id.more_updata);
		more_attention = (RelativeLayout)findViewById(R.id.more_attention);
		more_recommend_youlian = (RelativeLayout)findViewById(R.id.more_recommend_youlian);
		feekback = (RelativeLayout)findViewById(R.id.more_feekback);
		app_recommend = (RelativeLayout)findViewById(R.id.more_app_recommend);
		about = (RelativeLayout)findViewById(R.id.more_about);
		service = (RelativeLayout)findViewById(R.id.more_service);
		help = (RelativeLayout)findViewById(R.id.more_help);
		customServicePhone = (Button)findViewById(R.id.more_custom_service_phone);
		more_quit = (Button)findViewById(R.id.more_quit);
	}
	
	/**
	 * 事件监听
	 */
	private void initListener(){
		more_quit.setOnClickListener(this);
		more_recommend_youlian.setOnClickListener(this);
		more_attention.setOnClickListener(this);
		more_clearcache.setOnClickListener(this);
		messagePropelling.setOnClickListener(this);
		shareSet.setOnClickListener(this);
		about.setOnClickListener(this);
		service.setOnClickListener(this);
		help.setOnClickListener(this);
		feekback.setOnClickListener(this);
		appUpdata.setOnClickListener(this);
		app_recommend.setOnClickListener(this);
		customServicePhone.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.more_message_propelling:
			break;
		case R.id.more_share_set:
			Intent mShareSetActivity = new Intent(this, ShareSetActivity.class);
			startActivity(mShareSetActivity);
			break;
//		case R.id.more_about:
//			Intent aboutIntent = new Intent();
//			aboutIntent.setClass(this, WebViewActivity.class);
//			aboutIntent.putExtra(WebViewActivity.webType, 1);
//			aboutIntent.putExtra(WebViewActivity.TITLE, "关于我们");
//			aboutIntent.putExtra(WebViewActivity.BACK_GROUND, R.drawable.title_header_bg);
//			startActivity(aboutIntent);
//			break;
//		case R.id.more_service:
//			Intent tremIntent = new Intent();
//			tremIntent.setClass(this, WebViewActivity.class);
//			tremIntent.putExtra(WebViewActivity.webType, 2);
//			tremIntent.putExtra(WebViewActivity.TITLE, "服务条款");
//			tremIntent.putExtra(WebViewActivity.BACK_GROUND, R.drawable.title_header_bg);
//			startActivity(tremIntent);
//			break;
//		case R.id.more_help:
//			new HelpView(this,Configure.screenWidth,(int)((int)Configure.screenHeight*0.8));
//			break;
//		case R.id.more_feekback:
//			Intent mFeekBackActivity = new Intent(this, FeekBackActivity.class);
//			startActivity(mFeekBackActivity);
//			break;
//		case R.id.more_updata:
//			VersionManager vs = new VersionManager();
//			vs.checkAPK(this, true, this);
//			break;
//		case R.id.more_app_recommend:
//			Intent mAppRecommendActivity = new Intent(this, AppRecommendActivity.class);
//			startActivity(mAppRecommendActivity);
//			break;
//		case R.id.more_custom_service_phone:
//			break;
//		case R.id.more_clearcache:
//			break;
//		case R.id.more_quit:
//			break;
		}
		
	}
}
