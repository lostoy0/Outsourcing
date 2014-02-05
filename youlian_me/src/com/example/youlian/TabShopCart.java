package com.example.youlian;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class TabShopCart extends BaseActivity implements OnClickListener {
	
	private TextView mShopNameTextView, mCostMoneyTextView, mAmountTextView;
	private Button mEditButton, mYesButton, mPayButton, mDeleteButton;
	
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_tab_shoppingcart);
		
		initViews();
	}

	private void initViews() {
		mShopNameTextView = (TextView) findViewById(R.id.cart_tv_goodsname);
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
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.cart_btn_edit:
			
			break;
			
		case R.id.cart_btn_yes: 
			
			break;
			
		case R.id.cart_btn_pay:
			
			break;
			
		case R.id.cart_btn_delete:
			
			break;
		}
	}

}
