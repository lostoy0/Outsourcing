package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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
import com.example.youlian.adapter.FavouriteListAdapter;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Favourite;
import com.example.youlian.util.YlLogger;

/**
 * 我的收藏列表
 * @author raymond
 *
 */
public class FavouriteListActivity extends BaseActivity implements OnItemClickListener, OnClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(FavouriteListActivity.class.getSimpleName());

	private ListView mListView;
	private FavouriteListAdapter mAdapter;
	
	private Button mEditButton;
	
	private ArrayList<Favourite> mList;
	
	private boolean mEditing = false;
	
	public boolean getEditState() {
		return mEditing;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_list);
		
		mList = new ArrayList<Favourite>();
		
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		((TextView) findViewById(R.id.tv_title)).setText("我的收藏");
		
		mEditButton = (Button) findViewById(R.id.btn_right);
		mEditButton.setVisibility(View.VISIBLE);
		mEditButton.setOnClickListener(this);
		
		mListView = (ListView) findViewById(android.R.id.list);
		mListView.setOnItemClickListener(this);
		mAdapter = new FavouriteListAdapter(this, mList, MyVolley.getImageLoader());
		mListView.setAdapter(mAdapter);
		
		YouLianHttpApi.getCouponList(Global.getUserToken(this), createGetFavouriteListSuccessListener(), createGetFavouriteListErrorListener());
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Favourite favourite = mList.get(position);
		if(favourite != null) {
			String favorId = favourite.favour_id;
			if(TextUtils.isEmpty(favorId)) return;
			
			Intent intent = null;
			
			//关注的类型：1为会员卡2为优惠券 3为商家 4为普通活动 5 签到活动
			int type = favourite.type;
			switch(type) {
			case 1:
				intent = new Intent(this, MemberShipDetail.class);
				intent.putExtra("cardid", favorId);
				break;
				
			case 2:
				intent = new Intent(this, YouhuiQuanDetail.class);
				intent.putExtra("fav_ent_id", favorId);
				break;
				
			case 3:
				intent = new Intent(this, ShangjiaDetailActivity.class);
				intent.putExtra("customerid", favorId);
				break;
				
			case 4:
			case 5:
				intent = new Intent(this, AllSellerDetailActivity.class);
				intent.putExtra("actid", favorId);
				break;
			}
			
			startActivity(intent);
		}
	}

	private Response.Listener<String> createGetFavouriteListSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					List<Favourite> couponList = Favourite.from(response);
					if(couponList != null && couponList.size() > 0) {
						mList.addAll(couponList);
						mAdapter.notifyDataSetChanged();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createGetFavouriteListErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }

}
