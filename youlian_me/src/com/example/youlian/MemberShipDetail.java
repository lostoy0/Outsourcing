package com.example.youlian;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MemberShipDetail extends Activity implements OnClickListener {
	protected static final String TAG = "MembershipActivity";
	private ImageButton back;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_membership_detail);
		
		initViews();
	}
	
	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.membership);
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
}
