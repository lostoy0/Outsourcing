package com.example.youlian;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Shop;
import com.example.youlian.mode.YouhuiQuan;
import com.example.youlian.util.Utils;

public class SellerActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AllSellerActivity";
	private ImageButton back;
	private TextView tv_title;

	private ImageButton ib_right;
	private LinearLayout branch_shop_lay;
	private TextView tv_desc;
	private ImageView iv_icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_seller);

		
		initViews();

	}

	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);

		tv_title = (TextView) this.findViewById(R.id.tv_title);
		

		iv_icon = (ImageView) this.findViewById(R.id.iv_icon);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		
		String title = getIntent().getStringExtra("title");
		String url = getIntent().getStringExtra("url");
		String desc = getIntent().getStringExtra("desc");
				
		tv_title.setText(title);
		tv_desc.setText(desc);
		ImageLoader imageLoader = MyVolley.getImageLoader();
		imageLoader.get(url, ImageLoader
				.getImageListener(iv_icon, R.drawable.guanggao,
						R.drawable.guanggao));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
}
