package com.example.youlian;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.CardInfo;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;

public class EditCardActivity extends BaseActivity implements OnClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(EditCardActivity.class
			.getSimpleName());

	private Context mContext;

	private ImageButton mBackButton;
	private Button mSexButton, mSaveButton;
	private EditText mNameEditText, mPhoneEditText, mEmailEditText, mQQEditText, mUserIdCardNOEditText;
	private TextView mDateTextView;
	private RelativeLayout mDateRelativeLayout;

	private CardInfo mCardInfo;

	private String card_id;
	private String memberName, memberSex = "0", memberBirth, memberPhone,
			memberEmail, memberAddress, memberQq, membeIdCard, isAllowPush;

	private int sexCheck = 0;

	private String[] sexStrs = new String[] { "女", "男" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_card);

		mContext = this;

		card_id = getIntent().getStringExtra("card_id");

		initView();
		initListener();

		YouLianHttpApi.getCardInfo(card_id, getCardInfoSuccessListener(),
				getCardInfoErrorListener());
	}

	private void initView() {
		mBackButton = (ImageButton) findViewById(R.id.back);
		
		mNameEditText = (EditText) findViewById(R.id.name);
		mPhoneEditText = (EditText) findViewById(R.id.call);
		mUserIdCardNOEditText = (EditText) findViewById(R.id.user_id);
		mEmailEditText = (EditText) findViewById(R.id.email);
		mQQEditText = (EditText) findViewById(R.id.qq);
		mSexButton = (Button) findViewById(R.id.sex);
		mSaveButton = (Button) findViewById(R.id.save);
		mDateTextView = (TextView) findViewById(R.id.date);
		mDateRelativeLayout = (RelativeLayout) findViewById(R.id.date_rey);
		
		mSexButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(mContext)
						.setTitle("请选择")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setSingleChoiceItems(sexStrs,
								Integer.parseInt(memberSex),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichcountry) {
										sexCheck = whichcountry;
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface d,
											int which) {
										d.dismiss();
									}
								})
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface d,
											int which) {
										memberSex = String.valueOf(sexCheck);
										mSexButton.setText(sexStrs[sexCheck]);
										d.dismiss();
									}
								}).show();

			}
		});
		
		mDateRelativeLayout.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// 显示时间对话框
				showDialog(DATE_DIALOG_ID);

			}
		});
		
		// 得到当前的时间
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
	}

	private void initListener() {
		mBackButton.setOnClickListener(this);
		mSaveButton.setOnClickListener(this);
	}

	private void initCardInfo() {
		if(!TextUtils.isEmpty(mCardInfo.memberName)) mNameEditText.setText(mCardInfo.memberName);
		if(!TextUtils.isEmpty(mCardInfo.memberPhone)) mPhoneEditText.setText(mCardInfo.memberPhone);
		if(!TextUtils.isEmpty(mCardInfo.membeIdCard)) mUserIdCardNOEditText.setText(mCardInfo.membeIdCard);
		if(!TextUtils.isEmpty(mCardInfo.memberEmail)) mEmailEditText.setText(mCardInfo.memberEmail);
		if(!TextUtils.isEmpty(mCardInfo.memberQq)) mQQEditText.setText(mCardInfo.memberQq);
		mSexButton.setText(sexStrs[mCardInfo.memberSex]);
		if(!TextUtils.isEmpty(mCardInfo.memberBirth)) mDateTextView.setText(mCardInfo.memberBirth);
		
		if(!TextUtils.isEmpty(mCardInfo.memberAddress)) memberAddress = mCardInfo.memberAddress;
		else memberAddress = "";
		isAllowPush = mCardInfo.isAllowPush + "";
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.back:
			finish();

			break;
		case R.id.save:
			saveInfo();
			break;
		}
	}
	
	private void saveInfo() {
		memberName = mNameEditText.getText().toString();
		memberPhone = mPhoneEditText.getText().toString();
		membeIdCard = mUserIdCardNOEditText.getText().toString();
		memberEmail = mEmailEditText.getText().toString();
		memberQq = mQQEditText.getText().toString();
		memberBirth = mDateTextView.getText().toString();
		if (memberPhone != null && memberPhone.length() != 11) {
			Utils.showToast(mContext, "您输入的手机号码不正确");
			return;
		}
		if (membeIdCard != null && membeIdCard.length() != 18) {
			Utils.showToast(mContext, "您输入的身份证号码不正确");
			return;
		}
		
		YouLianHttpApi.editCardInfo(card_id, memberName, memberSex, memberBirth, memberPhone, 
				memberEmail, memberAddress, memberQq, membeIdCard, isAllowPush, 
						editCardInfoSuccessListener(), editCardInfoErrorListener());
	}

	/*
	 * 定义显示时间的组件
	 */
	// 记录年，月，日
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 0;
	/*
	 * 定义时间显示监听器
	 */
	private DatePickerDialog.OnDateSetListener mSetDateListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int myear, int monthOfYear,
				int dayOfMonth) {
			// 进行时间显示的更新
			year = myear;
			month = monthOfYear;
			day = dayOfMonth;
			// 更新时间的显示
			updateDisplay();
		}
	};

	/**
	 * 当显示时间窗口被创建时调用
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mSetDateListener, year, month,
					day);
		}
		return null;
	}

	/**
	 * 更新时间显示器
	 */
	private void updateDisplay() {
		mDateTextView.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(year).append("-").append(month + 1).append("-")
				.append(day).append(" "));

	}
 
	private Response.Listener<String> getCardInfoSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);

					try {
						JSONObject object = new JSONObject(response);
						if (1 == object.optInt(Constants.key_status)) {
							CardInfo info = CardInfo.from(object.optJSONObject(Constants.key_result));
							if(!Utils.isNull(info)) {
								mCardInfo = info;
								initCardInfo();
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	private Response.ErrorListener getCardInfoErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				mLogger.e(error.getMessage());
			}
		};
	}
	
	private Response.Listener<String> editCardInfoSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);

					try {
						JSONObject object = new JSONObject(response);
						if (1 == object.optInt(Constants.key_status)) {
							Utils.showToast(mContext, "编辑会员卡资料成功");
						} else {
							Utils.showToast(mContext, "编辑会员卡资料失败");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	private Response.ErrorListener editCardInfoErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				mLogger.e(error.getMessage());
				Utils.showToast(mContext, "编辑会员卡资料失败");
			}
		};
	}

}
