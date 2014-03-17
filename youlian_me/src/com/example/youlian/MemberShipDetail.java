package com.example.youlian;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Card;
import com.example.youlian.util.PreferencesUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

public class MemberShipDetail extends Activity implements OnClickListener {
	protected static final String TAG = "MembershipActivity";
	
	private static final int req_apply_card = 0x1000;
	
	private ImageButton back;
	private TextView tv_title;
	private Card card;
	private ImageView iv_biaoshi;
	private ImageView iv_icon;
	private TextView tv_apply;
	private TextView tv_desc;
	private TextView tv_date;
	private ImageView iv_online_chong;
	private TextView tv_left;
	private Button bt_chongzhi;
	private TextView tv_chongzhi_desc;
	private TextView tv_member_fuli;
	private TextView tv_use_desc;
	
	
	private RelativeLayout rel_mengdian;
	private TextView tv_mendian_info;
	private RelativeLayout rel_user_comment;
	private TextView tv_user_comment;
	private RelativeLayout rel_shop_desc;
	private TextView tv_shop_desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_membership_detail);

		String card_id = getIntent().getStringExtra("cardid");
		
		initViews();
		
		YouLianHttpApi.getMemberCardDetail(
				Global.getUserToken(getApplicationContext()), card_id,
				createGetCardDetailSuccessListener(), createMyReqErrorListener());
	}

	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back); 
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		
		
		iv_biaoshi = (ImageView)this.findViewById(R.id.iv_biaoshi);
		iv_icon = (ImageView)this.findViewById(R.id.iv_icon);
		tv_apply  = (TextView) this.findViewById(R.id.tv_apply);
		tv_desc  = (TextView) this.findViewById(R.id.tv_desc);
		tv_date = (TextView) this.findViewById(R.id.tv_date);
		iv_online_chong = (ImageView)this.findViewById(R.id.iv_online_chong);
		tv_left  = (TextView) this.findViewById(R.id.tv_left);
		bt_chongzhi = (Button)this.findViewById(R.id.bt_chongzhi);
		bt_chongzhi.setOnClickListener(this);
		tv_chongzhi_desc = (TextView) this.findViewById(R.id.tv_chongzhi_desc);
		tv_member_fuli = (TextView) this.findViewById(R.id.tv_member_fuli);
		tv_use_desc = (TextView) this.findViewById(R.id.tv_use_desc);
		rel_mengdian = (RelativeLayout) this.findViewById(R.id.rel_mengdian);
		rel_mengdian.setOnClickListener(this);
		tv_mendian_info = (TextView) this.findViewById(R.id.tv_mendian_info);
		rel_user_comment = (RelativeLayout) this
				.findViewById(R.id.rel_user_comment);
		rel_user_comment.setOnClickListener(this);
		tv_user_comment = (TextView) this.findViewById(R.id.tv_user_comment);
		rel_shop_desc = (RelativeLayout) this.findViewById(R.id.rel_shop_desc);
		rel_shop_desc.setOnClickListener(this);
		tv_shop_desc = (TextView) this.findViewById(R.id.tv_shop_desc);
		
		// set tab buttons
				initShareViews();
	}
	
	
	private Button mPieButton;
	private Button mTierButton;
	private Button mWiGameButton;
	private Button mMoreButton;
	private void initShareViews() {
		mPieButton = (Button) findViewById(R.id.btn_pie);//
		mPieButton.setOnClickListener(this);
		mTierButton = (Button) findViewById(R.id.btn_tier);
		mTierButton.setOnClickListener(this);
		mWiGameButton = (Button) findViewById(R.id.btn_wigame);
		mWiGameButton.setOnClickListener(this);
		mMoreButton = (Button) findViewById(R.id.btn_more);
		mMoreButton.setOnClickListener(this);
	}
	
	
	protected void refreshView() {
		ImageLoader imageLoader = MyVolley.getImageLoader();
		imageLoader.get(card.activatedPic, ImageLoader
				.getImageListener(iv_icon, R.drawable.guanggao,
						R.drawable.guanggao));
		tv_title.setText(card.card_name);
		
		if(TextUtils.isEmpty(card.user_card)){
			bt_chongzhi.setText(R.string.apply);
		}else{
			bt_chongzhi.setEnabled(false);
			bt_chongzhi.setClickable(false);
			bt_chongzhi.setText(R.string.already_apply);
		}
		
		
		if("1".equals(card.is_follow)){
			isFav = true;
			mMoreButton.setText("已收藏");
		}else{
			isFav = false;
			mMoreButton.setText("收藏");
		}
		
		tv_apply.setText(getString(R.string.number_apply_use_card, card.card_num));
		tv_desc.setText(card.agioInfo);
		tv_date.setText("有效期：" + card.time_from + "至" + card.time_to);
		tv_left.setText("剩余：" + card.card_surplus_num);
		
		tv_chongzhi_desc.setText(card.payPolicy);
		tv_member_fuli.setText(card.cluber_welfare);
		tv_use_desc.setText(card.card_directions);
		
		
		tv_mendian_info.setText("门店信息（" + card.shops.size() + ")");
		tv_user_comment.setText("用户点评(" + card.comments + ")");
		tv_shop_desc.setText(card.customer_brief);
	}

	 /**
     * @功能描述 : 分享(先选择平台)
     */
    private void openShareBoard() {
        mController.setShareContent("默认内容");
        mController.setShareMedia(mUMImgBitmap);
        mController.openShare(this, false);
    }
    
 // sdk controller
    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);;
    // 布局view
    private View mMainView = null;

    // 要分享的文字内容
    private String mShareContent = "";
    private final SHARE_MEDIA mTestMedia = SHARE_MEDIA.SINA;
    // 要分享的图片
    private UMImage mUMImgBitmap = null;

	private boolean isFav = false;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.bt_chongzhi:
			if(!PreferencesUtils.isLogin(this)) {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				
				/*YouLianHttpApi.applyCard(Global.getUserToken(getApplicationContext())
							, card.card_id, createApplyCardSuccessListener(), createMyReqErrorListener());*/
				
//				if(isApplySue){
//					getError("您已经申领过该会员卡");
//				}else{
					Intent mApplyCardActivity = new Intent(this,
							ApplyCardActivity.class);
					mApplyCardActivity.putExtra("card", card);
					startActivityForResult(mApplyCardActivity, req_apply_card);
//				}
			}
			break;
			
		case R.id.rel_mengdian:// 门店信息
			Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
			intent.putExtra("title", card.card_name);
			intent.putExtra("shops", card.shops);
			startActivity(intent);
			
			break;
		case R.id.rel_user_comment:// 用户点评
			intent = new Intent(getApplicationContext(), CommentActivity.class);
			intent.putExtra("title", card.card_name);
			intent.putExtra("customer_id", card.customer_id);
			startActivity(intent);
			
			
			break;
		case R.id.rel_shop_desc:// 
			intent = new Intent(getApplicationContext(), SellerActivity.class);
			intent.putExtra("title", card.customerName);
			intent.putExtra("url", card.nonactivatedPic);
			intent.putExtra("desc", card.customer_introduce);
			startActivity(intent);
			break;
			
		case R.id.btn_pie:// 敲到
			Intent i = new Intent(getApplicationContext(), CommentAddActivity.class);
			i.putExtra("customer_id", card.customer_id);
			startActivity(i);
			break;
		case R.id.btn_tier:// 分享
			openShareBoard();
			break;
		case R.id.btn_wigame:// 评论
			 intent = new Intent(getApplicationContext(), CommentActivity.class);
			intent.putExtra("title", card.card_name);
			intent.putExtra("customer_id", card.customer_id);
			
			startActivity(intent);
			break;
		case R.id.btn_more:// 收藏
			if(isFav){
				YouLianHttpApi.delFav(Global.getUserToken(getApplicationContext()), card.card_id, "1", createDelFavSuccessListener(), createMyReqErrorListener());
			}else{
				YouLianHttpApi.addFav(Global.getUserToken(getApplicationContext()), card.card_id, "1", createAddFavSuccessListener(), createMyReqErrorListener());
			}
			isFav = !isFav;
			break;
		default:
			break;
		}
	}

	
	
	
	private Response.Listener<String> createGetCardDetailSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							JSONObject jsonObject = o.optJSONObject("result");
							card = Card.parse(jsonObject);
							refreshView();
							
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
	
	

	private Response.Listener<String> createDelFavSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "取消成功",
									Toast.LENGTH_SHORT).show();
							isFav = false;
							mMoreButton.setText("收藏");
							
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
	
	private Response.Listener<String> createAddFavSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "关注成功",
									Toast.LENGTH_SHORT).show();
							isFav = true;
							mMoreButton.setText("已收藏");
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
	
	
	private Response.Listener<String> createApplyCardSuccessListener() {
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
							bt_chongzhi.setText(R.string.already_apply);
							
							Intent intent = new Intent(MemberShipDetail.this, CardListActivity.class);
							startActivity(intent);
							
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == req_apply_card && resultCode == RESULT_OK) {
			bt_chongzhi.setEnabled(false);
			bt_chongzhi.setClickable(false);
			bt_chongzhi.setText(R.string.already_apply);
		}
	}
}
