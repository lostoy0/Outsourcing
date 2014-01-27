package com.example.youlian;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.YouhuiQuan;
import com.umeng.socialize.bean.MultiStatus;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.MulStatusListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;


public class YouhuiQuanDetail extends Activity implements OnClickListener {
	protected static final String TAG = "YouhuiQuanDetail";
	public static final String DESCRIPTOR = "com.umeng.share";
	private ImageButton back;
	private TextView tv_title;

	private YouhuiQuan quan;
	private ImageView iv_guanggao;
	private Button bt_apply;
	private TextView tv_name;
	private TextView tv_shenling;
	private TextView tv_use;
	private TextView tv_use_desc;
	private RelativeLayout rel_mengdian;
	private TextView tv_mendian_info;
	private RelativeLayout rel_user_comment;
	private TextView tv_user_comment;
	private RelativeLayout rel_shop_desc;
	private TextView tv_shop_desc;
	private TextView apply_num;
	
	
	// sdk controller
    private UMSocialService mController = UMServiceFactory.getUMSocialService(DESCRIPTOR, RequestType.SOCIAL);;
    // 布局view
    private View mMainView = null;

    // 要分享的文字内容
    private String mShareContent = "";
    private final SHARE_MEDIA mTestMedia = SHARE_MEDIA.SINA;
    // 要分享的图片
    private UMImage mUMImgBitmap = null;
	
	public List<ImageView> ivs = new ArrayList<ImageView>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_youhuiquan_detail);

		quan = (YouhuiQuan) getIntent().getSerializableExtra("quan");
		String fav_ent_id = "";
		if (quan != null) {
			fav_ent_id = quan.fav_ent_id;
		}

		initViews();
		
		initConfig();

		
		YouLianHttpApi.getYouhuiQuanDetail(Global.getUserToken(getApplicationContext()), fav_ent_id,
				createMyReqSuccessListener(), createMyReqErrorListener());
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

	private Button mPieButton;
	private Button mTierButton;
	private Button mWiGameButton;
	private Button mMoreButton;
	private ImageView iv_one;
	private ImageView iv_two;
	private ImageView iv_three;

	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(quan.fav_ent_name);

		iv_guanggao = (ImageView) this.findViewById(R.id.iv_guanggao);
		bt_apply = (Button) this.findViewById(R.id.bt_apply);
		bt_apply.setOnClickListener(this);

		apply_num = (TextView) this.findViewById(R.id.apply_num);

		tv_name = (TextView) this.findViewById(R.id.tv_name);
		tv_shenling = (TextView) this.findViewById(R.id.tv_shenling);
		tv_use = (TextView) this.findViewById(R.id.tv_use);
		tv_use_desc = (TextView) this.findViewById(R.id.tv_use_desc);

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

		// set tab buttons
		mPieButton = (Button) findViewById(R.id.btn_pie);//
		mPieButton.setOnClickListener(this);

		mTierButton = (Button) findViewById(R.id.btn_tier);
		mTierButton.setOnClickListener(this);

		mWiGameButton = (Button) findViewById(R.id.btn_wigame);
		mWiGameButton.setOnClickListener(this);

		mMoreButton = (Button) findViewById(R.id.btn_more);
		mMoreButton.setOnClickListener(this);
		
		iv_one = (ImageView)findViewById(R.id.iv_one);
		iv_two = (ImageView)findViewById(R.id.iv_two);
		iv_three = (ImageView)findViewById(R.id.iv_three);
		ivs.add(iv_one);
		ivs.add(iv_two);
		ivs.add(iv_three);
	}
	


	protected void refreshView() {
		ImageLoader imageLoader = MyVolley.getImageLoader();
		if (TextUtils.isEmpty(quan.fav_id)) {
			if (quan.nonactivatedPic != null) {
				imageLoader.get(quan.nonactivatedPic, ImageLoader
						.getImageListener(iv_guanggao, R.drawable.guanggao,
								R.drawable.guanggao));
			}
		} else {
			if (quan.activatedPic != null) {
				imageLoader.get(quan.activatedPic, ImageLoader
						.getImageListener(iv_guanggao, R.drawable.guanggao,
								R.drawable.guanggao));
			}
		}
		apply_num.setText(getString(R.string.number_apply_use,
				quan.participate_num));
		tv_name.setText("名称：" + quan.fav_name);
		tv_shenling.setText("申领时间：" + quan.apply_date_from + "至"
				+ quan.apply_date_to);
		tv_use.setText("使用时间：" + quan.use_date_from + "至" + quan.use_date_to);
		tv_use_desc.setText("使用说明：\n" + quan.fav_detail);
		tv_mendian_info.setText("门店信息（" + quan.shops.size() + ")");
		tv_user_comment.setText("用户点评(" + quan.comments + ")");
		tv_shop_desc.setText(quan.customer_brief);
		
		List<YouhuiQuan> list = quan.moreYouHuiquan;
		
		if(list != null){
			int size = list.size();
			Log.i(TAG, "size:" + size);
			for(int i=0; i<size; i++){
				if(i>=3){
					break;
				}
				YouhuiQuan quan = list.get(i);
				if (TextUtils.isEmpty(quan.fav_id)) {
					if (quan.nonactivatedPic != null) {
						imageLoader.get(quan.nonactivatedPic, ImageLoader
								.getImageListener(ivs.get(i), R.drawable.guanggao,
										R.drawable.guanggao));
					}
				} else {
					if (quan.activatedPic != null) {
						imageLoader.get(quan.activatedPic, ImageLoader
								.getImageListener(ivs.get(i), R.drawable.guanggao,
										R.drawable.guanggao));
					}
				}
			}
		}
		
	}
	private boolean isFav = false;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.bt_apply:
			YouLianHttpApi.applyYouhuiQuan(Global.getUserToken(getApplicationContext()), quan.fav_ent_id, createApplyYouhuiQuanSuccessListener(), createMyReqErrorListener());
			break;
		case R.id.btn_pie:// 敲到
			break;
		case R.id.btn_tier:// 分享
			openShareBoard();
			break;
		case R.id.btn_wigame:// 评论
			Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
			intent.putExtra("quan", quan);
			startActivity(intent);
			break;
		case R.id.btn_more:// 收藏
			if(isFav){
				YouLianHttpApi.delFav(Global.getUserToken(getApplicationContext()), quan.fav_id, "2", createDelFavSuccessListener(), createMyReqErrorListener());
			}else{
				YouLianHttpApi.addFav(Global.getUserToken(getApplicationContext()), quan.fav_id, "2", createAddFavSuccessListener(), createMyReqErrorListener());
			}
			isFav = !isFav;
			break;
			
		case R.id.rel_mengdian:// 门店信息
			intent = new Intent(getApplicationContext(), ShopActivity.class);
			intent.putExtra("title", quan.fav_name);
			intent.putExtra("shops", quan.shops);
			startActivity(intent);
			
			break;
		case R.id.rel_user_comment:// 用户点评
			intent = new Intent(getApplicationContext(), CommentActivity.class);
			intent.putExtra("quan", quan);
			startActivity(intent);
			break;
		case R.id.rel_shop_desc:// 
			intent = new Intent(getApplicationContext(), SellerActivity.class);
			intent.putExtra("title", quan.fav_name);
			
			if (TextUtils.isEmpty(quan.fav_id)) {
				if (quan.nonactivatedPic != null) {
					intent.putExtra("url", quan.nonactivatedPic);
				}
			} else {
				if (quan.activatedPic != null) {
					intent.putExtra("url", quan.activatedPic);
				}
			}
			
			intent.putExtra("desc", quan.customer_brief);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}
	

	private Response.Listener<String> createMyReqSuccessListener() {
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
							quan = YouhuiQuan.parse(jsonObject);
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
	
	
	private Response.Listener<String> createApplyYouhuiQuanSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "申请成功",
									Toast.LENGTH_SHORT).show();
							bt_apply.setText("已申请");
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
							Toast.makeText(getApplicationContext(), "关注成功",
									Toast.LENGTH_SHORT).show();
							mMoreButton.setText("收藏");
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
}
