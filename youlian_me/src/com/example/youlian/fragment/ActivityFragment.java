package com.example.youlian.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.AllSellerDetailActivity;
import com.example.youlian.R;
import com.example.youlian.TabSign;
import com.example.youlian.YouLianHttpApi;
import com.example.youlian.adapter.ActivityAllListAdapter;
import com.example.youlian.adapter.ActivitySignListAdapter;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.ActivityAll;
import com.example.youlian.mode.ActivitySign;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;
import com.youlian.waterfall.lib.PLA_AdapterView;
import com.youlian.waterfall.lib.PLA_AdapterView.OnItemClickListener;
import com.youlian.waterfall.lib.XListView;
import com.youlian.waterfall.lib.XListView.IXListViewListener;

public class ActivityFragment extends BaseFragment {
	private static YlLogger mLogger = YlLogger.getLogger(ActivityFragment.class.getSimpleName());
	
	private static final int LOADING_MORE = 1;
	private static final int LOADING_REFRESH = 2;
	
	private XListView mListView;
	private ActivityAllListAdapter mAllListAdapter;
	private ActivitySignListAdapter mSignListAdapter;
	private List<ActivityAll> mAllList;
	private List<ActivitySign> mSignList;
	
	private int mType = 0;
	
	private boolean mIsLoading = false;
	private int mLoadingType;
	
	private boolean isSign() {
		return mType == TabSign.TYPE_SIGN;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if(bundle != null) {
			mType = bundle.getInt(TabSign.KEY_TYPE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_activity, container, false);
		mListView = (XListView) rootView.findViewById(R.id.list);
		mListView.setPullLoadEnable(false);
		mListView.setXListViewListener(mPullRefreshListener);
		mListView.setOnItemClickListener(mOnItemClickListener);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(isSign()) {
			mSignList = new ArrayList<ActivitySign>();
			mSignListAdapter = new ActivitySignListAdapter(getActivity(), mSignList, MyVolley.getImageLoader());
			mListView.setAdapter(mSignListAdapter);
		} else {
			mAllList = new ArrayList<ActivityAll>();
			mAllListAdapter = new ActivityAllListAdapter(getActivity(), mAllList, MyVolley.getImageLoader());
			mListView.setAdapter(mAllListAdapter);
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}
	
	private void refresh() {
		mIsLoading = true;
		mLoadingType = LOADING_REFRESH;
		if(isSign()) {
			YouLianHttpApi.getSignActivitys("", createGetSignActivitySuccessListener(), createGetSignActivityErrorListener());
		} else {
			YouLianHttpApi.getAllActivitys("", createGetAllActivitySuccessListener(), createGetAllActivityErrorListener());
		}
	}

	private IXListViewListener mPullRefreshListener = new IXListViewListener() {
		@Override
		public void onRefresh() {
			if(!mIsLoading) {
				refresh();
			}
		}

		@Override
		public void onLoadMore() {
			if(!mIsLoading) {
				mIsLoading = true;
				mLoadingType = LOADING_MORE;
				if(isSign()) {
					
				} else {
					
				}
			}
		}
	};
	
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(PLA_AdapterView<?> parent, View view,
				int position, long id) {
			
			mLogger.i("position: " + position);
			
			String actid = null;
			if(isSign()) {
				ActivitySign sign = mSignListAdapter.getItem(position-1);
				if(sign == null) return;
				actid = sign.id;
			} else {
				ActivityAll all = mAllListAdapter.getItem(position-1);
				if(all == null) return;
				actid = all.id;
			}
			
			if(!TextUtils.isEmpty(actid)) {
				Intent i = new Intent(getActivity(), AllSellerDetailActivity.class);
				i.putExtra("actid", actid);
				startActivity(i);
			}

		}
	};
	
	private Response.Listener<String> createGetAllActivitySuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					try {
						JSONObject object = new JSONObject(response);
						List<ActivityAll> list = ActivityAll.getList(object);
						if(Utils.isCollectionNotNull(list)) {
							if(mLoadingType == LOADING_REFRESH) {
								mAllList.clear();
							}
							mAllList.addAll(list);
							mAllListAdapter.notifyDataSetChanged();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				loadingComplete();
			}
		};
	}
	
	private Response.ErrorListener createGetAllActivityErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	loadingComplete();
            }
        };
    }
	
	private Response.Listener<String> createGetSignActivitySuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					try {
						JSONObject object = new JSONObject(response);
						List<ActivitySign> list = ActivitySign.getList(object);
						if(Utils.isCollectionNotNull(list)) {
							if(mLoadingType == LOADING_REFRESH) {
								mSignList.clear();
							}
							mSignList.addAll(list);
							mSignListAdapter.notifyDataSetChanged();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				loadingComplete();
			}
		};
	}
	
	private Response.ErrorListener createGetSignActivityErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	loadingComplete();
            }
        };
    }

	private void loadingComplete() {
		mIsLoading = false;
		if(mLoadingType == LOADING_MORE) {
			mListView.stopLoadMore();
		} else if(mLoadingType == LOADING_REFRESH) {
			mListView.stopRefresh();
		}
	}
}
