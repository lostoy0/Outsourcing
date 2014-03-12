package com.example.youlian.more;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.youlian.BaseActivity;
import com.example.youlian.R;
import com.example.youlian.adapter.MsgCenterAdapter;
import com.example.youlian.mode.MsgCenterResult;
import com.example.youlian.util.PreferencesUtils;
import com.youlian.view.dialog.HuzAlertDialog;

public class MsgCenterDetActivity extends BaseActivity implements
		OnClickListener {

	private ImageButton backBtn;

	private ImageButton delBtn;

	private final static int DET = 1;

	private MsgCenterResult msg;

	private MsgCenterAdapter msgAdapter;

	private boolean isRefresh = false;

	public TextView titile_text, date_text, cotent_text;

	private Context mContext;

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

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
					finish();
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

}
