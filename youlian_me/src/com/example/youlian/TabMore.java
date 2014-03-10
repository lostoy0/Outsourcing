package com.example.youlian;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.more.AppRecommendActivity;
import com.example.youlian.more.FeekBackActivity;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.Utils;
import com.example.youlian.view.SimpleProgressDialog;
import com.example.youlian.view.dialog.HuzAlertDialog;


public class TabMore extends Activity implements OnClickListener{
	
	private static final int REQ_CODE_LOGIN = 0x1000;

	protected static final String TAG = "TabMore";

	private RelativeLayout messagePropelling;
	
//	private RelativeLayout shareSet;
	
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
	public static final int MSG_CLOSE_LOADING = 1;
	
	private Handler mhHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == MSG_CLOSE_LOADING){
				SimpleProgressDialog.dismiss();
			}
		};
	};
	
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
		
		
		
//		shareSet = (RelativeLayout)findViewById(R.id.more_share_set);
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
		resetQuitButtonText();
	}
	
	private void resetQuitButtonText() {
		if(PreferencesUtils.isLogin(getApplicationContext())) {
			more_quit.setText("注销/退出登录");
		} else {
			more_quit.setText("登录");
		}
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
//		shareSet.setOnClickListener(this);
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
//		case R.id.more_share_set:
//			Intent mShareSetActivity = new Intent(this, ShareSetActivity.class);
//			startActivity(mShareSetActivity);
//			break;
		case R.id.more_about:
			Intent aboutIntent = new Intent();
			aboutIntent.setClass(this, WebViewActivity.class);
			aboutIntent.putExtra(WebViewActivity.webType, 1);
			aboutIntent.putExtra(WebViewActivity.TITLE, "关于我们");
			aboutIntent.putExtra(WebViewActivity.BACK_GROUND, R.drawable.bg_title);
			startActivity(aboutIntent);
			break;
		case R.id.more_service:
			Intent tremIntent = new Intent();
			tremIntent.setClass(this, WebViewActivity.class);
			tremIntent.putExtra(WebViewActivity.webType, 2);
			tremIntent.putExtra(WebViewActivity.TITLE, "服务条款");
			tremIntent.putExtra(WebViewActivity.BACK_GROUND, R.drawable.bg_title);
			startActivity(tremIntent);
			break;
//		case R.id.more_help:
//			new HelpView(this,Configure.screenWidth,(int)((int)Configure.screenHeight*0.8));
//			break;
		case R.id.more_feekback:
			Intent mFeekBackActivity = new Intent(this, FeekBackActivity.class);
			startActivity(mFeekBackActivity);
			break;
		case R.id.more_updata:
			SimpleProgressDialog.show(this);
			YouLianHttpApi.checkVersion(createCheckVersionSuccessListener(), createCheckVersionErrorListener());
			break;
		case R.id.more_app_recommend:
			Intent mAppRecommendActivity = new Intent(this, AppRecommendActivity.class);
			startActivity(mAppRecommendActivity);
			break;
		case R.id.more_custom_service_phone:
				Utils.call(getApplicationContext(), "09914542288");
			break;
		case R.id.more_clearcache:
			SimpleProgressDialog.show(this);
			mhHandler.sendEmptyMessageDelayed(MSG_CLOSE_LOADING, 2000);
			break;
		case R.id.more_quit:
			if(PreferencesUtils.isLogin(getApplicationContext())) {
				Global.destroy(this);
//				TabHome.sTabHome.finish();
				resetQuitButtonText();
			} else {
				login();
			}
			break;
		}
		
	}
	
	private void login() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivityForResult(intent, REQ_CODE_LOGIN);
	}
	
	public void notNewVersionShow(Context context) {
		StringBuffer sb = new StringBuffer();
		// sb.append("当前版本:");
		// sb.append(mVersionName);
		sb.append("已经是最新版本");
		Builder bd = new HuzAlertDialog.Builder(context);
		bd.setTitle("提示")
				.setMessage(sb.toString())// 设置内容
				.setPositiveButton("确定",// 设置确定按钮
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}).create();// 创建
		// 显示对话框
		bd.show();
	}
	
	
	private Response.Listener<String> createCheckVersionSuccessListener() {
        return new Response.Listener<String>() {
			@Override
            public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
            	Log.i(TAG, "success guanggao:" + response);
            	try {
					JSONObject o = new JSONObject(response);
					int status = o.optInt("status");
					if(status == 0){
						String msg = o.optString("msg");
						Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
					}else{
						
					}
					notNewVersionShow(TabMore.this);
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
        };
    }
	
    private Response.ErrorListener createCheckVersionErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	SimpleProgressDialog.dismiss();
            	Log.i(TAG, "error");
            }
        };
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQ_CODE_LOGIN && resultCode == RESULT_OK) {
			//登录成功，并成功返回
			if(!TextUtils.isEmpty(Global.getUserToken(this))) {
				resetQuitButtonText();
			}
		}
	}
}
