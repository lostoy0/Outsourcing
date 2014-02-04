package com.example.youlian;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.Card;
import com.example.youlian.util.YlLogger;

/**
 * 卡面
 * @author raymond
 *
 */
public class CardActivity extends BaseActivity implements OnClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(CardActivity.class.getSimpleName()); 
	
	private TextView mTitleTextView, mBalanceTextView, mScoreTextView, mValidDateTextView, mWelfareTextView;
	private ImageButton mBackButton, mSwitchButton;
	private ImageView mIconImageView, mTwoDimensionImageView, mFavImageView; 
	
	private String mCardId;
	private Card mCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		
		mLogger.i("oncreate");
		
		mCardId = getIntent().getStringExtra("card_id");
		if(mCardId == null) {
			mLogger.e("card id is null");
//			finish();
//			return;
		}
		
		initViews();
		
//		YouLianHttpApi.getCardDetail(mCardId, createGetCardDetailSuccessListener(), createGetCardDetailErrorListener());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.back:
			mLogger.i("clicked back");
			finish();
			break;
			
		case R.id.ib_switch:
			
			break;
			
		case R.id.card_btn_edit:
			
			break;
			
		case R.id.card_btn_consume:
			
			break;
			
		case R.id.card_btn_remove:
			
			break;
			
		case R.id.card_btn_share:
			
			break;
		}
	}

	private void initViews() {
		mTitleTextView = (TextView) findViewById(R.id.tv_title);
		mBackButton = (ImageButton) findViewById(R.id.back);
		mBackButton.setOnClickListener(this);
		mSwitchButton = (ImageButton) findViewById(R.id.ib_switch);
		mSwitchButton.setOnClickListener(this);
		
		mIconImageView = (ImageView) findViewById(R.id.card_iv_icon);
		mTwoDimensionImageView = (ImageView) findViewById(R.id.card_iv_twodimensioncode);
		mFavImageView = (ImageView) findViewById(R.id.card_iv_fav);
		
		mBalanceTextView = (TextView) findViewById(R.id.card_tv_balance);
		mScoreTextView = (TextView) findViewById(R.id.card_tv_score);
		mValidDateTextView = (TextView) findViewById(R.id.card_tv_valid_date);
		mWelfareTextView = (TextView) findViewById(R.id.card_tv_welfare);
		
		findViewById(R.id.card_btn_edit).setOnClickListener(this);
		findViewById(R.id.card_btn_consume).setOnClickListener(this);
		findViewById(R.id.card_btn_remove).setOnClickListener(this);
		findViewById(R.id.card_btn_share).setOnClickListener(this);
	}
	
	private Response.Listener<String> createGetCardDetailSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						parseDataAndShow(response);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private void parseDataAndShow(String response) throws JSONException {
		JSONObject object = new JSONObject(response);
		if("1".equals(object.opt(Constants.key_status))) {
			 mCard = Card.parse(object.optJSONObject(Constants.key_result));
			 setData();
		} 
	}
	
	private void setData() {
		if(mCard != null) {
//			MyVolley.getImageLoader().get(mInfo.logo, ImageLoader.getImageListener(mIconImageView, 0, 0));
			
			mTitleTextView.setText(mCard.card_no);
			mBalanceTextView.setText(mCard.myMoney + "元");
			mScoreTextView.setText(mCard.myScore);
			mValidDateTextView.setText(mCard.time_from + "至" + mCard.time_to);
			mWelfareTextView.setText(mCard.cluber_welfare);
		}
	}

	private Response.ErrorListener createGetCardDetailErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
	
}
