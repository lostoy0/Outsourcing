package com.example.youlian.more;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.R;
import com.example.youlian.YouLianHttpApi;
import com.example.youlian.mode.AppRecResult;
import com.example.youlian.view.SimpleProgressDialog;
import com.youlian.waterfall.lib.XListView;


/**
 * @author rome
 * @proName youlian
 * @version 1.0
 * @Data 2012-9-26 ����11:02:32
 *
   <b>Comment...</b>
 */
public class AppRecommendActivity extends Activity implements OnClickListener{
	
	 private XListView mListView;
	
	 private ImageButton backBtn;
	 
	 private final static int GET_APP_REC = 1;

	protected static final String TAG = "AppRecommendActivity";
	 
	 private AppRemStoreAdapter mAdapter;
	 
	 private ArrayList<AppRecResult> appRec;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.more_app_recommend);
		initView();
		initListener();
		synConnection(GET_APP_REC);
	}
	/**
	 * ��ʼ������
	 */
	private void initView(){
		backBtn = (ImageButton)findViewById(R.id.back);
		mListView =  (XListView)findViewById(R.id.apprem_listview);
		mAdapter = new AppRemStoreAdapter(this,appRec);
		mListView.setAdapter(mAdapter);
		mListView.setPullLoadEnable(false);
	}
	/**
	 * �¼�����
	 */
	private void initListener(){
		backBtn.setOnClickListener(this);
	}
	/**
	 *
	 * @param type
	 */
	private void synConnection(int type){
		SimpleProgressDialog.show(this);
		YouLianHttpApi.getRecommandApp(createGetAdSuccessListener(), createGetAdErrorListener());
	}
	private Response.Listener<String> createGetAdSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	SimpleProgressDialog.dismiss();
            	Log.i(TAG, "success:" + response);
            	if (response != null) {
				try {
					JSONObject o = new JSONObject(response);
					int status = o.optInt("status");
					if(status == 1){
						JSONArray array = o.optJSONArray("result");
						appRec = new ArrayList<AppRecResult>();
						int len = array.length();
						for(int i=0; i<len; i++){
							JSONObject oo = array.getJSONObject(i);
							appRec.add(AppRecResult.parse(oo));
						}
						
						mAdapter.addAllDataList(appRec);
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
	
	
	private Response.ErrorListener createGetAdErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	SimpleProgressDialog.dismiss();
            	Log.i(TAG, "error");
            }
        };
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
	private void initAppRec(){
//		if (mAdapter == null) {
//			mAdapter = new AppRemStoreAdapter(this,appRec);
//			mListView.setAdapter(mAdapter);
//		}else{
//			mAdapter.addAllDataList(appRec);
//			mListView.setSelection(0);
//		}
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

		default:
			break;
		}
	}
}
