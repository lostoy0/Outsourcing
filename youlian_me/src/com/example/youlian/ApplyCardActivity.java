package com.example.youlian;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.app.MyVolley;
import com.example.youlian.common.Configure;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.ApplyCardResult;
import com.example.youlian.mode.Card;
import com.example.youlian.mode.RegioninfoVO;
import com.example.youlian.mode.UserInfo;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.Utils;
import com.example.youlian.util.YlLogger;
import com.example.youlian.view.MySlipSwitch;
import com.example.youlian.view.MySlipSwitch.OnSwitchListener;
import com.example.youlian.view.SimpleProgressDialog;
import com.youlian.view.dialog.HuzAlertDialog;

public class ApplyCardActivity extends BaseActivity implements OnClickListener {
	private YlLogger mLogger = YlLogger.getLogger(ApplyCardActivity.class.getSimpleName());

	private Context mContext;

	private ImageButton backBtn;

	private static final int APPLY_CARD = 11, GET_USER_INFO = 12,
			GET_AREAS = 13;

	private ImageView imgView;

	private TextView card_name, apply_data_text;

	private LinearLayout apply_data_text_lay, apply_qq_text_lay, apply_id_lay,
			apply_email_lay, address_lay;

	private LinearLayout grey_line_a, grey_line_b, grey_line_c, grey_line_h,
			grey_line_i, grey_line_e, grey_line_f;

	private EditText apply_name, apply_call, apply_qq_text, apply_id,
			apply_email, address;

	// private TextView user_provinec,user_city,user_district;
	//
	// private String
	// province_id,city_id,district_id,userProvinec,userCity,userDistrict;

	private Button apply_sumbit, sex;

	private Card card;

	private String input_fileds;

	private String member_name, phone, birthday, qq, idcard, email,
			is_allow_push = "1", member_sex = "1", address_str;

	private String[] inputFiles;

	private ImageLoader imageLoader;

	private ApplyCardResult cardResult;

	private int sexCheck = 0;

	private String[] sexStrs = new String[] { "女", "男" };

	private ArrayList<RegioninfoVO> areaList;

	// 1 省份 2 市区 3地区
	private int clickType = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_card);
		mContext = this;
		imageLoader = MyVolley.getImageLoader();
		card = (Card) getIntent().getSerializableExtra("card");
		if (card != null)
			input_fileds = card.input_fileds;
		if (Utils.notNull(input_fileds)) {
			inputFiles = input_fileds.split(",");
		}
		System.out.println("input_fileds:" + input_fileds);
		initView();
		initListener();

		loadData();
	}

	private void loadData() {
		SimpleProgressDialog.show(this);
		YouLianHttpApi.getUserInfo(Global.getUserToken(this), createGetUserInfoSuccessListener(), createGetUserInfoErrorListener());
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		backBtn = (ImageButton) findViewById(R.id.back);
		apply_sumbit = (Button) findViewById(R.id.apply_sumbit);
		card_name = (TextView) findViewById(R.id.card_name);
		sex = (Button) findViewById(R.id.sex);
		apply_name = (EditText) findViewById(R.id.apply_name);
		apply_call = (EditText) findViewById(R.id.apply_call);
		apply_data_text = (TextView) findViewById(R.id.apply_data_text);
		apply_qq_text = (EditText) findViewById(R.id.apply_qq_text);
		address = (EditText) findViewById(R.id.address);
		// apply_address_text = (EditText)findViewById(R.id.apply_address_text);
		apply_id = (EditText) findViewById(R.id.apply_id);
		apply_email = (EditText) findViewById(R.id.apply_email);

		// user_provinec = (TextView)findViewById(R.id.user_provinec);
		// user_city = (TextView)findViewById(R.id.user_city);
		// user_district = (TextView)findViewById(R.id.user_district);

		// apply_name_lay = (LinearLayout)findViewById(R.id.apply_name_lay);
		// apply_call_lay = (LinearLayout)findViewById(R.id.apply_call_lay);
		apply_data_text_lay = (LinearLayout) findViewById(R.id.apply_data_text_lay);
		apply_qq_text_lay = (LinearLayout) findViewById(R.id.apply_qq_text_lay);
		// apply_address_text_lay =
		// (LinearLayout)findViewById(R.id.apply_address_text_lay);
		apply_id_lay = (LinearLayout) findViewById(R.id.apply_id_lay);
		apply_email_lay = (LinearLayout) findViewById(R.id.apply_email_lay);

		address_lay = (LinearLayout) findViewById(R.id.address_lay);
		// city_lay = (LinearLayout)findViewById(R.id.city_lay);
		// district_lay = (LinearLayout)findViewById(R.id.district_lay);

		grey_line_a = (LinearLayout) findViewById(R.id.grey_line_a);
		grey_line_b = (LinearLayout) findViewById(R.id.grey_line_b);
		grey_line_c = (LinearLayout) findViewById(R.id.grey_line_c);
		grey_line_e = (LinearLayout) findViewById(R.id.grey_line_e);
		grey_line_f = (LinearLayout) findViewById(R.id.grey_line_f);
		// grey_line_g = (LinearLayout)findViewById(R.id.grey_line_g);
		grey_line_h = (LinearLayout) findViewById(R.id.grey_line_h);

		grey_line_i = (LinearLayout) findViewById(R.id.grey_line_i);

		if (inputFiles != null && inputFiles.length > 0) {
			for (int i = 0; i < inputFiles.length; i++) {
				String inputStr = inputFiles[i];
				System.out.println("inputstr:" + inputStr);
				if (Utils.notNull(inputStr)) {
					if ("email".equals(inputStr)) {
						apply_email_lay.setVisibility(View.VISIBLE);
						grey_line_c.setVisibility(View.VISIBLE);
					} else if ("birthday".equals(inputStr)) {
						apply_data_text_lay.setVisibility(View.VISIBLE);
						grey_line_e.setVisibility(View.VISIBLE);
					} else if ("qq".equals(inputStr)) {
						apply_qq_text_lay.setVisibility(View.VISIBLE);
						grey_line_f.setVisibility(View.VISIBLE);
					} else if ("address".equals(inputStr)) {
						address_lay.setVisibility(View.VISIBLE);
						// city_lay.setVisibility(View.VISIBLE);
						// district_lay.setVisibility(View.VISIBLE);
						grey_line_i.setVisibility(View.VISIBLE);
					} else if ("idcard".equals(inputStr)) {
						apply_id_lay.setVisibility(View.VISIBLE);
						grey_line_h.setVisibility(View.VISIBLE);
					}
				}
			}
		}

		card_name.setText(card.card_name);
		imgView = (ImageView) findViewById(R.id.card_img);
		String fileUrl = card.activatedPic;
		if (Utils.notNull(fileUrl)) {
			imageLoader.get(fileUrl, ImageLoader.getImageListener(imgView,
					R.drawable.default_img, 0));
		}

		MySlipSwitch myBtn = (MySlipSwitch) findViewById(R.id.msg_slipBtn);// 获得指定控件

		myBtn.setImageResource(R.drawable.open, R.drawable.close,
				R.drawable.slipping);
		PreferencesUtils.addConfigInfo(mContext, Configure.SESSION_USER_IS_MSG,
				true);
		if (PreferencesUtils.getBooleanByKey(mContext,
				Configure.SESSION_USER_IS_MSG)) {
			myBtn.setSwitchState(true, true);
		} else {
			myBtn.setSwitchState(false, true);
		}
		myBtn.setOnSwitchListener(new OnSwitchListener() {
			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				if (isSwitchOn) {
					System.out.println("开");
					is_allow_push = "1";
					PreferencesUtils.addConfigInfo(mContext,
							Configure.SESSION_USER_IS_MSG, true);
				} else {
					System.out.println("关");
					is_allow_push = "0";
					PreferencesUtils.addConfigInfo(mContext,
							Configure.SESSION_USER_IS_MSG, false);

				}
			}
		});
		sex.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Builder bd = new HuzAlertDialog.Builder(mContext);
				bd.setTitle("请选择");
				bd.setSingleChoiceItems(sexStrs, Integer.parseInt(member_sex),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichcountry) {
								sexCheck = whichcountry;
							}
						});
				bd.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface d, int which) {
								System.out.println("选择:" + sexCheck);
								member_sex = String.valueOf(sexCheck);
								sex.setText(sexStrs[sexCheck]);
								d.dismiss();
							}
						});
				bd.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface d, int which) {
								d.dismiss();
							}
						});
				bd.show();

			}
		});
		apply_data_text_lay.setOnClickListener(new OnClickListener() {

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

	/**
	 * 事件监听
	 */
	private void initListener() {
		backBtn.setOnClickListener(this);
		// address_lay.setOnClickListener(this);
		// city_lay.setOnClickListener(this);
		// district_lay.setOnClickListener(this);
		apply_sumbit.setOnClickListener(this);
	}

	private void getError(String msg) {
		Toast toast = Toast.makeText(getApplicationContext(), msg,
				Toast.LENGTH_LONG);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	}

	private void initArea() {
		final int size = areaList.size();
		String areas[] = new String[size];
		for (int i = 0; i < size; i++) {
			areas[i] = areaList.get(i).areaName;
		}
		// showDialog(areas);
	}

	// private void showDialog(final String areas[]){
	// System.out.println("showDialog");
	// System.out.println("areas大小："+areas.length);
	// final int size = areaList.size();
	//
	// Builder bd = new HuzAlertDialog.Builder(this);
	// bd.setTitle("请选择")
	// .setItems(areas, new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// switch (clickType) {
	// case 1:
	// if(which<size){
	// user_provinec.setText(areas[which]);
	// user_city.setText("");
	// user_district.setText("");
	// province_id = areaList.get(which).getAreaId();
	// userProvinec = province_id;
	// }
	// break;
	// case 2:
	// if(which<size){
	// user_city.setText(areas[which]);
	// user_district.setText("");
	// city_id = areaList.get(which).getAreaId();
	// userCity = city_id;
	// }
	// break;
	// case 3:
	// if(which<size){
	// user_district.setText(areas[which]);
	// district_id = areaList.get(which).getAreaId();
	// userDistrict =district_id;
	// }
	// break;
	// }
	// dialog.dismiss();
	// }
	// }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// }
	// }).show();
	// }
	/**
	 * @author simon
	 * @pyoulian
	 * @version 1.0
	 * @Data 上午11:02:32
	 */

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();

			break;
		case R.id.apply_sumbit:
			member_name = apply_name.getText().toString();
			if (Utils.isNull(member_name)) {
				getError("必填信息未填写完整");
				return;
			}
			phone = apply_call.getText().toString();
			if (Utils.isNull(phone)) {
				getError("必填信息未填写完整");
				return;
			}

			if (apply_data_text_lay.getVisibility() == View.VISIBLE) {
				birthday = apply_data_text.getText().toString();
				if (Utils.isNull(birthday)) {
					getError("必填信息未填写完整");
					return;
				}
			}
			if (apply_qq_text_lay.getVisibility() == View.VISIBLE) {
				qq = apply_qq_text.getText().toString();
				if (Utils.isNull(qq)) {
					getError("必填信息未填写完整");
					return;
				}
			}
			if (apply_id_lay.getVisibility() == View.VISIBLE) {
				idcard = apply_id.getText().toString();
				if (Utils.isNull(idcard)) {
					getError("必填信息未填写完整");
					return;
				} else if (Utils.isNull(idcard) && idcard.length() != 18) {
					getError("您的身份证位数不正确");
					return;
				}

			}
			if (address_lay.getVisibility() == View.VISIBLE) {
				address_str = address.getText().toString();
				if (Utils.isNull(address_str)) {
					getError("必填信息未填写完整");
					return;
				}
			}
			if (apply_email_lay.getVisibility() == View.VISIBLE) {
				email = apply_email.getText().toString();
				if (Utils.isNull(email)) {
					getError("必填信息未填写完整");
					return;
				}
			}
			// if(provinec_lay.getVisibility() == View.VISIBLE){
			// province_id = user_provinec.getText().toString();
			// if(Utils.isNull(idcard)){
			// getError("必填信息未填写完整");
			// return;
			// }
			// }
			// if(city_lay.getVisibility() == View.VISIBLE){
			// city_id = user_city.getText().toString();
			// if(Utils.isNull(idcard)){
			// getError("必填信息未填写完整");
			// return;
			// }
			// }
			// if(district_lay.getVisibility() == View.VISIBLE){
			// district_id = user_district.getText().toString();
			// if(Utils.isNull(idcard)){
			// getError("必填信息未填写完整");
			// return;
			// }
			// }
			
			SimpleProgressDialog.show(this);
			YouLianHttpApi.applyCard(Global.getUserToken(this), card.card_id, email, qq, idcard, 
					birthday, member_name, member_sex, phone, is_allow_push, 
					createApplyCardSuccessListener(), createMyReqErrorListener());
			
			break;
		// case R.id.provinec_lay:
		// clickType =1;
		// province_id = "";
		// city_id ="";
		// district_id="";
		// synConnection(GET_AREAS);
		// break;
		// case R.id.city_lay:
		// String provinec = user_provinec.getText().toString();
		// if(Utils.notNull(provinec)){
		// clickType =2;
		// city_id ="";
		// district_id="";
		// synConnection(GET_AREAS);
		// }else{
		// getError("请先选择省份");
		// }
		// break;
		// case R.id.district_lay:
		// String city = user_city.getText().toString();
		// if(Utils.notNull(city)){
		// clickType =3;
		// district_id="";
		// synConnection(GET_AREAS);
		// }else{
		// getError("请先选择市区");
		// }
		// break;

		}
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
		apply_data_text.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(year).append("-").append(month + 1).append("-")
				.append(day).append(" "));

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
							UserInfo info = UserInfo.from(entity);
							setUserInfo(info);
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
	
	private void setUserInfo(UserInfo info) {
		if(info != null) {
			//用户已有的资料
			member_name = info.userName;
			phone = info.phone;
			email = info.email;
			if(Utils.notNull(member_name))apply_name.setText(member_name);
			if(Utils.notNull(phone))apply_call.setText(phone);
			if(Utils.notNull(email))apply_email.setText(email);
		}
	}
	
	private Response.Listener<String> createApplyCardSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				mLogger.i(response);
				if (response != null) {
					try {
						JSONObject o = new JSONObject(response);
						int status = o.optInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "申请成功",
									Toast.LENGTH_SHORT).show();
							setResult(RESULT_OK);
							finish();
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
				SimpleProgressDialog.dismiss();
				mLogger.e(error.getMessage());
				Toast.makeText(getApplicationContext(), "请求失败",
						Toast.LENGTH_SHORT).show();
			}
		};
	}

}
