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
import com.example.youlian.view.SimpleProgressDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.youlian.utils.zxin.create.BarcodeCreater;
import com.youlian.view.dialog.HuzAlertDialog;

/**
 * 卡面
 * @author raymond
 *
 */
public class CardActivity extends BaseActivity implements OnClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(CardActivity.class.getSimpleName()); 
	
	public static final String DESCRIPTOR = "com.umeng.share";
	
	private static final int BARCODE = 1;
	private static final int QRCODE = 2;
	
	private TextView mNoTextView, mBalanceTextView, mScoreTextView, mValidDateTextView, mWelfareTextView, mCardTypeTextView, mShopDetailTextView;
	private ImageButton mBackButton, mSwitchButton;
	private ImageView mIconImageView, mBarCodeImageView, mQRCodeImageView, mFavImageView; 
	
	private Card mCard, mListCard;
	
	private int mCodeType = QRCODE; //1：一维码  2：二维码 
	
	private CardActivity mContext;
	
	// sdk controller
    private UMSocialService mController = UMServiceFactory.getUMSocialService(DESCRIPTOR, RequestType.SOCIAL);
    //要分享的文字内容
    private String mShareContent = "";
    private final SHARE_MEDIA mTestMedia = SHARE_MEDIA.SINA;
    // 要分享的图片
    private UMImage mUMImgBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		
		mLogger.i("oncreate");
		
		mContext = this;
		
		mListCard = (Card) getIntent().getSerializableExtra("card");
		if(mListCard == null) {
			finish();
			return;
		}
		
		initViews();
		
		SimpleProgressDialog.show(this);
		YouLianHttpApi.getCardDetail(mListCard.card_id, createGetCardDetailSuccessListener(), createGetCardDetailErrorListener());
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
		case QRCODE: //转成一维
			mQRCodeImageView.setVisibility(View.GONE);
			mBarCodeImageView.setVisibility(View.VISIBLE);
			mCodeType = BARCODE;
			break;
		case BARCODE:
			mBarCodeImageView.setVisibility(View.GONE);
			mQRCodeImageView.setVisibility(View.VISIBLE);
			mCodeType = QRCODE;
			break;
		}
	}

	private void edit() {
		if(!"1".equals(mListCard.applyWay)){
			Intent mEditCardActivity = new Intent(this, EditCardActivity.class);
			mEditCardActivity.putExtra("card_id", mListCard.card_id);
			startActivity(mEditCardActivity);
		}else{
			Utils.showToast(this, "您的卡为商家自发,无法编辑");
		}
	}

	private void consume() {
		SimpleProgressDialog.show(this);
		YouLianHttpApi.costCard(mListCard.card_id, costCardSuccessListener(), costCardErrorListener());
	}

	private void remove() {
		if(!"1".equals(mListCard.applyWay)){
			Builder bd = new HuzAlertDialog.Builder(this);
			bd.setTitle("移除");
			bd.setMessage("是否移除卡片");
			bd.setPositiveButton("是", new DialogInterface.OnClickListener() { 
		        public void onClick(DialogInterface d, int which) { 
		        	SimpleProgressDialog.show(mContext);
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
		openShareBoard();
	}

	private void startFav() {
		
	}

	private void startShopDetail() {
		Intent intent = new Intent(this, ShangjiaDetailActivity.class);
		intent.putExtra("customerid", mCard.customer_id);
		startActivity(intent);
	}
	
	 /**
     * @功能描述 : 初始化与SDK相关的成员变量
     */
    private void initConfig() {
        // 要分享的文字内容
        mShareContent = getResources().getString(
                R.string.umeng_socialize_share_content);
        mController.setShareContent("测试内容");

        mUMImgBitmap = new UMImage(getApplicationContext(),
                "http://www.umeng.com/images/pic/banner_module_social.png");
        // mUMImgBitmap = new UMImage(mContext, new
        // File("/mnt/sdcard/DCIM/Camera/1357290284463.jpg"));
        // 设置图片
        // 其他方式构造UMImage
        // UMImage umImage_url = new UMImage(mContext,
        // "http://historyhots.com/uploadfile/2013/0110/20130110064307373.jpg");
        //
        // mUMImgBitmap = new UMImage(mContext, new File(
        // "mnt/sdcard/test.png"));

        UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
        uMusic.setAuthor("zhangliyong");
        uMusic.setTitle("天籁之音");

        UMVideo umVedio = new UMVideo(
                "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
        umVedio.setThumb("http://historyhots.com/uploadfile/2013/0110/20130110064307373.jpg");
        umVedio.setTitle("哇喔喔喔！");

        // 添加新浪和QQ空间的SSO授权支持
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(
                new QZoneSsoHandler(this));
        // 添加腾讯微博SSO支持
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

    }
	
	 /**
     * @功能描述 : 分享(先选择平台)
     */
    private void openShareBoard() {
        mController.setShareContent("默认内容");
        mController.setShareMedia(mUMImgBitmap);
        mController.openShare(this, false);
    }

	private void initViews() {
		mNoTextView = (TextView) findViewById(R.id.tv_title);
		mBackButton = (ImageButton) findViewById(R.id.back);
		mBackButton.setOnClickListener(this);
		mSwitchButton = (ImageButton) findViewById(R.id.ib_switch);
		mSwitchButton.setOnClickListener(this);
		
		mIconImageView = (ImageView) findViewById(R.id.card_iv_icon);
		mQRCodeImageView = (ImageView) findViewById(R.id.card_iv_twodimensioncode);
		mBarCodeImageView = (ImageView) findViewById(R.id.card_iv_onedimensioncode);
		
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
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
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
			
			mQRCodeImageView.setImageBitmap(BarcodeCreater.create2DCode(0 + mCard.card_no + System.currentTimeMillis()));
			mBarCodeImageView.setImageBitmap(
					BarcodeCreater.creatBarcode(this, 
							0 + mCard.card_no + System.currentTimeMillis(),
							20, 50, false));
			mBarCodeImageView.setVisibility(View.GONE);
			
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
            	SimpleProgressDialog.dismiss();
            }
        };
    }
	
	private Response.Listener<String> createRemoneCardSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
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
            	SimpleProgressDialog.dismiss();
            	Utils.showToast(CardActivity.this, "移除成功");
            }
        };
    }
	
	private Response.Listener<String> costCardSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						if("1".equals(object.optString(Constants.key_status))) {
							ArrayList<CardCost> costList = CardCost.getList(object);
							if(Utils.isCollectionNotNull(costList)) {
								showConsumeRecordDialog(costList);
							} else {
								showToast("没有消费记录");
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
            	SimpleProgressDialog.dismiss();
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
