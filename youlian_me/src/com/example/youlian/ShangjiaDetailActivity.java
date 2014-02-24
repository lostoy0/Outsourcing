package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.TabFirstPage.ImageAdapter;
import com.example.youlian.TabFirstPage.ImageAdapter.ViewHolder;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Ad;
import com.example.youlian.mode.Card;
import com.example.youlian.mode.Customer;
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

public class ShangjiaDetailActivity extends Activity implements OnClickListener {
	protected static final String TAG = "ShangjiaDetailActivity";
	private ImageButton back;
	private TextView tv_title;
	private Customer card;
	private ImageView iv_icon;
	private TextView tv_apply;
	private TextView tv_desc;
	private TextView tv_date;
	private Button bt_chongzhi;
	private TextView tv_chongzhi_desc;
	private TextView tv_member_fuli;
	private TextView tv_use_desc;
	
	
	private RelativeLayout rel_mengdian;
	private TextView tv_mendian_info;
	private RelativeLayout rel_user_comment;
	private TextView tv_user_comment;
	private RelativeLayout rel_shop_desc;
	private TextView tv_shop_desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shangjia_detail);

		String customerid = getIntent().getStringExtra("customerid");
		
		initViews();
		
		YouLianHttpApi.getCustomerDetail(
				Global.getUserToken(getApplicationContext()), customerid,
				createDelFavSuccessListener(), createMyReqErrorListener());
	}
	private Gallery image_wall_gallery;
	private ImageAdapter adapter;
	private LinearLayout pointLinear;
	private int indicatorPositon;
	private TextView youhuiquan_huiyuanka;
	private TextView psersonal_xiaofei;
	
	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back); 
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		
		
		
		pointLinear = (LinearLayout) this
				.findViewById(R.id.gallery_point_linear);
		image_wall_gallery = (Gallery)this.findViewById(R.id.image_wall_gallery);
		adapter = new ImageAdapter(getApplicationContext());
		image_wall_gallery.setAdapter(adapter);
		
		image_wall_gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				changePointView(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		youhuiquan_huiyuanka  = (TextView) this.findViewById(R.id.youhuiquan_huiyuanka);
		psersonal_xiaofei  = (TextView) this.findViewById(R.id.psersonal_xiaofei);
		tv_use_desc = (TextView) this.findViewById(R.id.tv_use_desc);
		tv_member_fuli = (TextView) this.findViewById(R.id.tv_member_fuli);
		
		
		
		rel_mengdian = (RelativeLayout) this.findViewById(R.id.rel_mengdian);
		rel_mengdian.setOnClickListener(this);
		tv_mendian_info = (TextView) this.findViewById(R.id.tv_mendian_info);
		rel_user_comment = (RelativeLayout) this
				.findViewById(R.id.rel_user_comment);
		rel_user_comment.setOnClickListener(this);
		tv_user_comment = (TextView) this.findViewById(R.id.tv_user_comment);
		rel_shop_desc = (RelativeLayout) this.findViewById(R.id.rel_shop_desc);
		rel_shop_desc.setOnClickListener(this);
		tv_shop_desc = (TextView) this.findViewById(R.id.tv_shop_desc);
		initShareViews();
	}
	
	
	private boolean isFav = false;
	private Button mPieButton;
	private Button mTierButton;
	private Button mWiGameButton;
	private Button mMoreButton;
	private void initShareViews() {
		mPieButton = (Button) findViewById(R.id.btn_pie);//
		mPieButton.setOnClickListener(this);
		mTierButton = (Button) findViewById(R.id.btn_tier);
		mTierButton.setOnClickListener(this);
		mWiGameButton = (Button) findViewById(R.id.btn_wigame);
		mWiGameButton.setOnClickListener(this);
		mMoreButton = (Button) findViewById(R.id.btn_more);
		mMoreButton.setOnClickListener(this);
	}
	
	protected void refreshView() {
		adapter.add(card.images);
		addPoint(card.images.size());
		
		tv_title.setText(card.name);
		
		StringBuilder sb = new StringBuilder();
		sb.append(card.favEntityCount).append("张优惠券 ")
		.append("  |  ")
		.append(card.favEntityCount).append("张会员卡");
		youhuiquan_huiyuanka.setText(sb.toString());
		
		psersonal_xiaofei.setText(getString(R.string.personal_custom, card.averagePrice));
		tv_use_desc.setText(getString(R.string.liulan_count, card.browseCount));// 浏览次数
		tv_member_fuli.setText(getString(R.string.fav_count, card.favoritesCount));// 收藏人数
		

		tv_mendian_info.setText("门店信息（" + card.shops.size() + ")");
		tv_user_comment.setText("用户点评(" + card.comments + ")");
		tv_shop_desc.setText(card.brief);
	}

	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.rel_mengdian:// 门店信息
			Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
			intent.putExtra("title", card.name);
			intent.putExtra("shops", card.shops);
			startActivity(intent);
			
			break;
		case R.id.rel_user_comment:// 用户点评
			intent = new Intent(getApplicationContext(), CommentActivity.class);
			intent.putExtra("title", card.name);
			intent.putExtra("customer_id", card.id);
			startActivity(intent);
			break;
		case R.id.rel_shop_desc:// 
			intent = new Intent(getApplicationContext(), SellerActivity.class);
			intent.putExtra("title", card);
			intent.putExtra("url", card.logo);
			intent.putExtra("desc", card.brief);
			startActivity(intent);
			break;
			
		case R.id.btn_pie:// 敲到
			break;
		case R.id.btn_tier:// 分享
			openShareBoard();
			break;
		case R.id.btn_wigame:// 评论
			 intent = new Intent(getApplicationContext(), CommentActivity.class);
			intent.putExtra("title", card.name);
			intent.putExtra("customer_id", card.id);
			
			startActivity(intent);
			break;
		case R.id.btn_more:// 收藏
			if(isFav){
				YouLianHttpApi.delFav(Global.getUserToken(getApplicationContext()), card.id, "2", createDelFavSuccessListener(), createMyReqErrorListener());
			}else{
				YouLianHttpApi.addFav(Global.getUserToken(getApplicationContext()), card.id, "2", createAddFavSuccessListener(), createMyReqErrorListener());
			}
			isFav = !isFav;
			break;
		default:
			break;
		}
	}
	
	
	

    // 要分享的文字内容
    private String mShareContent = "";
    private final SHARE_MEDIA mTestMedia = SHARE_MEDIA.SINA;
    // 要分享的图片
    private UMImage mUMImgBitmap = null;
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
    
    
    private Response.Listener<String> createAddFavSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "关注成功",
									Toast.LENGTH_SHORT).show();
							mMoreButton.setText("已收藏");
						} else {
							String msg = o.optString("msg");
							Toast.makeText(getApplicationContext(), msg,
									Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}
    
 // sdk controller
    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);;

	private Response.Listener<String> createDelFavSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							JSONObject jsonObject = o.optJSONObject("result");
							card = Customer.parse(jsonObject);
							refreshView();
							
						} else {
							String msg = o.optString("msg");
							Toast.makeText(getApplicationContext(), msg,
									Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

	
	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, "error");
				Toast.makeText(getApplicationContext(), "请求失败",
						Toast.LENGTH_SHORT).show();
			}
		};
	}
	
	
	class ImageAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<String> ads = new ArrayList<String>();

		public ImageAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return ads.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_gallery, null);
				vh = new ViewHolder();
				vh.imageView = (ImageView) convertView
						.findViewById(R.id.gallery_image);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			setValue(vh, position);
			return convertView;
		}
		
		public void add(List<String> ads){
			this.ads.addAll(ads);
			notifyDataSetChanged();
		}

		private void setValue(ViewHolder vh, int position) {
			String ad = ads.get(position);
			if(!TextUtils.isEmpty(ad)){
				ImageLoader imageLoader = MyVolley.getImageLoader();
	            imageLoader.get(ad, 
	                           ImageLoader.getImageListener(vh.imageView, 
	                                                         R.drawable.guanggao, 
	                                                         R.drawable.guanggao));
			}
			
			changePointView(position);
		}

		protected  class ViewHolder {
			int tag;
			ImageView imageView;
		}
	}
	
	
	public void changePointView(int cur) {
		View view = pointLinear.getChildAt(indicatorPositon);
		View curView = pointLinear.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.screen_indicator_off);
			curPointView.setBackgroundResource(R.drawable.screen_indicator_on);
			indicatorPositon = cur;
		}
	}
	
	private void addPoint(int size) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 0, 0, 0);
		for (int i = 0; i < size; i++) {
			ImageView pointView = new ImageView(
					getApplicationContext());
			pointView.setLayoutParams(params);
			if (i == 0) {
				pointView
						.setBackgroundResource(R.drawable.screen_indicator_on);
			} else
				pointView
						.setBackgroundResource(R.drawable.screen_indicator_off);
			pointLinear.addView(pointView);
		}
	}
}
