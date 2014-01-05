package com.example.youlian;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MembershipActivity extends Activity implements OnClickListener{

    private ImageButton back;
	private TextView tv_title;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        
        initViews();
        
        
    }

	private void initViews() { 
		back = (ImageButton)this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
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
