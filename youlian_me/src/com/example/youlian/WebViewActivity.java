package com.example.youlian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youlian.view.SimpleProgressDialog;


public class WebViewActivity extends Activity {

	private TextView txt_title,txt_content;
//	private WebView web_view;
	public static String URL = "url";
	public static String TITLE = "title";
	public static String BACK_GROUND = "background";
	public static String webType="type";//1 ��������  2 ��������
	private String url;
	private String title;
	private int background;
	private ProgressBar progress_horizontal;
	private static int type=0;
	private static final int ABOUT = 1;
	private static final int SERVICE_TERM = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_webview);
		getData();
		initView();
	}

	private void getData() {
		Intent intent = getIntent();
		type = intent.getIntExtra(webType, 0);
		if (intent.getStringExtra(TITLE) != null
				&& !intent.getStringExtra(TITLE).equals("")) {
			title = intent.getStringExtra(TITLE);
		}
		background = intent.getIntExtra(BACK_GROUND, 0);
		
	}

	private void initView() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_content = (TextView) findViewById(R.id.txt_content);
		if (title != null && !title.equals("")) {
			txt_title.setText(title);
			if (background != 0) {
				txt_title.setBackgroundResource(background);
			} else {
				txt_title.setBackgroundColor(R.drawable.bg_title);
			}
		} else {
			txt_title.setVisibility(View.GONE);
		}

		
	}

	private void loadWeb() {
//
//		web_view.getSettings().setSupportZoom(true);
//		web_view.getSettings().setBuiltInZoomControls(true);
//		web_view.getSettings().setJavaScriptEnabled(true);
//		web_view.getSettings().setDefaultTextEncodingName("utf-8");

//		web_view.setWebChromeClient(new WebChromeClient() {
//			@Override
//			public void onProgressChanged(WebView view, int newProgress) {
//				if(newProgress<100)
//				{
//					progress_horizontal.setVisibility(View.VISIBLE);
//					progress_horizontal.setProgress(newProgress);
//				}
//				else
//				{
//					progress_horizontal.setVisibility(View.GONE);
//				}
//			}
//			
//		});
//		SimpleProgressDialog.dismiss();
//		if (url != null && !url.equals("")) {
//			web_view.loadUrl(url);
//		}
	}

}
