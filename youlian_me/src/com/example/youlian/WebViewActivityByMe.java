package com.example.youlian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;


public class WebViewActivityByMe extends Activity implements OnClickListener {

	public static String URL = "url";
	private String url;
	protected static final String TAG = "WebViewActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview);
		getData();
		initView();
	}

	private void getData() {
		Intent intent = getIntent();
//		url = "http://www.baidu.com";
		url = intent.getStringExtra(URL);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		
		default:
			break;
		}
	}
	

	
	private ImageButton back;
	private TextView tv_title;
	private WebView webview;
	private void initView() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText("网页跳转");

		
		webview = (WebView)this.findViewById(R.id.webview);
		webview.loadUrl(url);
	}

}
