package com.example.youlian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.adapter.GoodsListAdapter;
import com.example.youlian.app.MyVolley;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.Goods;
import com.example.youlian.mode.Order;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;
import com.example.youlian.view.SimpleProgressDialog;

public class TabShopCart extends BaseActivity implements OnClickListener {
	private YlLogger mLogger = YlLogger.getLogger(TabShopCart.class.getSimpleName());
	
	private TextView mShopNameTextView, mCostMoneyTextView, mAmountTextView;
	private Button mEditButton, mYesButton, mPayButton, mDeleteButton;
	
	private View mContentView, mEmptyView;
	
	private ListView mListView;
	private GoodsListAdapter mAdapter;
	private ArrayList<Goods> mGoodsList;
	private HashMap<String, Boolean> mStateMap;
	
	private String mSelectCartIds;

	private boolean mIsEditing = false;
	public boolean isEditing() {
		return mIsEditing;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_tab_shoppingcart);
		
		mGoodsList = new ArrayList<Goods>();
		mStateMap = new HashMap<String, Boolean>();
		
		initViews();
		
		if(!PreferencesUtils.isLogin(this)) {
			login();
		} else {
			loadData();
		}
	}
	
	private void loadData() {
//		SimpleProgressDialog.show(this);
		YouLianHttpApi.getShoppingCart(Global.getUserToken(this), createGetGoodsListSuccessListener(), createGetGoodsListErrorListener());
	}

	private void login() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivityForResult(intent, Constants.REQ_CODE_LOGIN);
	}

	private void initViews() {
		mContentView = findViewById(R.id.contentView);
		mEmptyView = findViewById(R.id.emptyView);
		mEmptyView.setVisibility(View.GONE);
		
		mShopNameTextView = (TextView) findViewById(R.id.cart_tv_goodsname);
		mShopNameTextView.setText("");
		mCostMoneyTextView = (TextView) findViewById(R.id.cart_tv_money);
		mAmountTextView = (TextView) findViewById(R.id.cart_tv_amount);
		
		mEditButton = (Button) findViewById(R.id.cart_btn_edit);
		mEditButton.setOnClickListener(this);
		mYesButton = (Button) findViewById(R.id.cart_btn_yes);
		mYesButton.setOnClickListener(this);
		mPayButton = (Button) findViewById(R.id.cart_btn_pay);
		mPayButton.setOnClickListener(this);
		mDeleteButton = (Button) findViewById(R.id.cart_btn_delete);
		mDeleteButton.setOnClickListener(this);
		
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new GoodsListAdapter(this, mGoodsList, mStateMap, MyVolley.getImageLoader());
		mListView.setAdapter(mAdapter);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.cart_btn_edit:
			edit();
			break;
			
		case R.id.cart_btn_yes: 
			editComplete();
			break;
			
		case R.id.cart_btn_pay:
			if(!mGoodsList.isEmpty() && selectAtLeastOne()) {
				resetPayingData();
				SimpleProgressDialog.show(this);
				YouLianHttpApi.addOrder(Global.getUserToken(this), mSelectCartIds, 0, 
						createAddOrderSuccessListener(), createAddOrderErrorListener());
			} else {
				Utils.showToast(this, "购物车还没有物品不需要支付哦");
			}
			break;
			
		case R.id.cart_btn_delete:
			deleteSelectedGoods();
			break;
		}
	}
	
	private void resetPayingData() {
		mSelectCartIds = "";
		StringBuilder builder = new StringBuilder();
		for(Goods goods : mGoodsList) {
			if(mStateMap.get(goods.goodsId)) {
				builder.append(goods.id).append(",");
			}
		}
		if(builder.length() > 0) {
			builder.deleteCharAt(builder.length()-1);
			mSelectCartIds = builder.toString();
		}
	}
	
	private boolean selectAtLeastOne() {
		for(Goods goods: mGoodsList) {
			if(mStateMap.get(goods.goodsId)) return true;
		}
		return false;
	}

	private void startPay(Order order) {
		Intent intent = new Intent(this, PayActivity.class);
		intent.putExtra(PayActivity.KEY_ORDER, order);
		startActivity(intent);
	}

	private void deleteSelectedGoods() {
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		for(int i=0; i<mGoodsList.size(); i++) {
			if(mStateMap.get(mGoodsList.get(i).goodsId)) {
				mStateMap.remove(mGoodsList.get(i).goodsId);
				continue;
			}
			goodsList.add(mGoodsList.get(i));
		}
		mGoodsList.clear();
		mGoodsList.addAll(goodsList);
		mAdapter.notifyDataSetChanged();
	}

	private void editComplete() {
		mIsEditing = false;
		mEditButton.setVisibility(View.VISIBLE);
		mYesButton.setVisibility(View.GONE);
		mPayButton.setVisibility(View.VISIBLE);
		mDeleteButton.setVisibility(View.GONE);
		mAdapter.notifyDataSetChanged();
	}

	private void edit() {
		mIsEditing = true;
		mEditButton.setVisibility(View.GONE);
		mYesButton.setVisibility(View.VISIBLE);
		mPayButton.setVisibility(View.GONE);
		mDeleteButton.setVisibility(View.VISIBLE);
		resetDeleteButton();
		mAdapter.notifyDataSetChanged();
	}
	
	public void resetQuantityAndMoney() {
		int totalQuantity = 0;
		int totalMoney = 0;
		for(int i=0; i<mGoodsList.size(); i++) {
			if(!mStateMap.get(mGoodsList.get(i).goodsId)) continue; 
			Goods goods = mGoodsList.get(i);
			if(goods != null) {
				totalQuantity += goods.quantity;
				totalMoney += goods.goodsPrice*goods.quantity;
			}
		}
		resetMoney(totalMoney);
		resetQuantity(totalQuantity);
	}
	
	public void resetDeleteButton() {
		mDeleteButton.setText("删除(" + getSelectGoodsCount() + ")");
	}
	
	private void resetMoney(int money) {
		mCostMoneyTextView.setText(money + "元");
	}
	
	private void resetQuantity(int quantity) {
		mAmountTextView.setText(quantity + "");
	}
	
	private int getSelectGoodsCount() {
		int selectQuantity = 0;
		for(int i=0; i<mGoodsList.size(); i++) {
			if(mStateMap.get(mGoodsList.get(i).goodsId)) selectQuantity ++;
		}
		return selectQuantity;
	}
	
	private Response.Listener<String> createGetGoodsListSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
//				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						List<Goods> goodsList = Goods.get(object);
						if(Utils.isCollectionNotNull(goodsList)) {
							mContentView.setVisibility(View.VISIBLE);
							mEmptyView.setVisibility(View.GONE);
							
							mGoodsList.addAll(goodsList);
							for(int i=0; i<mGoodsList.size(); i++) {
								mStateMap.put(mGoodsList.get(i).goodsId, true);
							}
							mAdapter.notifyDataSetChanged();
							resetQuantityAndMoney();
						} else {
							showEmptyView();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createGetGoodsListErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//            	SimpleProgressDialog.dismiss();
            	mLogger.e(error.getMessage());
            	showEmptyView();
            }
        };
    }
	
	private Response.Listener<String> createAddOrderSuccessListener() {
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
						Order order = Order.from(object.optJSONObject(Constants.key_result));
						if(order != null) {
							startPay(order);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createAddOrderErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	SimpleProgressDialog.dismiss();
            	mLogger.e(error.getMessage());
            }
        };
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constants.REQ_CODE_LOGIN && resultCode == RESULT_OK) {
			//登录成功，并成功返回
			if(!TextUtils.isEmpty(Global.getUserToken(this))) {
				//usertoken is valid, then load data
				loadData();
			}
		}
	}

	private void showEmptyView() {
		mContentView.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.VISIBLE);
	}
	
}
