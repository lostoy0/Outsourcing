package com.example.youlian;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.mode.Comment;
import com.example.youlian.mode.YouhuiQuan;
import com.example.youlian.view.ActLeft;
import com.example.youlian.view.ActRight;
import com.example.youlian.view.CommentItem;

public class CommentAddActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AllSellerActivity";
	private ImageButton back;
	private TextView tv_title;
	private LinearLayout container_left;
	private LinearLayout container_right;
	
	private YouhuiQuan quan;
	private ImageButton ib_right;
	private ImageView iv_icon_one;
	private ImageView iv_icon_two;
	private ImageView iv_icon_three;
	private ImageView iv_icon_four;
	private ImageView iv_icon_five;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_comment);
		
		initViews();
		
		quan = (YouhuiQuan) getIntent().getSerializableExtra("quan");
		
	}
	
	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		
		ib_right = (ImageButton) this.findViewById(R.id.ib_right);
		ib_right.setBackgroundResource(R.drawable.select_btn_sumbit);
		ib_right.setVisibility(View.VISIBLE);
		ib_right.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.act);
		
		iv_icon_one = (ImageView)this.findViewById(R.id.iv_icon_one);
		iv_icon_two = (ImageView)this.findViewById(R.id.iv_icon_two);
		iv_icon_three = (ImageView)this.findViewById(R.id.iv_icon_three);
		iv_icon_four = (ImageView)this.findViewById(R.id.iv_icon_four);
		iv_icon_five = (ImageView)this.findViewById(R.id.iv_icon_five);
		iv_icon_one.setOnClickListener(this);
		iv_icon_two.setOnClickListener(this);
		iv_icon_three.setOnClickListener(this);
		iv_icon_four.setOnClickListener(this);
		iv_icon_five.setOnClickListener(this);
		
	}
	
	

	private Response.Listener<String> createGetCommentSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "申请成功",
									Toast.LENGTH_SHORT).show();
							JSONArray array = o.optJSONArray("result");
							int size = array.length();
							for(int i=0; i<size; i++){
								JSONObject oo = array.getJSONObject(i);
								Comment c = Comment.parse(oo);
								CommentItem one = new CommentItem(getApplicationContext());
								
								if(i%2 == 0){
									c.pic = "asdf";
									one.setData(c);
									container_left.addView(one);
								}else{
									one.setData(c);
									container_right.addView(one);
								}
							}
							
						} else {
							String msg = o.optString("msg");
							Toast.makeText(getApplicationContext(), msg,
									Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, "error");
				Toast.makeText(getApplicationContext(), "请求失败",
						Toast.LENGTH_SHORT).show();
			}
		};
	}


    protected void addViews() {
    	for(int i=0; i<5; i++){
    		ActLeft left = new ActLeft(getApplicationContext());
    		ActRight right = new ActRight(getApplicationContext());
    		left.setData();
    		left.setTag(i);
    		left.setOnClickListener(onClickListener);
    		container_left.addView(left);
    		container_right.addView(right);
    	}
	}
    
    private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Integer tag = (Integer)v.getTag();
			Log.i(TAG, "tag:" + tag);
			Intent i = new Intent(getApplicationContext(), AllSellerDetailActivity.class);
			startActivity(i);
		}
	};

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
		case R.id.ib_right:
			Log.i(TAG, "ddddddddd");
			
			break;
		case R.id.iv_icon_one:
			break;
		case R.id.iv_icon_two:
			break;
		case R.id.iv_icon_three:
			break;
		case R.id.iv_icon_four:
			break;
		case R.id.iv_icon_five:
			break;
			

		default:
			break;
		}
	}
}
