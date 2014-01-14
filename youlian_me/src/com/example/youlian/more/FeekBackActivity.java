package com.example.youlian.more;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.R;
import com.example.youlian.YouLianHttpApi;
import com.example.youlian.common.Config;
import com.example.youlian.util.Utils;

/**
 * @author rome
 * @proName youlian
 * @version 1.0
 * @Data 2012-9-26 ����11:02:32
 *
   <b>Comment...</b>
 */
public class FeekBackActivity extends Activity implements OnClickListener{
	
	 private ImageButton backBtn,more_custom_service_phone;
	 
	 private static final int FEEK_BACK =1;

	protected static final String TAG = "FeekBackActivity";
	 
	 private EditText feekback_edit;
	 
	 private Button submitBtn;
	 
	 private String feekContent;
	 
	 private TextView tv_title;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.more_feekback);
		initView();
		initListener();
		
	}
	/**
	 *
	 * @param type
	 */
	private void synConnection(String content){
		YouLianHttpApi.feedBack("", content, createGetAdSuccessListener(), createGetAdErrorListener());
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
	
	/**
	 * ��ʼ������
	 */
	private void initView(){
		backBtn = (ImageButton)findViewById(R.id.back);
		submitBtn = (Button)findViewById(R.id.submit);
		more_custom_service_phone = (ImageButton)findViewById(R.id.more_custom_service_phone);
		feekback_edit = (EditText)findViewById(R.id.feekback_edit);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.user_feedback);
		
		
	}
	/**
	 * �¼�����
	 */
	private void initListener(){
		backBtn.setOnClickListener(this);
		submitBtn.setOnClickListener(this);
		more_custom_service_phone.setOnClickListener(this);
	}
	
	/**
	 * login error...
	 */
	private void getError(String msg) {
		Toast toast = Toast.makeText(getApplicationContext(),
				msg, Toast.LENGTH_LONG);
			   toast.setGravity(Gravity.BOTTOM, 0, 0);
			   toast.show();
	}
	/**
	 * @author simon
	 * @pyoulian
	 * @version 1.0
	 * @Data ����11:02:32
	 */

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();
			
			break;
		case R.id.submit:
			 feekContent = feekback_edit.getText().toString();
			int size = feekContent.length();
			 
			if(size>0){
				if(feekContent.length()>300){
					getError(getString(R.string.not_more_than_three));
					
				}else{
					synConnection(feekContent);
				}
			}else{ 
				getError(getString(R.string.not_null));
			}
			break;
		case R.id.more_custom_service_phone:
			Utils.call(this, Config.youlianPhone);
			break;

		}
	}

}
