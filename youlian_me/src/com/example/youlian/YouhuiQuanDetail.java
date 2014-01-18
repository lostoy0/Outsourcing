package com.example.youlian;

import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
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
import com.example.youlian.mode.YouhuiQuan;


public class YouhuiQuanDetail extends Activity implements OnClickListener {
	protected static final String TAG = "MembershipActivity";
	public static final String DESCRIPTOR = "com.umeng.share";
	private ImageButton back;
	private TextView tv_title;

	private YouhuiQuan quan;
	private ImageView iv_guanggao;
	private Button bt_apply;
	private TextView tv_name;
	private TextView tv_shenling;
	private TextView tv_use;
	private TextView tv_use_desc;
	private RelativeLayout rel_mengdian;
	private TextView tv_mendian_info;
	private RelativeLayout rel_user_comment;
	private TextView tv_user_comment;
	private RelativeLayout rel_shop_desc;
	private TextView tv_shop_desc;
	private TextView apply_num;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_youhuiquan_detail);

		quan = (YouhuiQuan) getIntent().getSerializableExtra("quan");
		String fav_ent_id = "";
		if (quan != null) {
			fav_ent_id = quan.fav_ent_id;
		}

		initViews();

		YouLianHttpApi.getYouhuiQuanDetail(null, fav_ent_id,
				createMyReqSuccessListener(), createMyReqErrorListener());
	}

	private Button mPieButton;
	private Button mTierButton;
	private Button mWiGameButton;
	private Button mMoreButton;

	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(quan.fav_ent_name);

		iv_guanggao = (ImageView) this.findViewById(R.id.iv_guanggao);
		bt_apply = (Button) this.findViewById(R.id.bt_apply);
		bt_apply.setOnClickListener(this);

		apply_num = (TextView) this.findViewById(R.id.apply_num);

		tv_name = (TextView) this.findViewById(R.id.tv_name);
		tv_shenling = (TextView) this.findViewById(R.id.tv_shenling);
		tv_use = (TextView) this.findViewById(R.id.tv_use);
		tv_use_desc = (TextView) this.findViewById(R.id.tv_use_desc);

		rel_mengdian = (RelativeLayout) this.findViewById(R.id.rel_mengdian);
		tv_mendian_info = (TextView) this.findViewById(R.id.tv_mendian_info);

		rel_user_comment = (RelativeLayout) this
				.findViewById(R.id.rel_user_comment);
		tv_user_comment = (TextView) this.findViewById(R.id.tv_user_comment);

		rel_shop_desc = (RelativeLayout) this.findViewById(R.id.rel_shop_desc);
		tv_shop_desc = (TextView) this.findViewById(R.id.tv_shop_desc);

		// set tab buttons
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
		if (TextUtils.isEmpty(quan.fav_id)) {
			if (quan.nonactivatedPic != null) {
				imageLoader.get(quan.nonactivatedPic, ImageLoader
						.getImageListener(iv_guanggao, R.drawable.guanggao,
								R.drawable.guanggao));
			}
		} else {
			if (quan.activatedPic != null) {
				imageLoader.get(quan.activatedPic, ImageLoader
						.getImageListener(iv_guanggao, R.drawable.guanggao,
								R.drawable.guanggao));
			}
		}
		apply_num.setText(getString(R.string.number_apply_use,
				quan.participate_num));
		tv_name.setText("名称：" + quan.fav_name);
		tv_shenling.setText("申领时间：" + quan.apply_date_from + "至"
				+ quan.apply_date_to);
		tv_use.setText("使用时间：" + quan.use_date_from + "至" + quan.use_date_to);
		tv_use_desc.setText("使用说明：\n" + quan.fav_detail);
		tv_mendian_info.setText("门店信息（" + quan.shops.size() + ")");
		tv_user_comment.setText("用户点评(" + quan.comments + ")");

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.bt_apply:
			break;
		case R.id.btn_pie:// 敲到
			break;
		case R.id.btn_tier:// 分享
			break;
		case R.id.btn_wigame:// 评论
			break;
		case R.id.btn_more:// 收藏
			break;

		default:
			break;
		}
	}
	

	private Response.Listener<String> createMyReqSuccessListener() {
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
							quan = YouhuiQuan.parse(jsonObject);
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
			}
		};
	}
}
