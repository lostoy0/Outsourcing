package com.example.youlian;

import com.example.youlian.util.YlLogger;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * 支付宝手机网页支付
 * @author Raymond
 * @date 2014-3-11 下午8:33:54
 */
public class AliWapPayActivity extends BaseActivity {
	private YlLogger mLogger = YlLogger.getLogger(AliWapPayActivity.class.getSimpleName());
	
	private WebView mWebView;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_alipay_wap);
		
		String url = getIntent().getStringExtra("url");
		if(TextUtils.isEmpty(url)) finish();
		
		mWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		mWebView.setWebChromeClient(new PayWebChromeClient());
		mWebView.setWebViewClient(new PayWebViewClient());
		mWebView.loadUrl(url);
	}
	
	class PayWebChromeClient extends WebChromeClient {

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			AliWapPayActivity.this.setProgress(newProgress * 1000);
		}
		
	}
	
	class PayWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if(!TextUtils.isEmpty(url) && (url.contains("test.younion.cn") || url.startsWith("www.younion.com.cn"))) {
				setResult(RESULT_OK);
				finish();
			}
			
			view.loadUrl(url);
			return true;
		}
		
	}
	
}
