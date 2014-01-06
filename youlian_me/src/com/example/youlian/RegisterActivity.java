package com.example.youlian;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author rome
 * @proName youlian
 * @version 1.0
 * @Data 2012-9-26 上午11:02:32
 *
   <b>Comment...</b>
 */
@SuppressWarnings("unused")
public class RegisterActivity extends Activity implements OnClickListener{
	
	 private LinearLayout lay;
	 private ImageButton backBtn;
	 private static final int REGISTER_ID = 1;
	 private EditText nameEdit,psdEdit;
	 private Button submitBtn;
	 private String loginType;
	 private Context mContext;

	 private String name;
	 private String psd;
	 private TextView register_pro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_register);
		mContext = this;
		loginType = getIntent().getStringExtra("loginType");
		initView();
		initListener();
		
	}
	/**
	 * 初始化界面
	 */
	private void initView(){
		lay = (LinearLayout)findViewById(R.id.lay);
		backBtn = (ImageButton)findViewById(R.id.back);
		submitBtn = (Button)findViewById(R.id.submit);
		nameEdit= (EditText)findViewById(R.id.login_id);
		psdEdit = (EditText)findViewById(R.id.password);
		register_pro = (TextView)findViewById(R.id.register_pro);
		register_pro.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
//		TextPaint tp = register_pro.getPaint();
//        tp.setFakeBoldText(true); 
	}
	/**
	 * 事件监听
	 */
	private void initListener(){
		backBtn.setOnClickListener(this);
		submitBtn.setOnClickListener(this);
		register_pro.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();
			
			break;
		case R.id.register_pro:
//			Intent tremIntent = new Intent();
//			tremIntent.setClass(this, WebViewActivity.class);
//			tremIntent.putExtra(WebViewActivity.webType, 2);
//			tremIntent.putExtra(WebViewActivity.TITLE, "服务条款");
//			tremIntent.putExtra(WebViewActivity.BACK_GROUND, R.drawable.title_header_bg);
//			startActivity(tremIntent);
			
			break;
		case R.id.submit:
//			
			break;

		default:
			break;
		}
	}
}
