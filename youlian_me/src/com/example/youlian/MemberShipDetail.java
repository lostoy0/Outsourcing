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
import com.example.youlian.mode.YouhuiQuan;

public class MemberShipDetail extends Activity implements OnClickListener {
	protected static final String TAG = "MembershipActivity";
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

		card = (Card) getIntent().getSerializableExtra("card");
		
		initViews();
		
		YouLianHttpApi.getMemberCardDetail(
				Global.getUserToken(getApplicationContext()), card.card_id,
				createDelFavSuccessListener(), createMyReqErrorListener());
	}

	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back); 
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(card.card_name);
		
		iv_biaoshi = (ImageView)this.findViewById(R.id.iv_biaoshi);
		iv_icon = (ImageView)this.findViewById(R.id.iv_icon);
		tv_apply  = (TextView) this.findViewById(R.id.tv_apply);
		tv_desc  = (TextView) this.findViewById(R.id.tv_desc);
		tv_date = (TextView) this.findViewById(R.id.tv_date);
		iv_online_chong = (ImageView)this.findViewById(R.id.iv_online_chong);
		tv_left  = (TextView) this.findViewById(R.id.tv_left);
		bt_chongzhi = (Button)this.findViewById(R.id.bt_chongzhi);
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
		
	}
	
	protected void refreshView() {
		ImageLoader imageLoader = MyVolley.getImageLoader();
		imageLoader.get(card.activatedPic, ImageLoader
				.getImageListener(iv_icon, R.drawable.guanggao,
						R.drawable.guanggao));
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

	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.rel_mengdian:// 门店信息
			Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
			intent.putExtra("title", card.card_name);
			intent.putExtra("shops", card.shops);
			startActivity(intent);
			
			break;
		case R.id.rel_user_comment:// 用户点评
			intent = new Intent(getApplicationContext(), CommentActivity.class);
			intent.putExtra("quan", card);
			startActivity(intent);
			break;
		case R.id.rel_shop_desc:// 
			intent = new Intent(getApplicationContext(), SellerActivity.class);
			intent.putExtra("title", card);
			intent.putExtra("url", card.nonactivatedPic);
			intent.putExtra("desc", card.customer_brief);
			startActivity(intent);
			break;
		default:
			break;
		}
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
}
