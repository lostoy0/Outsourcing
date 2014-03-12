package com.example.youlian;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.adapter.AreaListAdapter;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.RegioninfoVO;
import com.example.youlian.util.YlLogger;
import com.example.youlian.view.SimpleProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class AreaDistrictActivity extends BaseActivity implements OnItemClickListener {
private static YlLogger mLogger = YlLogger.getLogger(AreaCityActivity.class.getSimpleName());
	
	private ArrayList<RegioninfoVO> mRegioninfoVOs;
	
	private ListView mListView;
	private AreaListAdapter mAdapter;
	
	private RegioninfoVO mProvince, mCity, mDistrict;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_list);
		
		mRegioninfoVOs = new ArrayList<RegioninfoVO>();
		
		mProvince = (RegioninfoVO) getIntent().getSerializableExtra(AreaProvinceActivity.key_province);
		mCity = (RegioninfoVO) getIntent().getSerializableExtra(AreaProvinceActivity.key_city);
		
		((TextView) findViewById(R.id.tv_title)).setText("区");
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
		YouLianHttpApi.getAreaByProvinceIdCid(mProvince==null?null:mProvince.areaId, 
					mCity==null?null:mCity.areaId, null, 
							createGetCitySuccessListener(), createGetCityErrorListener());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mDistrict = mAdapter.getItem(position);
		Intent intent = new Intent();
		intent.putExtra(AreaProvinceActivity.key_district, mDistrict);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private Response.Listener<String> createGetCitySuccessListener() {
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
									if(vo != null) mRegioninfoVOs.add(0, vo);
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
	
	private Response.ErrorListener createGetCityErrorListener() {
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
