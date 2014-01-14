package com.example.youlian;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.view.SimpleProgressDialog;


public class WebViewActivity extends Activity implements OnClickListener {

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
	private int type=0;
	private static final int ABOUT = 1;
	private static final int SERVICE_TERM = 2;
	protected static final String TAG = "WebViewActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.more_webview);
		
		initView();
		
		getData();
	}

	private void getData() {
		Intent intent = getIntent();
		type = intent.getIntExtra(webType, 0);
		if (intent.getStringExtra(TITLE) != null
				&& !intent.getStringExtra(TITLE).equals("")) {
			title = intent.getStringExtra(TITLE);
			tv_title.setText(title);
		}
		
		switch (type) {
		case 1:
			YouLianHttpApi.getAbout(createGetAdSuccessListener(), createGetAdErrorListener());
			break;
		case 2:
			YouLianHttpApi.getService(createGetAdSuccessListener(), createGetAdErrorListener());
			break;
		}
		
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
	

	private Response.Listener<String> createGetAdSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	Log.i(TAG, "success:" + response);
            	try {
					JSONObject o = new JSONObject(response);
					int status = o.optInt("status");
					if(status == 0){// failed
						String msg = o.optString("msg");
						Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
					}else{// success
						JSONObject jB = o.optJSONObject("result");
						if(type == SERVICE_TERM){
							String result = jB.optString("service_term_html");
							txt_content.setText(Html.fromHtml(result));
						}else{
							String result = jB.optString("about_html");
							txt_content.setText(Html.fromHtml(result));
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
        };
    }
	
	
	private Response.ErrorListener createGetAdErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Log.i(TAG, "error");
            }
        };
    }
	private ImageButton back;
	private TextView tv_title;
	private void initView() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		txt_content = (TextView) findViewById(R.id.txt_content);

		
	}

}
