package com.example.youlian.view;

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
import com.example.youlian.mode.Act;

public class ActRight extends FrameLayout {
	/**
	 * Context
	 */
	private Context mContext = null;
	
	private ImageView iv;

	private TextView tv_title;

	public ActRight(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public ActRight(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public ActRight(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) mLayoutInflater.inflate(
				R.layout.item_act_right, null);

		addView(view);
		iv = (ImageView) this.findViewById(R.id.iv_icon);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
	}
	
	public void setData(Act act){
		ImageLoader imageLoader = MyVolley.getImageLoader();
        imageLoader.get(act.pic, 
                       ImageLoader.getImageListener(iv, 
                                                     0, 
                                                     0));
        tv_title.setText(act.title);
	}
}
