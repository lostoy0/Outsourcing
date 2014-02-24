package com.example.youlian.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.AllSellerDetailActivity;
import com.example.youlian.MemberShipDetail;
import com.example.youlian.R;
import com.example.youlian.ShangjiaDetailActivity;
import com.example.youlian.WebViewActivityByMe;
import com.example.youlian.YouhuiQuanDetail;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Pic;
import com.example.youlian.mode.SubjectActivity;

public class TemplateFour extends FrameLayout {
	protected static final String TAG = "TemplateFive";
	/**
	 * Context
	 */
	private Context mContext = null;

	private TextView tv_title;

	private ImageView iv_first;

	private ImageView iv_second;

	private ImageView iv_third;
	
	private ImageView iv_fourth;

	private List<ImageView> ivs = new ArrayList<ImageView>();

	public TemplateFour(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public TemplateFour(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public TemplateFour(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) mLayoutInflater.inflate(R.layout.templatefour, null);

		addView(view);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		iv_first = (ImageView) this.findViewById(R.id.iv_first);
		iv_second = (ImageView) this.findViewById(R.id.iv_second);
		iv_third = (ImageView) this.findViewById(R.id.iv_third);
		iv_fourth = (ImageView) this.findViewById(R.id.iv_fourth);

		ivs.add(iv_first);
		ivs.add(iv_second);
		ivs.add(iv_third);
		ivs.add(iv_fourth);
	}

	public void setData(SubjectActivity subjectActivity) {
		if (subjectActivity == null)
			return;
		tv_title.setText(subjectActivity.title);
		List<Pic> pics = subjectActivity.pics;
		ImageLoader imageLoader = MyVolley.getImageLoader();
		if (pics != null && pics.size() == 4) {
			int size = pics.size();
			for (int i = 0; i < size; i++) {
				Pic p = pics.get(i);
				ImageView iv = ivs.get(i);
				imageLoader.get(p.pics, ImageLoader.getImageListener(
						iv, R.drawable.guanggao, R.drawable.guanggao));
				iv.setTag(p);
				iv.setOnClickListener(clickListener);
			}
		}

	}
	
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Pic p = (Pic)v.getTag();
			if(p != null){
				Log.i(TAG, "p:" + p);
				skip(p);
			}
		}
	};
	private void skip(Pic pic) {
		int type = Integer.parseInt(pic.linkTypes);
		switch (type) {
		case 0:
			Intent in = new Intent(mContext, MemberShipDetail.class);
			in.putExtra("cardid", pic.linkIds);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(in);
			break;
		case 1:
			in = new Intent(mContext, YouhuiQuanDetail.class);
			in.putExtra("fav_ent_id", pic.linkIds);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(in);
			break;
		case 2:
			 in = new Intent(mContext, ShangjiaDetailActivity.class);
			in.putExtra("customerid", pic.linkIds);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(in);
			break;
		case 3:
			Intent i = new Intent(mContext, AllSellerDetailActivity.class);
			i.putExtra("actid", pic.linkIds);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(i);
			break;
		case 4:// 站外
			in = new Intent(mContext, WebViewActivityByMe.class);
			in.putExtra(WebViewActivityByMe.URL, pic.linkIds);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(in);
			break;

		default:
			break;
		}
	}
}
