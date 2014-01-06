package com.example.youlian.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.youlian.R;

public class TemplateTwo extends FrameLayout {
	/**
	 * Context
	 */
	private Context mContext = null;
	
	private ImageView iv;
	

	public TemplateTwo(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public TemplateTwo(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public TemplateTwo(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) mLayoutInflater.inflate(
				R.layout.templatetwo, null);

		addView(view);
//		iv = (ImageView) this.findViewById(R.id.iv);
	}
	
	
}
