package com.example.youlian;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.app.MyVolley;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.RegioninfoVO;
import com.example.youlian.mode.UserInfo;
import com.example.youlian.util.Utilities;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;
import com.example.youlian.view.SimpleProgressDialog;

public class ModifyUserInfoActivity extends BaseActivity implements
		OnClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(ModifyUserInfoActivity.class.getSimpleName());
	
	private static final int REQ_ADDR = 0x1000;
	
	private ImageButton mBackButton;
	private Button mSaveButton;
	private ImageView mIconImageView;
	private TextView mAddrTextView;
	private EditText mNickNameEditText, mPhoneEditText, mEmailEditText;
	private View mModifyIconView, mModifyPassView, mModifyAddrView;
	
	private RegioninfoVO mProvince, mCity, mDistrict;
	
	private UserInfo mUserInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_user_info);
		
		initView();
		
		SimpleProgressDialog.show(this);
		YouLianHttpApi.getUserInfo(Global.getUserToken(this), createGetUserInfoSuccessListener(), createGetUserInfoErrorListener());
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		mBackButton = (ImageButton) findViewById(R.id.back);
		mBackButton.setOnClickListener(this);
		mSaveButton = (Button) findViewById(R.id.modify_btn_save);
		mSaveButton.setOnClickListener(this);
		mIconImageView = (ImageView) findViewById(R.id.modify_iv_icon);
		mNickNameEditText = (EditText) findViewById(R.id.modify_nickname_et);
		mPhoneEditText = (EditText) findViewById(R.id.modify_phone_et);
		mEmailEditText = (EditText) findViewById(R.id.modify_email_et);
		mModifyIconView = findViewById(R.id.modify_icon_panel);
		mModifyIconView.setOnClickListener(this);
		mModifyPassView = findViewById(R.id.modify_pass_panel);
		mModifyPassView.setOnClickListener(this);
		mAddrTextView = (TextView) findViewById(R.id.modify_addr_tv);
		mModifyAddrView = findViewById(R.id.modify_addr_panel);
		mModifyAddrView.setOnClickListener(this);
		
		
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
			
			
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
			
		case R.id.modify_btn_save:
			save();
			break;
			
		case R.id.modify_icon_panel:
			if (mRelativeLayoutUpload.getVisibility() != View.VISIBLE) {
				mRelativeLayoutUpload.setVisibility(View.VISIBLE);
			} else {
				mRelativeLayoutUpload.setVisibility(View.GONE);
			}
			break;
			
		case R.id.modify_addr_panel:
			Intent intent = new Intent(this, AreaProvinceActivity.class);
			startActivityForResult(intent, REQ_ADDR);
			break;
			
		case R.id.modify_pass_panel:
			startActivity(new Intent(this, ModifyPsdActivity.class));
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
		}
	}

	private void save() {
		YouLianHttpApi.updateUserInfo(Global.getUserToken(this), 
				mNickNameEditText.getText().toString().trim(), 
				mNickNameEditText.getText().toString().trim(),  
				mPhoneEditText.getText().toString().trim(), mEmailEditText.getText().toString().trim(), 
						mProvince==null?"":mProvince.areaId, mCity==null?"":mCity.areaId,
								mDistrict==null?"":mDistrict.areaId, 
										createSaveSuccessListener(), createSaveErrorListener());
	}
	
	private Response.Listener<String> createSaveSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
					Utils.showToast(ModifyUserInfoActivity.this, "修改失败");
				} else {
					mLogger.i(response);
					
					try {
						JSONObject object = new JSONObject(response);
						if("1".equals(object.opt(Constants.key_status))) {
							Utils.showToast(ModifyUserInfoActivity.this, "修改成功");
						} else {
							Utils.showToast(ModifyUserInfoActivity.this, "修改失败");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				finish();
			}
		};
	}
	
	private Response.ErrorListener createSaveErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	Utils.showToast(ModifyUserInfoActivity.this, "修改失败");
            	finish();
            }
        };
    }
	
	private Response.Listener<String> createGetUserInfoSuccessListener() {
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
						if("1".equals(object.opt(Constants.key_status))) {
							JSONObject entity = object.optJSONObject(Constants.key_result);
							mUserInfo = UserInfo.from(entity);
							setUserInfo();
						} 
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createGetUserInfoErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	SimpleProgressDialog.dismiss();
            	mLogger.e(error.getMessage());
            }
        };
    }
	
	private void setUserInfo() {
		if(mUserInfo != null) {
			MyVolley.getImageLoader().get(mUserInfo.logo, ImageLoader.getImageListener(mIconImageView, 0, 0));
			mNickNameEditText.setText(mUserInfo.userName);
			mPhoneEditText.setText(mUserInfo.phone);
			mEmailEditText.setText(mUserInfo.email);
			mAddrTextView.setText(mUserInfo.province.areaName + " " + mUserInfo.city.areaName + " " + mUserInfo.district.areaName);
		}
	}
	
	private static final int REQ_CHOOSE_PHOTO = 8;
	private static final int REQ_IMAGE_CAPTURE = 9;

	private static final String TAG = "ModifyUserInfoActivity";
	private Uri mCaptureUri;
	private String upLoadFilePath = "";
	private void uploadByTakePhoto() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			try {
				File uploadFile = new File(
						MyVolley.getCacheDir(getApplicationContext()), "pic_"
								+ String.valueOf(System.currentTimeMillis())
								+ ".png");
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
					mIconImageView.setImageBitmap(photo);
					mIconImageView.invalidate();
					bytes = Utilities.bitmap2Bytes(photo);
					YouLianHttpApi.updateUserIcon(Global.getUserToken(getApplicationContext()), bytes, null, null);
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
			
		case REQ_ADDR:
			if(requestCode == REQ_ADDR && resultCode == RESULT_OK) {
				mProvince = (RegioninfoVO) data.getSerializableExtra(AreaProvinceActivity.key_province);
				mCity = (RegioninfoVO) data.getSerializableExtra(AreaProvinceActivity.key_city);
				mDistrict = (RegioninfoVO) data.getSerializableExtra(AreaProvinceActivity.key_district);
				if(mProvince != null) {
					StringBuilder builder = new StringBuilder();
					builder.append(mProvince.areaName).append(" ").append(mCity==null?"":mCity.areaName).append(" ").append(mDistrict==null?"":mDistrict.areaName);
					if(builder.length()>0) mAddrTextView.setText(builder.toString());
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
