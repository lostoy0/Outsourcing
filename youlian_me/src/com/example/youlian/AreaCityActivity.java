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

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class AreaCityActivity extends BaseActivity implements OnItemClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(AreaCityActivity.class.getSimpleName());
	
	private static final int req_district = 0x1000;
	
	private ArrayList<RegioninfoVO> mRegioninfoVOs;
	
	private ListView mListView;
	private AreaListAdapter mAdapter;
	
	private RegioninfoVO mProvince, mCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_list);
		
		mRegioninfoVOs = new ArrayList<RegioninfoVO>();
		
		mProvince = (RegioninfoVO) getIntent().getSerializableExtra(AreaProvinceActivity.key_province);
		
		((TextView) findViewById(R.id.tv_title)).setText("城市");
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
		
		YouLianHttpApi.getAreaByProvinceIdCid(mProvince==null?null:mProvince.areaId, null, null, 
				createGetCitySuccessListener(), createGetCityErrorListener());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mCity = mAdapter.getItem(position);
		
		Global.saveLocCityId(this, mCity.areaId);
		
		Intent intent = new Intent(this, AreaDistrictActivity.class);
		intent.putExtra(AreaProvinceActivity.key_province, mProvince);
		intent.putExtra(AreaProvinceActivity.key_city, mCity);
		startActivityForResult(intent, req_district);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == req_district && resultCode == RESULT_OK) {
			data.putExtra(AreaProvinceActivity.key_city, mCity);
			setResult(RESULT_OK, data);
			finish();
		}
	}
	
	private Response.Listener<String> createGetCitySuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
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
            	finish();
            }
        };
    }

}
