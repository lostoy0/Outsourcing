package com.example.youlian;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.adapter.MyCardCostAdapter;
import com.example.youlian.app.MyVolley;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.Card;
import com.example.youlian.mode.CardCost;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;
import com.youlian.utils.zxin.create.BarcodeCreater;
import com.youlian.view.dialog.HuzAlertDialog;

/**
 * 卡面
 * @author raymond
 *
 */
public class CardActivity extends BaseActivity implements OnClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(CardActivity.class.getSimpleName()); 
	
	private TextView mNoTextView, mBalanceTextView, mScoreTextView, mValidDateTextView, mWelfareTextView, mCardTypeTextView, mShopDetailTextView;
	private ImageButton mBackButton, mSwitchButton;
	private ImageView mIconImageView, mOneDimensionImageView, mTwoDimensionImageView, mFavImageView; 
	
	private Card mCard;
	
	private String mCardId;
	private String mCardName;
	private String mCardEntityId;
	private String mApplyWay;
	
	private int mCodeType = 2; //1：一维码  2：二维码 
	
	private CardActivity mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		
		mLogger.i("oncreate");
		
		mContext = this;
		
		mCardId= getIntent().getStringExtra("card_id");
		if(mCardId == null) {
			mLogger.e("card id is null");
			finish();
			return;
		}
		
		mCardName = getIntent().getStringExtra("card_name");
		mCardEntityId = getIntent().getStringExtra("entity_id");
		mApplyWay = getIntent().getStringExtra("applyWay");
		
		initViews();
		
		YouLianHttpApi.getCardDetail(mCardId, createGetCardDetailSuccessListener(), createGetCardDetailErrorListener());
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
			switchCode();
			break;
			
		case R.id.card_btn_edit:
			edit();
			break;
			
		case R.id.card_btn_consume:
			consume();
			break;
			
		case R.id.card_btn_remove:
			remove();
			break;
			
		case R.id.card_btn_share:
			share();
			break;
			
		case R.id.card_iv_fav:
			startFav();
			break;
			
		case R.id.card_tv_shopdetail:
			startShopDetail();
			break;
		}
	}

	private void switchCode() {
		switch (mCodeType) {
		case 2: //转成一维
			mTwoDimensionImageView.setVisibility(View.GONE);
			mOneDimensionImageView.setVisibility(View.VISIBLE);
			mCodeType = 1;
			break;
		case 1:
			mOneDimensionImageView.setVisibility(View.GONE);
			mTwoDimensionImageView.setVisibility(View.VISIBLE);
			mCodeType = 0;
			break;
		}
	}

	private void edit() {
		if(!"1".equals(mApplyWay)){
			Intent mEditCardActivity = new Intent(this, EditCardActivity.class);
			mEditCardActivity.putExtra("card_id", mCardId);
			startActivity(mEditCardActivity);
		}else{
			Utils.showToast(this, "您的卡为商家自发,无法编辑");
		}
	}

	private void consume() {
		YouLianHttpApi.costCard(mCardId, costCardSuccessListener(), costCardErrorListener());
	}

	private void remove() {
		if(!"1".equals(mApplyWay)){
			Builder bd = new HuzAlertDialog.Builder(this);
			bd.setTitle("移除");
			bd.setMessage("是否移除卡片");
			bd.setPositiveButton("是", new DialogInterface.OnClickListener() { 
		        public void onClick(DialogInterface d, int which) { 
		        	YouLianHttpApi.deleteCard(mCard.card_id, "1", createRemoneCardSuccessListener(), createRemoneCardErrorListener());
		        } 
	        });
			bd.setNeutralButton("否", new DialogInterface.OnClickListener() { 
		        public void onClick(DialogInterface d, int which) { 
		            d.dismiss(); 
		        } 
	        });
			bd.show();
		} else {
			Utils.showToast(this, "您的卡为商家自发,无法移除");
		}
	}

	private void share() {
		// TODO Auto-generated method stub
		
	}

	private void startFav() {
		// TODO Auto-generated method stub
		
	}

	private void startShopDetail() {
		// TODO Auto-generated method stub
		
	}

	private void initViews() {
		mNoTextView = (TextView) findViewById(R.id.tv_title);
		mBackButton = (ImageButton) findViewById(R.id.back);
		mBackButton.setOnClickListener(this);
		mSwitchButton = (ImageButton) findViewById(R.id.ib_switch);
		mSwitchButton.setOnClickListener(this);
		
		mIconImageView = (ImageView) findViewById(R.id.card_iv_icon);
		mTwoDimensionImageView = (ImageView) findViewById(R.id.card_iv_twodimensioncode);
		mOneDimensionImageView = (ImageView) findViewById(R.id.card_iv_onedimensioncode);
		
		mFavImageView = (ImageView) findViewById(R.id.card_iv_fav);
		mFavImageView.setOnClickListener(this);
		mShopDetailTextView = (TextView) findViewById(R.id.card_tv_shopdetail);
		mShopDetailTextView.setOnClickListener(this);
		
		mBalanceTextView = (TextView) findViewById(R.id.card_tv_balance);
		mScoreTextView = (TextView) findViewById(R.id.card_tv_score);
		mValidDateTextView = (TextView) findViewById(R.id.card_tv_valid_date);
		mWelfareTextView = (TextView) findViewById(R.id.card_tv_welfare);
		
		mCardTypeTextView = (TextView) findViewById(R.id.card_tv_type);
		
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
			if(!TextUtils.isEmpty(mCard.activatedPic)) {
				MyVolley.getImageLoader().get(mCard.activatedPic, ImageLoader.getImageListener(mIconImageView, 0, 0));
			} else if(!TextUtils.isEmpty(mCard.nonactivatedPic)) {
				MyVolley.getImageLoader().get(mCard.nonactivatedPic, ImageLoader.getImageListener(mIconImageView, 0, 0));
			} else {
				mIconImageView.setImageResource(R.drawable.default_img);
			}
			
			mTwoDimensionImageView.setImageBitmap(BarcodeCreater.create2DCode(0 + mCard.card_no + System.currentTimeMillis()));
			mOneDimensionImageView.setImageBitmap(
					BarcodeCreater.creatBarcode(this, 
							0 + mCard.card_no + System.currentTimeMillis(),
							20, 50, false));
			mOneDimensionImageView.setVisibility(View.GONE);
			
			mNoTextView.setText("NO." + mCard.card_no);
			
			if(TextUtils.isEmpty(mCard.myMoney)) {
				mBalanceTextView.setText("0" + "元");
			} else {
				mBalanceTextView.setText(mCard.myMoney + "元");
			}
			
			if(TextUtils.isEmpty(mCard.myScore)) {
				mScoreTextView.setText("0");
			} else {
				mScoreTextView.setText(mCard.myScore);
			}
			
			mValidDateTextView.setText(mCard.time_from + "至" + mCard.time_to);
			mWelfareTextView.setText(mCard.cluber_welfare);
			
			mCardTypeTextView.setText(mCard.cardLevel);
			
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
	
	private Response.Listener<String> createRemoneCardSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						if(1 == object.optInt(Constants.key_status)) {
							Utils.showToast(CardActivity.this, "移除成功");
							setResult(RESULT_OK, new Intent().putExtra("rm", true));
							finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createRemoneCardErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	Utils.showToast(CardActivity.this, "移除成功");
            }
        };
    }
	
	private Response.Listener<String> costCardSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						if(1 == object.optInt(Constants.key_status)) {
							ArrayList<CardCost> costList = CardCost.getList(object);
							if(Utils.isCollectionNotNull(costList)) {
								showConsumeRecordDialog(costList);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener costCardErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
	
	protected void showConsumeRecordDialog(ArrayList<CardCost> costList) {
		if(!Utils.isCollectionNotNull(costList)) return;
		
		//此处直接new一个Dialog对象出来，在实例化的时候传入主题
        final Dialog dialog = new Dialog(this, R.style.dialog);
        //设置它的ContentView
        View view = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
        .inflate(R.layout.cost_dialog, null);
        ListView mListView = (ListView)view.findViewById(R.id.my_cost_listview);
        ImageButton colse = (ImageButton)view.findViewById(R.id.close);
        colse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				dialog.dismiss();
			}
		});
        if(costList.size()==0){
        	TextView no_cost = (TextView)view.findViewById(R.id.no_cost);
        	mListView.setVisibility(View.GONE);
        	no_cost.setVisibility(View.VISIBLE);
        	 dialog.setContentView(view);
	            dialog.show();
        }else{
        	 MyCardCostAdapter mAdapter = new MyCardCostAdapter(mContext, costList);
	            mListView.setAdapter(mAdapter);
	            dialog.setContentView(view);
	            dialog.show();
        }
	}
	
}
