package com.example.youlian.view;

import java.util.ArrayList;
import java.util.List;

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

import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.AllSellerDetailActivity;
import com.example.youlian.R;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Comment;
import com.example.youlian.mode.Pic;

public class CommentItem extends FrameLayout {
	/**
	 * Context
	 */
	private Context mContext = null;
	
	private ImageView iv_image;

	private TextView tv_content;

	private ImageView iv_icon_one;

	private ImageView iv_icon_two;

	private ImageView iv_icon_three;

	private ImageView iv_icon_four;

	private ImageView iv_icon_five;
	private List<ImageView> ivs = new ArrayList<ImageView>();

	private TextView tv_username;

	private TextView tv_date;

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
		
		
		iv_icon_one = (ImageView)view.findViewById(R.id.iv_icon_one);
		iv_icon_two = (ImageView)view.findViewById(R.id.iv_icon_two);
		iv_icon_three = (ImageView)view.findViewById(R.id.iv_icon_three);
		iv_icon_four = (ImageView)view.findViewById(R.id.iv_icon_four);
		iv_icon_five = (ImageView)view.findViewById(R.id.iv_icon_five);
		ivs.add(iv_icon_one);
		ivs.add(iv_icon_two);
		ivs.add(iv_icon_three);
		ivs.add(iv_icon_four);
		ivs.add(iv_icon_five);
		
		iv_image = (ImageView) view.findViewById(R.id.iv_image);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		
		tv_username = (TextView) view.findViewById(R.id.tv_username);
		tv_date = (TextView) view.findViewById(R.id.tv_date);
	}
	
	public void setData(Comment comment){
		int level = (int) Float.parseFloat(comment.starLevel);
		for(int i=0; i<level; i++){
			ivs.get(i).setImageResource(R.drawable.star_red);
		}
		
		if(comment.pic != null){
			Pic p = comment.pic;
			if(TextUtils.isEmpty(p.url)){
				iv_image.setVisibility(View.GONE);
			}else{
				iv_image.setVisibility(View.VISIBLE);
				ImageLoader imageLoader = MyVolley.getImageLoader();
	            imageLoader.get(p.url, 
	                           ImageLoader.getImageListener(iv_image, 
	                                                         0, 
	                                                         0));
			}
		}
		
		tv_username.setText(comment.userName);
		tv_content.setText(comment.content);
		tv_date.setText(comment.addTime);
	}
	
	
	
	
}
