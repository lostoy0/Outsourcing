package com.example.youlian.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.R;
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
		List<Pic> pics = sub.pics;
		if(pics != null && pics.size() == 1){
			Pic pic = pics.get(0);
			ImageLoader imageLoader = MyVolley.getImageLoader();
            imageLoader.get(pic.pics, 
                           ImageLoader.getImageListener(iv_first, 
                                                         0, 
                                                         0));
			 
            tv_title.setText(sub.title);
		}
	}
}
