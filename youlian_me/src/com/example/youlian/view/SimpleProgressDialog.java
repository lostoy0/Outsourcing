package com.example.youlian.view;

import android.content.Context;

/**
 *
 * @author simon
 * @proName vipshop
 * @version 1.0
 * @Data 2012-7-17 下午02:30:55
 *
   <b>Dialog</b>
 */
public class SimpleProgressDialog {
	private static CustomProgressDialog mDialog;

	public static void show(Context context) {

		if(mDialog == null)
			mDialog = CustomProgressDialog.createDialog(context);
			mDialog.setCancelable(true);
			mDialog.show();

	}

	public static void dismiss(){
		if(mDialog != null&& mDialog.isShowing())mDialog.dismiss();
		mDialog = null;
	}
}
