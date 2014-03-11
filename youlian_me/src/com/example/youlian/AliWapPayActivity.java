package com.example.youlian;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

/**
 * 
 * 支付宝手机网页支付
 * @author Raymond
 * @date 2014-3-11 下午8:33:54
 */
public class AliWapPayActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String url = getIntent().getStringExtra("url");
		if(TextUtils.isEmpty(url)) finish();
		
		WebView webView = new WebView(this);
		setContentView(webView);
		
		webView.loadUrl(url);
	}
	
}
