package com.example.youlian;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.app.MyVolley;
import com.example.youlian.util.ExceptionUtils;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.Utilities;

public class CommentAddActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AllSellerActivity";
	private ImageButton back;
	private TextView tv_title;
	private LinearLayout container_left;
	private LinearLayout container_right;
	
	private ImageButton ib_right;
	private ImageView iv_icon_one;
	private ImageView iv_icon_two;
	private ImageView iv_icon_three;
	private ImageView iv_icon_four;
	private ImageView iv_icon_five;
	private List<ImageView> ivs = new ArrayList<ImageView>();
	private EditText et_content;
	private String customer_id;
//	byte[] data;
	private ImageView iv_pp;
	private ImageView iv_image;
	
	
	public static final int msg_error = 0;
	public static final int msg_success = 1;
	
	private Handler mhHandler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case msg_error:
					Toast.makeText(getApplicationContext(), "请求失败",
							Toast.LENGTH_SHORT).show();
					break;
				case msg_success:
					Toast.makeText(getApplicationContext(), "请求成功",
							Toast.LENGTH_SHORT).show();
					break;
					
				default:
					break;
				}
				
			};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_comment);
		
		initViews();
		
		customer_id = getIntent().getStringExtra("customer_id");
		
		
//		AssetManager am = getAssets();  
//		try {
//			InputStream is = am.open("launcher.png");
//			data = InputStreamToByte(is);
//			Log.i(TAG, "data:" + data.length);
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}  
		
	}
	
	
	private byte[] InputStreamToByte(InputStream is) throws IOException {
		   ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		   int ch;
		   while ((ch = is.read()) != -1) {
		    bytestream.write(ch);
		   }
		   byte imgdata[] = bytestream.toByteArray();
		   bytestream.close();
		   return imgdata;
		  }

	
	private void initViews() {
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		
		ib_right = (ImageButton) this.findViewById(R.id.ib_right);
		ib_right.setBackgroundResource(R.drawable.select_btn_sumbit);
		ib_right.setVisibility(View.VISIBLE);
		ib_right.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.act);
		
		iv_icon_one = (ImageView)this.findViewById(R.id.iv_icon_one);
		iv_icon_two = (ImageView)this.findViewById(R.id.iv_icon_two);
		iv_icon_three = (ImageView)this.findViewById(R.id.iv_icon_three);
		iv_icon_four = (ImageView)this.findViewById(R.id.iv_icon_four);
		iv_icon_five = (ImageView)this.findViewById(R.id.iv_icon_five);
		iv_icon_one.setOnClickListener(this);
		iv_icon_two.setOnClickListener(this);
		iv_icon_three.setOnClickListener(this);
		iv_icon_four.setOnClickListener(this);
		iv_icon_five.setOnClickListener(this);
		ivs.add(iv_icon_one);
		ivs.add(iv_icon_two);
		ivs.add(iv_icon_three);
		ivs.add(iv_icon_four);
		ivs.add(iv_icon_five);
		
		et_content = (EditText)this.findViewById(R.id.et_content);
		
		
		iv_pp = (ImageView)this.findViewById(R.id.iv_pp);
		iv_pp.setOnClickListener(this);
		iv_image = (ImageView)this.findViewById(R.id.iv_image);
		
		
		mRelativeLayoutUpload = (RelativeLayout) this
				.findViewById(R.id.rl_upload);
		mRelativeLayoutUpload.setVisibility(View.GONE);
		mRelativeLayoutUpload.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mRelativeLayoutUpload.setVisibility(View.GONE);
				return true;
			}
		});
		mTakePhoto = (Button) this.findViewById(R.id.bt_pz);
		mPics = (Button) this.findViewById(R.id.bt_photo);
		mCancelUpload = (Button) this.findViewById(R.id.bt_cancel_upload);
		mTakePhoto.setOnClickListener(this);
		mPics.setOnClickListener(this);
		mCancelUpload.setOnClickListener(this);

		findViewById(R.id.upload_container).setOnTouchListener(
				new View.OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						return true;
					}
		});
	}
	
	// 上传
		private RelativeLayout mRelativeLayoutUpload;
		private Button mTakePhoto;
		private Button mPics;
		private Button mCancelUpload;

	private Response.Listener<String> createGetCommentSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "success:" + response);
				mhHandler.sendEmptyMessage(msg_success);
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



	private Response.ErrorListener createGetAdErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mhHandler.sendEmptyMessage(msg_error);
            }
        };
    }
	
	private int star_level;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.ib_right:
			Log.i(TAG, "ddddddddd");
			if(!PreferencesUtils.isLogin(this)) {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else{
				final String content = et_content.getText().toString();
				YouLianHttpApi.comment3(Global.getUserToken(getApplicationContext()), customer_id, content, 
						String.valueOf(star_level), "112.234", "23.234", "2", bytes,
						createGetCommentSuccessListener(), createGetAdErrorListener());
			}
			break;
		case R.id.iv_icon_one:
			star_level = 1;
			refreshStar();
			break;
		case R.id.iv_icon_two:
			star_level = 2;
			refreshStar();
			break;
		case R.id.iv_icon_three:
			star_level = 3;
			refreshStar();
			break;
		case R.id.iv_icon_four:
			star_level = 4;
			refreshStar();
			break;
		case R.id.iv_icon_five:
			star_level = 5;
			refreshStar();
			break;
			
		case R.id.iv_pp:// 上传
			if (mRelativeLayoutUpload.getVisibility() != View.VISIBLE) {
				mRelativeLayoutUpload.setVisibility(View.VISIBLE);
			} else {
				mRelativeLayoutUpload.setVisibility(View.GONE);
			}
			break;
			
		case R.id.bt_pz:// 拍照上传
			uploadByTakePhoto();
			break;
		case R.id.bt_photo:// 照片
			uploadByPics();
			break;
		case R.id.bt_cancel_upload:// 取消上传
			mRelativeLayoutUpload.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	private void refreshStar() {
		for(int i=0; i<5; i++){
			ImageView iv = ivs.get(i);
			if(i<star_level){
				iv.setImageResource(R.drawable.select_btn_star_red_stone);
			}else{
				iv.setImageResource(R.drawable.select_btn_star_huise);
			}
		}
	}
	
	private static final int REQ_CHOOSE_PHOTO = 8;
	private static final int REQ_IMAGE_CAPTURE = 9;
	private Uri mCaptureUri;
	private String upLoadFilePath = "";
	private void uploadByTakePhoto() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			try {
				File uploadFile = new File(
						MyVolley.getCacheDir(getApplicationContext()), "pic_"
								+ String.valueOf(System.currentTimeMillis())
								+ ".jpg");
				mCaptureUri = Uri.fromFile(uploadFile);
				upLoadFilePath = uploadFile.getPath();
				Log.i(TAG, "upLoadFilePath:" + upLoadFilePath);
				Intent intent = Utilities.createPhotoCaptureIntent(mCaptureUri);
				startActivityForResult(intent, REQ_IMAGE_CAPTURE);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(getApplicationContext(), "wy_toast_no_camera",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "无法拍照", Toast.LENGTH_SHORT)
					.show();
		}
	}
	byte[] bytes;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		mTencent.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQ_CHOOSE_PHOTO:
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					iv_image.setImageBitmap(photo);
					iv_image.invalidate();
					bytes = Utilities.bitmap2Bytes(photo);
					Log.i(TAG, "bytes length:" +  bytes.length);
					hideUploadView();
				}
			}
			break;

		case REQ_IMAGE_CAPTURE:
			if (resultCode == RESULT_OK) {
				try {
					Intent intent = new Intent("com.android.camera.action.CROP");
					intent.setDataAndType(mCaptureUri, "image/*");
					intent.putExtra("crop", "true");
					// aspectX aspectY 是宽高的比例
					intent.putExtra("aspectX", 1);
					intent.putExtra("aspectY", 1);
					// outputX outputY 是裁剪图片宽高
					intent.putExtra("outputX", 220);
					intent.putExtra("outputY", 220);
					intent.putExtra("return-data", true);
					startActivityForResult(intent, REQ_CHOOSE_PHOTO);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(getApplicationContext(),
							R.string.no_image_gallery, Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;
		}
	}
	private void uploadByPics() {
		try {
			Intent intent = Utilities.createPhotoPickIntent(220, 220, 1, 1,
					null);
			startActivityForResult(intent, REQ_CHOOSE_PHOTO);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(getApplicationContext(), R.string.no_image_gallery,
					Toast.LENGTH_SHORT).show();
		}
	}


	protected void hideUploadView() {
		if (mRelativeLayoutUpload.getVisibility() == View.VISIBLE) {
			mRelativeLayoutUpload.setVisibility(View.GONE);
		}
	}
}
