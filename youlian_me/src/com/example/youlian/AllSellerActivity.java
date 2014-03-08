package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.mode.Act;
import com.example.youlian.mode.Ad;
import com.example.youlian.mode.Card;
import com.example.youlian.view.ActLeft;
import com.example.youlian.view.ActRight;
import com.example.youlian.view.SimpleProgressDialog;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

public class AllSellerActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AllSellerActivity";
	private ImageButton back;
	private TextView tv_title;
	private LinearLayout container_left;
	private LinearLayout container_right;

	public List<Act> acts = new ArrayList<Act>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);
		
		initViews();
		SimpleProgressDialog.show(this);
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
            	SimpleProgressDialog.dismiss();
            	
            	if (response != null) {
				try {
					JSONObject o = new JSONObject(response);
					int status = o.optInt("status");
					if(status == 1){
						JSONArray array = o.optJSONArray("result");
						int len = array.length();
						for(int i=0; i<len; i++){
							JSONObject oo = array.getJSONObject(i);
							acts.add(Act.parse(oo));
						}
						addViews();
					}else{
						String msg = o.optString("msg");
						Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
            }
        };
    }
	


    protected void addViews() {
    	int size = acts.size();
    	for(int i=0; i<size; i++){
    		if(i % 2 == 0){
    			ActLeft left = new ActLeft(getApplicationContext());
    			left.setData(acts.get(i));
        		left.setTag(i);
        		left.setOnClickListener(onClickListener);
        		container_left.addView(left);
    		}else{
    			ActRight right = new ActRight(getApplicationContext());
    			right.setData(acts.get(i));
    			right.setTag(i);
    			right.setOnClickListener(onClickListener);
    			container_right.addView(right);
    		}
    	}
	}
    
    private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Integer tag = (Integer)v.getTag();
			Log.i(TAG, "tag:" + tag);
			Act act = acts.get(tag);
			Intent i = new Intent(getApplicationContext(), AllSellerDetailActivity.class);
			i.putExtra("actid", act.id);
			startActivity(i);
		}
	};

	private Response.ErrorListener createGetAdErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	SimpleProgressDialog.dismiss();
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
