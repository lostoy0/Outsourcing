package com.example.youlian.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.R;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Pic;

public class TemplateOne extends FrameLayout {
	/**
	 * Context
	 */
	private Context mContext = null;
	
	private ImageView iv;
	

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
		iv = (ImageView) this.findViewById(R.id.iv);
	}
	
	public void setData(List<Pic> pics){
		if(pics == null) return;
		if(pics.size()>0){
			Pic pic = pics.get(0);
			ImageLoader imageLoader = MyVolley.getImageLoader();
            imageLoader.get(pic.pics, 
                           ImageLoader.getImageListener(iv, 
                                                         R.drawable.guanggao, 
                                                         R.drawable.guanggao));
			 
			 
		}
	}
}
