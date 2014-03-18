package com.example.youlian;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.adapter.AreaListAdapter;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.RegioninfoVO;
import com.example.youlian.util.YlLogger;
import com.example.youlian.view.SimpleProgressDialog;


public class AreaProvinceActivity extends BaseActivity implements OnItemClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(AreaProvinceActivity.class.getSimpleName());
	
	public static final String key_province = "province";
	public static final String key_city = "city";
	public static final String key_district = "district";

	private static final int req_city = 0x1000;
	
	private ArrayList<RegioninfoVO> mRegioninfoVOs;
	
	private ListView mListView;
	private AreaListAdapter mAdapter;
	
	private RegioninfoVO mProvince;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_list);
		
		mRegioninfoVOs = new ArrayList<RegioninfoVO>();
		
		((TextView) findViewById(R.id.tv_title)).setText("省份");
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mListView = (ListView) findViewById(android.R.id.list);
		mListView.setOnItemClickListener(this);
		mAdapter = new AreaListAdapter(this, mRegioninfoVOs);
		mListView.setAdapter(mAdapter);
		
		SimpleProgressDialog.show(this);
		YouLianHttpApi.getAreaByProvinceIdCid(null, null, null, createGetProvinceSuccessListener(), createGetProvinceErrorListener());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mProvince = mAdapter.getItem(position);
		Intent intent = new Intent(this, AreaCityActivity.class);
		intent.putExtra(key_province, mProvince);
		startActivityForResult(intent, req_city);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == req_city && resultCode == RESULT_OK) {
			data.putExtra(key_province, mProvince);
			setResult(RESULT_OK, data);
			finish();
		}
	}
	
	private Response.Listener<String> createGetProvinceSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						if("1".equals(object.opt(Constants.key_status))) {
							JSONArray array = object.optJSONArray(Constants.key_result);
							if(array != null && array.length()>0) {
								for(int i=0; i<array.length(); i++) {
									RegioninfoVO vo = RegioninfoVO.from(array.getJSONObject(i));
									if(vo != null) mRegioninfoVOs.add(vo);
								}
								mAdapter.notifyDataSetChanged();
							}
						} 
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createGetProvinceErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	SimpleProgressDialog.dismiss();
            	finish();
            }
        };
    }

}
