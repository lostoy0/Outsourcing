package com.example.youlian.more;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.youlian.R;
import com.example.youlian.view.MySlipSwitch;
import com.example.youlian.view.MySlipSwitch.OnSwitchListener;


/**
 * @author rome
 * @proName youlian
 * @version 1.0
 * @Data 2012-9-26 ����11:02:32
 *
   <b>Comment...</b>
 */
public class ShareSetActivity extends Activity implements OnClickListener{
	
	 private Context mContext;
	 
	 private MySlipSwitch myTXBtn;
		private MySlipSwitch myXLBtn;
		private MySlipSwitch myRRBtn;

		private ImageButton back;

		private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.more_share_set);
		mContext= this;
		
		initView();
		initListener();
		
	}
	
	/**
	 * ��ʼ������
	 */
	private void initView(){
		back = (ImageButton) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.shared_set);
		
		
		initSlipBtn();
	}
	/**
	 * �¼�����
	 */
	private void initListener(){
		back.setOnClickListener(this);
	}
	private void initSlipBtn(){
		
        myTXBtn =(MySlipSwitch) findViewById(R.id.tx_slipBtn);//���ָ���ؼ�  
		
       myTXBtn.setImageResource(R.drawable.open, R.drawable.close, R.drawable.slipping);
//       if(PreferencesUtils.getBooleanByKey(mContext, Configure.IS_SHARE_TX)){
//    	   myTXBtn.setSwitchState(true,true);
//		}else{
//			myTXBtn.setSwitchState(false,true);
//		}
       myTXBtn.setOnSwitchListener(new OnSwitchListener() {
				
				@Override
				public void onSwitched(boolean isSwitchOn) {
					// TODO Auto-generated method stub
					if(isSwitchOn) {
//						PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_TX,true);
//						isLogin_tencent();
					} else {
//						PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_TX, false);
					}
				}
			});
        myXLBtn =(MySlipSwitch) findViewById(R.id.xl_slipBtn);//���ָ���ؼ�  
		
       myXLBtn.setImageResource(R.drawable.open, R.drawable.close, R.drawable.slipping);
//       if(PreferencesUtils.getBooleanByKey(mContext, Configure.IS_SHARE_XL)){
//    	   myXLBtn.setSwitchState(true,true);
//		}else{
//			myXLBtn.setSwitchState(false,true);
//		}
       myXLBtn.setOnSwitchListener(new OnSwitchListener() {
				
				@Override
				public void onSwitched(boolean isSwitchOn) {
					// TODO Auto-generated method stub
				if(isSwitchOn) {
//					PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_XL,true);
//					isLogin_sina();
				} else {
//					PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_XL, false);
				}
			}
			});
         myRRBtn =(MySlipSwitch) findViewById(R.id.rr_slipBtn);//���ָ���ؼ�  
		
        myRRBtn.setImageResource(R.drawable.open, R.drawable.close, R.drawable.slipping);
//        if(PreferencesUtils.getBooleanByKey(mContext, Configure.IS_SHARE_RR)){
//        	myRRBtn.setSwitchState(true,true);
// 		}else{
// 			myRRBtn.setSwitchState(false,true);
// 		}
//        myRRBtn.setOnSwitchListener(new OnSwitchListener() {
//				
//				@Override
//				public void onSwitched(boolean isSwitchOn) {
//					if(isSwitchOn) {
////						PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_RR,true);
////						isLogin_renren();
//					} else {
//						PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_RR, false);
//					}
//				}
//			});
	}
	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();
			
			break;

		default:
			break;
		}
	}
	
//
//	private void isLogin_sina() {
////		ConfigHelper.USERINFO_SINA = InfoHelper.getAccessInfo(mContext, 1);
//		 String token = PreferencesUtils.getStringByKey(mContext, Configure.USERINFO_SIAN);
//		 String expires_in = PreferencesUtils.getStringByKey(mContext, Configure.EXPIRES_IN_SIAN);
//		 AccessToken accessToken = null;
//		 if(Utils.notNull(token)){
//			 Utility.setAuthorization(new Oauth2AccessTokenHeader());
//	         accessToken = new AccessToken(token, SystemConfig.CONSUMERSECRET_SINA);
//	         if(Utils.notNull(expires_in))
//	         accessToken.setExpiresIn(expires_in);
//	         Weibo.getInstance().setAccessToken(accessToken);
//		 }
//		
//		if (accessToken != null) {
//			PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_XL,true);
//		} else {
//			//���û�а� Ҫ��
////			Intent intent = new Intent(this, AuthorizeActivity.class);
////			intent.putExtra("thrid_weibo", SystemConfig.SINA_WEIBO);
////			intent.putExtra("type", SystemConfig.TYPE_SHARE);
////			startActivity(intent);
//			Weibo weibo = Weibo.getInstance();
//			// !!Don't forget to set app_key and secret before get token!!!
//			weibo.setupConsumerConfig(SystemConfig.CONSUMERKEY_SINA, SystemConfig.CONSUMERSECRET_SINA);
//			weibo.setRedirectUrl("http://www.skycell.cn");
//			weibo.authorize(ShareSetActivity.this, new AuthDialogListener());
//			
//			
//		}
//	}
//	class AuthDialogListener implements WeiboDialogListener {
//
//        @Override
//        public void onComplete(Bundle values) {
//        	System.out.println("������Ȩ�ɹ�");
//            String token = values.getString("access_token");
//            String expires_in = values.getString("expires_in");
//            System.out.println("access_token : " + token + "  expires_in: " + expires_in);
//            Utility.setAuthorization(new Oauth2AccessTokenHeader());
//            AccessToken accessToken = new AccessToken(token, SystemConfig.CONSUMERSECRET_SINA);
//            accessToken.setExpiresIn(expires_in);
//            Weibo.getInstance().setAccessToken(accessToken);
//            PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_XL,true);
//            PreferencesUtils.addConfigInfo(mContext, Configure.USERINFO_SIAN, token);
//            PreferencesUtils.addConfigInfo(mContext, Configure.EXPIRES_IN_SIAN, expires_in);
//            
//        }
//
//        @Override
//        public void onError(DialogError e) {
//            Toast.makeText(getApplicationContext(), "��Ȩʧ��:" + e.getMessage(),
//                    Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onCancel() {
//            Toast.makeText(getApplicationContext(), "��Ȩʧ��", Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onWeiboException(WeiboException e) {
////            Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(),
////                    Toast.LENGTH_LONG).show();
//        	PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_XL,true);
//        	 PreferencesUtils.addConfigInfo(mContext, Configure.USERINFO_SIAN, Weibo.getInstance().getAccessToken().getToken());
//        	 Long expiresIn = Weibo.getInstance().getAccessToken().getExpiresIn();
//             PreferencesUtils.addConfigInfo(mContext, Configure.EXPIRES_IN_SIAN, String.valueOf(expiresIn));
//        }
//
//    }
//	private void isLogin_tencent() {
//		ConfigHelper.USERINFO_TENCENT = InfoHelper.getAccessInfo(mContext, 2);
//		if (ConfigHelper.USERINFO_TENCENT != null) {
//			PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_TX,true);
//		} else {
//			Intent intent = new Intent(this, AuthorizeActivity.class);
//			intent.putExtra("thrid_weibo", SystemConfig.TENCENT_WEIBO);
//			intent.putExtra("type", SystemConfig.TYPE_SET);
//			startActivity(intent);
//		}
//	}
//	
//	private void isLogin_renren() {
//		ConfigHelper.USERINFO_RENREN = PreferencesUtils.getStringByKey(mContext, Configure.USERINFO_RENREN);
//		System.out.println("ConfigHelper.USERINFO_RENREN:"+ConfigHelper.USERINFO_RENREN);
//		if (ConfigHelper.USERINFO_RENREN != null &&!"".equals(ConfigHelper.USERINFO_RENREN)) {
//			PreferencesUtils.addConfigInfo(mContext,Configure.IS_SHARE_RR,true);
//		} else {
//			String url = "https://graph.renren.com/oauth/authorize?"+    
//            "client_id="+SystemConfig.CONSUMERKEY_RENREN+"&response_type=token"+    
//            "&display=touch&redirect_uri=http://graph.renren.com/oauth/login_success.html";
//			System.out.println("����url:"+url);
//			Intent intent = new Intent(mContext,
//					RenrenWebActivity.class);
//			intent.putExtra("url", url);
//			intent.putExtra("type", SystemConfig.TYPE_SET);
//			startActivity(intent);
//		}
//	}
}
