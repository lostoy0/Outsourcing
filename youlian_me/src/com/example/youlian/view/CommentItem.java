package com.example.youlian.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youlian.AllSellerDetailActivity;
import com.example.youlian.R;
import com.example.youlian.mode.Comment;

public class CommentItem extends FrameLayout {
	/**
	 * Context
	 */
	private Context mContext = null;
	
	private ImageView iv_image;

	private TextView tv_title;
	

	public CommentItem(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public CommentItem(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public CommentItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) mLayoutInflater.inflate(
				R.layout.item_comment, null);

		addView(view);
		iv_image = (ImageView) this.findViewById(R.id.iv_image);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
	}
	
	public void setData(Comment comment){
		if(TextUtils.isEmpty(comment.pic)){
			iv_image.setVisibility(View.GONE);
		}else{
			iv_image.setVisibility(View.VISIBLE);
		}
	}
	
	
	
	
}
