package com.example.youlian.view;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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

public class TemplateOne extends FrameLayout {
	/**
	 * Context
	 */
	private Context mContext = null;
	
	private TextView tv_title;

	private ImageView iv_first;

	SubjectActivity sub;

	public TemplateOne(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public TemplateOne(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public TemplateOne(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) mLayoutInflater.inflate(
				R.layout.templateone, null);

		addView(view);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		iv_first = (ImageView) this.findViewById(R.id.iv_first);
	}
	
	public void setData(SubjectActivity sub){
		if(sub == null) return;
		this.sub = sub;
		List<Pic> pics = sub.pics;
		if(pics != null && pics.size() == 1){
			Pic pic = pics.get(0);
			ImageLoader imageLoader = MyVolley.getImageLoader();
            imageLoader.get(pic.pics, 
                           ImageLoader.getImageListener(iv_first, 
                                                         0, 
                                                         0));
			 
            tv_title.setText(sub.title);
            iv_first.setTag(pic);
            iv_first.setOnClickListener(onClickListener);
		}
		
		
	}
	
	OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Pic pic = (Pic)v.getTag();
			skip(pic);
		}
		
		
	};
	
	private void skip(Pic pic) {
		int type = Integer.parseInt(pic.linkTypes);
		switch (type) {
		case 0:
			Intent in = new Intent(mContext, MemberShipDetail.class);
			in.putExtra("cardid", pic.linkIds);
			mContext.startActivity(in);
			break;
		case 1:
			Intent intent = new Intent(mContext, YouhuiQuanDetail.class);
			intent.putExtra("fav_ent_id", pic.linkIds);
			mContext.startActivity(intent);
			break;
		case 2:
			 in = new Intent(mContext, ShangjiaDetailActivity.class);
			in.putExtra("customerid", pic.linkIds);
			mContext.startActivity(in);
			break;
		case 3:
			Intent i = new Intent(mContext, AllSellerDetailActivity.class);
			i.putExtra("actid", pic.linkIds);
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
