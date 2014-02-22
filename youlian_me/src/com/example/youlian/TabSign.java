package com.example.youlian;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.youlian.fragment.ActivityFragment;


public class TabSign extends FragmentActivity {
	
	public static final String KEY_TYPE = "type";
	public static final int TYPE_SUBJECT = 0;
	public static final int TYPE_SIGN = TYPE_SUBJECT + 1;
	
	private RadioGroup mRadioGroup;
	private RadioButton mSubjectRadioButton, mSignRadioButton;
	private ImageView mCursor;// 动画图片
	private ViewPager mPager;
	private ActPagerAdapter mAdapter;
	
	private int mOffset = 0;// 动画图片偏移量
	private int mCurrentIndex = 0;// 当前页卡编号
	private int mCursorWidth;// 动画图片宽度
	private int mScreenWidth;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tab_tier);
		
		initViews();
		
	}

	private void initViews() {
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
		
		mSubjectRadioButton = (RadioButton) findViewById(R.id.radio_subject);
		mSignRadioButton = (RadioButton) findViewById(R.id.radio_sign);
		
		initAnimationParams();
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setOnPageChangeListener(mOnPageChangeListener);
		mAdapter = new ActPagerAdapter(getSupportFragmentManager(), this);
		mPager.setAdapter(mAdapter);
		mPager.setCurrentItem(1);
	}
	
	private void initAnimationParams() {
		mCursor = (ImageView) findViewById(R.id.cursor);
		mCursorWidth = BitmapFactory.decodeResource(getResources(), R.drawable.second_goto).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;// 获取分辨率宽度
		mOffset = (mScreenWidth / 2 - mCursorWidth) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(mOffset, 0);
		mCursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if(checkedId == R.id.radio_subject) {
				mPager.setCurrentItem(0);
			} else if(checkedId == R.id.radio_sign) {
				mPager.setCurrentItem(1);
			}
		}
	};
	
	private void selected(int position) {
		if(position == 0) {
			mSubjectRadioButton.setTextColor(getResources().getColor(R.color.red));
			mSignRadioButton.setTextColor(getResources().getColor(R.color.blue));
			mSubjectRadioButton.setEnabled(false);
			mSignRadioButton.setEnabled(true);
		} else if(position == 1) {
			mSignRadioButton.setTextColor(getResources().getColor(R.color.red));
			mSubjectRadioButton.setTextColor(getResources().getColor(R.color.blue));
			mSignRadioButton.setEnabled(false);
			mSubjectRadioButton.setEnabled(true);
		}
	}
	
	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			selected(arg0);
			
			Animation animation = null;
			if(arg0 == 0) {
				if(mCurrentIndex == 1) {
					animation = new TranslateAnimation(mScreenWidth/2, 0, 0, 0);
				}
			} else if(arg0 == 1) {
				if(mCurrentIndex == 0) {
					animation = new TranslateAnimation(0, mScreenWidth/2, 0, 0);
				}
			}
			
			mCurrentIndex = arg0;
			if(animation != null) {
				animation.setFillAfter(true);// True:图片停在动画结束位置
				animation.setDuration(200);
				mCursor.startAnimation(animation);
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};

	private final class ActPagerAdapter extends FragmentPagerAdapter {
		private Context mContext;

		public ActPagerAdapter(FragmentManager fm, Context context) {
			super(fm);
			mContext = context;
		}

		@Override
		public Fragment getItem(int position) {
			Bundle bundle = new Bundle();
			bundle.putInt(KEY_TYPE, position);
			return Fragment.instantiate(mContext, ActivityFragment.class.getName(), bundle);
		}

		@Override
		public int getCount() {
			return 2;
		}
		
	}
	
}
