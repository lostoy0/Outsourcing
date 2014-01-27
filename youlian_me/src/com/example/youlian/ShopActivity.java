package com.example.youlian;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.mode.Shop;
import com.example.youlian.mode.YouhuiQuan;
import com.example.youlian.util.Utils;

public class ShopActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AllSellerActivity";
	private ImageButton back;
	private TextView tv_title;
	
	private ImageButton ib_right;
	private LinearLayout branch_shop_lay;
	private String title;
	public ArrayList<Shop> shops;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop);
		title = getIntent().getStringExtra("title");
		shops = (ArrayList<Shop>) getIntent().getSerializableExtra("shops");
		
		initViews();
		
		
		
		// CardDetailedActivity 以前版本
		initBranchShop(shops);
	}
	
	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(title);
		
		branch_shop_lay = (LinearLayout)this.findViewById(R.id.branch_shop_lay);
	}
	
	
	
	private void initBranchShop(ArrayList<Shop> shops){
		LinearLayout.LayoutParams Linparams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams Linparams3 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		RelativeLayout.LayoutParams Relparams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		RelativeLayout.LayoutParams Relparams2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		System.out.println("shops 大小:"+shops.size());
//		LinearLayout lay = new LinearLayout(this);
//		lay.setLayoutParams(new RelativeLayout.LayoutParams(
//				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		int size = shops.size();
		if(Utils.isArrayNotNull(shops)){
			for (int i =0;i<size;i++) {
				//左边布局
				System.out.println("店铺："+i);
				Shop  shop = shops.get(i);
				LinearLayout allLay = new LinearLayout(this);
				allLay.setLayoutParams(Relparams);
				allLay.setPadding(8, 8, 8, 8);
				allLay.setOrientation(LinearLayout.VERTICAL);
				
				
				RelativeLayout relativelay = new RelativeLayout(this);
				relativelay.setLayoutParams(Linparams);
//				relativelay.setPadding(8, 8, 8, 8);
				relativelay.setId(i);
				
				LinearLayout leftLay = new LinearLayout(this);
				leftLay.setLayoutParams(Relparams);
				leftLay.setOrientation(LinearLayout.VERTICAL);
				
				TextView shop_name = new TextView(this);
//				TextPaint tpaint = shop_name.getPaint();
//                tpaint .setFakeBoldText(true);
				String shopName = shop.name;
				shop_name.setLayoutParams(Linparams3);
				shop_name.setTextColor(Color.BLACK);
				shop_name.setTextSize(16);
//				if(Utils.notNull(shopName)){
//					int lnh = shopName.length();
//					if(lnh>13){
//						shop_name.setText(shopName.substring(0,13)+"\n"+shopName.substring(13,lnh));
//					}else{
//						shop_name.setText(shopName);
//					}
//				}
				shop_name.setText(shopName);
				
				TextView shop_phone = new TextView(this);
				final String phone = shop.phone;
				shop_phone.setLayoutParams(Linparams3);
				shop_phone.setTextColor(R.color.black_2);
				shop_phone.setTextSize(16);
				shop_phone.setText(phone);
				
				TextView shop_address = new TextView(this);
				shop_address.setLayoutParams(Linparams3);
				shop_address.setTextColor(R.color.black_2);
				shop_address.setTextSize(16);
				String adress = shop.address;
//				if(Utils.notNull(adress)){
//					int lnh = adress.length();
//					if(lnh>14){
//						shop_address.setText(adress.substring(0,14)+"\n"+adress.substring(14,lnh));
//					}else{
//						shop_address.setText(adress);
//					}
//				}
				shop_address.setText(adress);
				leftLay.addView(shop_name);
				leftLay.addView(shop_phone);
//				leftLay.addView(shop_address);
				
				//右边布局
				LinearLayout rightLay = new LinearLayout(this);
				Relparams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
				rightLay.setLayoutParams(Relparams2);
				rightLay.setOrientation(LinearLayout.HORIZONTAL);
				
				ImageButton phoneBtn = new ImageButton(this);
				LinearLayout.LayoutParams Linparams2 = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				Linparams2.rightMargin=6;
				phoneBtn.setLayoutParams(Linparams2);
				phoneBtn.setBackgroundResource(R.drawable.call_btn);
				
				ImageButton adressBtn = new ImageButton(this);
				adressBtn.setLayoutParams(Linparams);
				adressBtn.setBackgroundResource(R.drawable.map_btn);
				
				rightLay.addView(phoneBtn);
				rightLay.addView(adressBtn);
				
				relativelay.addView(leftLay);
				relativelay.addView(rightLay);
				
				
				allLay.addView(relativelay);
				allLay.addView(shop_address);
				
				
				phoneBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(Utils.notNull(phone))
						Utils.call(getApplicationContext(), phone);
						
					}
				});
				 final String lng = shop.lng;
				 final String lat = shop.lat;
				
				adressBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						double lngf = 0;
						double latf = 0;
						if (Utils.notNull(lng))
							lngf = Double.parseDouble(lng);
						if (Utils.notNull(lat))
							latf = Double.parseDouble(lat);
//						Intent mBaiduMapActivity = new Intent(getApplicationContext(),BaiduMapActivity.class);
//						mBaiduMapActivity.putExtra("dLong", lngf);
//						mBaiduMapActivity.putExtra("dLat", latf);
//						mBaiduMapActivity.putExtra("storeName", cardDet.getCard_name());
//						startActivity(mBaiduMapActivity);
						
					}
				});
				branch_shop_lay.addView(allLay);
				if(i<size-1){
					LinearLayout lay = new LinearLayout(this);
					lay.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.FILL_PARENT, 1));
					lay.setBackgroundColor(R.color.grey);
					branch_shop_lay.addView(lay);
				}
			}
		}
		
	}
	
	

	private Response.Listener<String> createGetCommentSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "申请成功",
									Toast.LENGTH_SHORT).show();
							
							
						} else {
							String msg = o.optString("msg");
							Toast.makeText(getApplicationContext(), msg,
									Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, "error");
				Toast.makeText(getApplicationContext(), "请求失败",
						Toast.LENGTH_SHORT).show();
			}
		};
	}


  
    
    private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Integer tag = (Integer)v.getTag();
			Log.i(TAG, "tag:" + tag);
			Intent i = new Intent(getApplicationContext(), AllSellerDetailActivity.class);
			startActivity(i);
		}
	};

	private Response.ErrorListener createGetAdErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Log.i(TAG, "error");
            }
        };
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.ib_right:
			Intent i = new Intent(getApplicationContext(), CommentAddActivity.class);
			startActivity(i);
			break;
			

		default:
			break;
		}
	}
}
