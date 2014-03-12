package com.example.youlian.more;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.BaseActivity;
import com.example.youlian.Global;
import com.example.youlian.R;
import com.example.youlian.YouLianHttpApi;
import com.example.youlian.adapter.MsgCenterAdapter;
import com.example.youlian.mode.MsgCenterResult;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;
import com.example.youlian.view.SimpleProgressDialog;

public class MsgCenterActivity extends BaseActivity implements OnClickListener, OnItemClickListener{
	private YlLogger mLogger = YlLogger.getLogger(MsgCenterActivity.class.getSimpleName());
	
	 private ImageButton mBackButton;
	 private ListView mListView;
	 private LinearLayout mEmptyView;
	 
	 private ArrayList<MsgCenterResult> mList;
	 private MsgCenterAdapter mAdapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_msg_center);
		
		mList = new ArrayList<MsgCenterResult>();
		
		initView();
		
		SimpleProgressDialog.show(this);
		YouLianHttpApi.getMessageList(Global.getUserToken(this), createGetMsgListSuccessListener(), createGetMsgListErrorListener());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void initView(){
		mBackButton = (ImageButton)findViewById(R.id.back);
		mBackButton.setOnClickListener(this);
		mEmptyView = (LinearLayout)findViewById(R.id.emptyView);
		mEmptyView.setVisibility(View.GONE);
		mListView = (ListView)findViewById(R.id.msg_listview);
		mListView.setOnItemClickListener(this);
		mAdapter = new MsgCenterAdapter(this, mList);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MsgCenterResult msg =null;
		msg = (MsgCenterResult)parent.getAdapter().getItem(position);
		Intent msgDetailedActivity = new Intent(this, MsgCenterDetActivity.class);
		msgDetailedActivity.putExtra("msg", msg);
		startActivity(msgDetailedActivity);
	}
	
	private Response.Listener<String> createGetMsgListSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					try {
						ArrayList<MsgCenterResult> list = MsgCenterResult.getList(response);
						if(Utils.isCollectionNotNull(list)) {
							hideEmptyView();
							mList.addAll(list);
							mAdapter.notifyDataSetChanged();
						} else {
							showEmptyView();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
	}
	
	private Response.ErrorListener createGetMsgListErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	SimpleProgressDialog.dismiss();
            	showEmptyView();
            }
        };
    }
	
	private void hideEmptyView() {
		mListView.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.GONE);
	}
	
	private void showEmptyView() {
		mListView.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.VISIBLE);
	}
}
