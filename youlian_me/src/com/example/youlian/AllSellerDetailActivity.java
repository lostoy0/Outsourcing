package com.example.youlian;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Act;
import com.example.youlian.mode.Card;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AllSellerDetailActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AllSellerDetailActivity";
	private ImageButton back;
	private TextView tv_title;
	private Act act;
	private ImageView iv;
	private TextView tv_desc;
	private TextView tv_start_time;
	private TextView tv_end_time;
	private TextView tv_eat_num;
	private TextView tv_eat_standard;
	private TextView tv_eat_time;
	private TextView tv_eat_didian;
	private TextView tv_eat_address;
	private TextView tv_act_rule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_seller_activities_detail);
		
		act = (Act) getIntent().getSerializableExtra("act");
		
		initViews();
		
		
//		YouLianHttpApi.getActDetail(act.id, createGetAdSuccessListener(), createGetAdErrorListener());
		refresh();
	}
	
	

	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(act.title);
		
		iv = (ImageView)this.findViewById(R.id.iv);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		
		tv_start_time = (TextView) this.findViewById(R.id.tv_start_time);
		tv_end_time = (TextView) this.findViewById(R.id.tv_end_time);
		tv_eat_num = (TextView) this.findViewById(R.id.tv_eat_num);
		tv_eat_standard = (TextView) this.findViewById(R.id.tv_eat_standard);
		tv_eat_time = (TextView) this.findViewById(R.id.tv_eat_time);
		
		tv_eat_didian = (TextView) this.findViewById(R.id.tv_eat_didian);
		tv_eat_address = (TextView) this.findViewById(R.id.tv_eat_address);
		tv_act_rule = (TextView) this.findViewById(R.id.tv_act_rule);
		
		initShareViews();
	}
	
	private Button mPieButton;
	private Button mTierButton;
	private Button mWiGameButton;
	private Button mMoreButton;
	private boolean isFav = false;
	
	// sdk controller
    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);;
    // 布局view
    private View mMainView = null;

    // 要分享的文字内容
    private String mShareContent = "";
    private final SHARE_MEDIA mTestMedia = SHARE_MEDIA.SINA;
    // 要分享的图片
    private UMImage mUMImgBitmap = null;
    
    
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
	
	
	private void refresh() {
		ImageLoader imageLoader = MyVolley.getImageLoader();
        imageLoader.get(act.pic, 
                       ImageLoader.getImageListener(iv, 
                                                     0, 
                                                     0));
        tv_desc.setText(act.description);
        
        tv_start_time.setText(getString(R.string.start_time, act.startTime));
        tv_end_time.setText(getString(R.string.end_time, act.endTime));
        tv_eat_num.setText(getString(R.string.eat_num, "2"));
        tv_eat_standard.setText(getString(R.string.eat_standard, act.description));
        tv_eat_time.setText(getString(R.string.eat_time, act.description));
        tv_eat_didian.setText(getString(R.string.eat_address, act.description));
        tv_eat_address.setText(getString(R.string.address, act.instruction));
        tv_act_rule.setText(getString(R.string.act_rule, act.instruction));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		
		case R.id.btn_pie:// 敲到
			break;
		case R.id.btn_tier:// 分享
			openShareBoard();
			break;
		case R.id.btn_wigame:// 评论
			Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
			intent.putExtra("quan", 1);
			startActivity(intent);
			break;
		case R.id.btn_more:// 收藏
//			if(isFav){
//				YouLianHttpApi.delFav(Global.getUserToken(getApplicationContext()), quan.fav_id, "2", createDelFavSuccessListener(), createMyReqErrorListener());
//			}else{
//				YouLianHttpApi.addFav(Global.getUserToken(getApplicationContext()), quan.fav_id, "2", createAddFavSuccessListener(), createMyReqErrorListener());
//			}
			isFav = !isFav;
			break;
		default:
			break;
		}
	}
	
	

    /**
     * @功能描述 : 分享(先选择平台)
     */
    private void openShareBoard() {
        mController.setShareContent("默认内容");
        mController.setShareMedia(mUMImgBitmap);
        mController.openShare(this, false);
    }
	
	private Response.Listener<String> createGetAdSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	Log.i(TAG, "success:" + response);
            	
            	
            	if (response != null) {
				try {
					JSONObject o = new JSONObject(response);
					int status = o.optInt("status");
					if(status == 1){
//						JSONArray array = o.optJSONArray("result");
//						int len = array.length();
//						for(int i=0; i<len; i++){
//							JSONObject oo = array.getJSONObject(i);
//							acts.add(Act.parse(oo));
//						}
					}else{
						String msg = o.optString("msg");
						Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
            }
        };
    }
	
	private Response.ErrorListener createGetAdErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Log.i(TAG, "error");
            }
        };
    }
}
