package com.example.youlian;

import java.util.List;

import org.json.JSONException;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.mode.Ad;
import com.example.youlian.view.ActLeft;
import com.example.youlian.view.ActRight;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AllSellerActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AllSellerActivity";
	private ImageButton back;
	private TextView tv_title;
	private LinearLayout container_left;
	private LinearLayout container_right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_seller_activities);
		
		initViews();
		
		YouLianHttpApi.getAllactivitys(createGetAdSuccessListener(), createGetAdErrorListener());
		
	}
	
	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.act);
		
		
		container_left = (LinearLayout)this.findViewById(R.id.container_left);
		container_right = (LinearLayout)this.findViewById(R.id.container_right);
	}
	
	

	private Response.Listener<String> createGetAdSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	Log.i(TAG, "success:" + response);
            	
            	addViews();
//            	try {
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
            }
        };
    }
	


    protected void addViews() {
    	for(int i=0; i<5; i++){
    		ActLeft left = new ActLeft(getApplicationContext());
    		ActRight right = new ActRight(getApplicationContext());
    		container_left.addView(left);
    		container_right.addView(right);
    	}
	}

	private Response.ErrorListener createGetAdErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Log.i(TAG, "error");
            }
        };
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
