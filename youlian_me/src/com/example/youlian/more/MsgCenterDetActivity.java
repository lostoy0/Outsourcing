package com.example.youlian.more;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.BaseActivity;
import com.example.youlian.Global;
import com.example.youlian.R;
import com.example.youlian.YouLianHttpApi;
import com.example.youlian.common.Constants;
import com.example.youlian.mode.MsgCenterResult;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.YlLogger;
import com.example.youlian.view.SimpleProgressDialog;
import com.youlian.view.dialog.HuzAlertDialog;

public class MsgCenterDetActivity extends BaseActivity implements OnClickListener {
	private YlLogger mLogger = YlLogger.getLogger(MsgCenterDetActivity.class.getSimpleName());

	private ImageButton backBtn;
	private ImageButton delBtn;

	private MsgCenterResult msg;

	public TextView titile_text, date_text, cotent_text;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_msg_center_det);
		msg = (MsgCenterResult) getIntent().getSerializableExtra("msg");
		mContext = this;
		initView();
		initListener();
		initMsgs();
	}

	private void initView() {
		backBtn = (ImageButton) findViewById(R.id.back);
		delBtn = (ImageButton) findViewById(R.id.del);
		titile_text = (TextView) findViewById(R.id.titile_text);
		date_text = (TextView) findViewById(R.id.date_text);
		cotent_text = (TextView) findViewById(R.id.cotent_text);
	}

	private void initListener() {
		backBtn.setOnClickListener(this);
		delBtn.setOnClickListener(this);
	}

	private void initMsgs() {
		titile_text.setText(msg.title);
		date_text.setText(msg.time);
		cotent_text.setText(Html.fromHtml(msg.content));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();

			break;
		case R.id.del:
			Builder bd = new HuzAlertDialog.Builder(this);
			bd.setTitle("删除");
			bd.setMessage("是否删这个信息");
			bd.setPositiveButton("是", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface d, int which) {
					PreferencesUtils.addConfigInfo(mContext, msg.id, "del");
					YouLianHttpApi.delMessage(Global.getUserToken(MsgCenterDetActivity.this), msg.id + ":" + msg.type, createSuccessListener(), createErrorListener());
				}
			});
			bd.setNeutralButton("否", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface d, int which) {
					d.dismiss();
				}
			});
			bd.show();
			break;
		}
	}
	
	private Response.Listener<String> createSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				SimpleProgressDialog.dismiss();
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
					showToast("删除失败");
				} else {
					try {
						JSONObject object = new JSONObject(response);
						if(1 == object.optInt(Constants.key_status)) {
							showToast("删除成功");
							finish();
						} else {
							showToast("删除失败");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			}
		};
	}
	
	private Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            	SimpleProgressDialog.dismiss();
            	showToast("删除失败");
            }
        };
    }

}
