package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.adapter.CouponListAdapter;
import com.example.youlian.mode.YouhuiQuan;
import com.example.youlian.util.YlLogger;

/**
 * 我的优惠券列表
 * @author raymond
 *
 */
public class CouponListActivity extends BaseActivity implements OnItemClickListener, OnClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(CouponListActivity.class.getSimpleName());

	private ListView mListView;
	private CouponListAdapter mAdapter;
	
	private Button mEditButton;
	
	private ArrayList<YouhuiQuan> mCouponList;
	
	private boolean mEditing = false;
	
	public boolean getEditState() {
		return mEditing;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_list);
		
		mCouponList = new ArrayList<YouhuiQuan>();
		
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		((TextView) findViewById(R.id.tv_title)).setText("我的优惠券");
		
		mEditButton = (Button) findViewById(R.id.btn_right);
		mEditButton.setVisibility(View.VISIBLE);
		mEditButton.setOnClickListener(this);
		
		mListView = (ListView) findViewById(android.R.id.list);
		mListView.setOnItemClickListener(this);
		mAdapter = new CouponListAdapter(this, mCouponList);
		mListView.setAdapter(mAdapter);
		
		YouLianHttpApi.getCouponList(Global.getUserToken(this), createGetCardListSuccessListener(), createGetCardListErrorListener());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_right:
			if(!mEditing) {
				mEditing = true;
				mEditButton.setText("完成");
			} else {
				mEditing = false;
				mEditButton.setText("编辑");
			}
			mAdapter.notifyDataSetChanged();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		Intent intent = new Intent(this, CardActivity.class);
//		startActivity(intent);
	}

	private Response.Listener<String> createGetCardListSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						List<YouhuiQuan> couponList = YouhuiQuan.parse(response);
						if(couponList != null && couponList.size() > 0) {
							mCouponList.addAll(couponList);
							mAdapter.notifyDataSetChanged();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createGetCardListErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }

}