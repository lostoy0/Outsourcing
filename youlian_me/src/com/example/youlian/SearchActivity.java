package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.mode.Card;
import com.example.youlian.mode.Customer;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.view.ListviewAct;
import com.example.youlian.view.ListviewCards;
import com.example.youlian.view.ListviewShangjia;
import com.example.youlian.view.SimpleProgressDialog;

/**
 * @author simon
 * @proName youlian
 * @version 1.0
 * @Data 2012-9-26 ����11:02:32
 *
   <b>Comment...</b>
 */
public class SearchActivity extends Activity implements OnClickListener, OnCheckedChangeListener, OnItemClickListener{
	    private Context mContext;
	    private RadioGroup radioGroup;
	    private ListviewCards cardListView;
	    private ListviewAct actionListView;
	    private ListviewShangjia shangjiaListView;
	    private EditText searchEdit;
	    private ImageButton ib_right, searchBtn;
	    private int groupCount;
	    
	    private TextView storesNocardText,cardsNocardText,actionsNocardText;
	    private ViewPager mPager;//ҳ������
	    private List<View> listViews; // Tabҳ���б�
	    private ImageView cursor;// ����ͼƬ
	    private int offset = 0;// ����ͼƬƫ����
		private int currIndex = 0;// ��ǰҳ�����
		private int bmpW;// ����ͼƬ���
		private final static int GET_SEARCH_STORE =1;//�̼�
		private final static int GET_SEARCH_ACTION =2;//�
		private final static int GET_SEARCH_CARDS =3;//��Ա��
		private final static int GET_CATE_CARDS = 4; //
		protected static final String TAG = "SearchActivity";
		
		private String user_token;
		
		private String searchKey;
		
		private boolean isRefresh = false;
		
		private String[] cates = new String[]{"ȫ��","��ʳ","����","ס��","����","����","����",
				"����","��Ӱ","����","����","ҽ��","��Ӥ","����"};
		
		private String searchType = "1";
		
		MyPagerAdapter myPagerAdapter;
		
		private int cate_id;
		private View right_slide;
		private Button back_to_main;
		private ImageButton back;
		private TextView tv_title;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.search);
			user_token = PreferencesUtils.getUserToken(this);
			mContext = this;
			initView();
			InitImageView();
			initListener();
		}
		private void initView(){
	    	radioGroup = (RadioGroup)findViewById(R.id.my_Radiogroup);
	    	groupCount = radioGroup.getChildCount();
	    	searchEdit =(EditText)findViewById(R.id.search_edit);
	    	cursor = (ImageView) findViewById(R.id.cursor);
			mPager = (ViewPager) findViewById(R.id.vPager);
			ib_right = (ImageButton) findViewById(R.id.ib_right);
			searchBtn = (ImageButton) findViewById(R.id.search_btn);
			LayoutInflater mInflater = getLayoutInflater();
			
			
			back = (ImageButton) this.findViewById(R.id.back);
			back.setOnClickListener(this);
//			ib_right.setVisibility(View.VISIBLE);
			ib_right.setOnClickListener(this);
			
			
			
			shangjiaListView = new ListviewShangjia(getApplicationContext());
			
			actionListView = new ListviewAct(getApplicationContext());
			cardListView = new ListviewCards(getApplicationContext());
			
			
			
			listViews = new ArrayList<View>();
			listViews.add(shangjiaListView);
			listViews.add(actionListView);
			listViews.add(cardListView);
			
			
			right_slide = this.findViewById(R.id.right_slide);
			back_to_main = (Button)right_slide.findViewById(R.id.back_to_main);
			back_to_main.setOnClickListener(this);
			
			tv_title = (TextView) this.findViewById(R.id.tv_title);
			tv_title.setText(R.string.search);
			
		}
		private void initListener(){
	    	radioGroup.setOnCheckedChangeListener(this);
//	    	storeListView.setOnItemClickListener(this);
//	    	actionListView.setOnItemClickListener(this);
//	    	cardListView.setOnItemClickListener(this);
	    	
	    	
	    	myPagerAdapter = new MyPagerAdapter(listViews);
	    	mPager.setAdapter(myPagerAdapter);
			mPager.setCurrentItem(0);
			mPager.setOnPageChangeListener(new MyOnPageChangeListener());
			searchBtn.setOnClickListener(this);
	    }
		
		
		/**
		 * @author simon
		 * @pyoulian
		 * @version 1.0
		 * @Data ����11:02:32
		 */
		@Override
		public void onCheckedChanged(RadioGroup group, int readioID) {
			int index = 0;
			switch (readioID) {
			case R.id.radio_store:
				index = 0;
				break;
			case R.id.radio_action:
				index = 1;
				break;
			case R.id.radio_card:
				index = 2;
				break;
			}
			for (int i = 0; i < groupCount; i++) {
				RadioButton button = (RadioButton) group.getChildAt(i);
				if(button.getId()==readioID){
					button.setTextColor(getResources().getColor(R.color.red));
					button.setEnabled(false);
				}else{
					button.setTextColor(getResources().getColor(R.color.blue));
					button.setEnabled(true);
				}
			}
			mPager.setCurrentItem(index);
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.search_btn:
				searchKey = searchEdit.getText().toString();
				switch (currIndex) {
				case 0: // 商家
					SimpleProgressDialog.show(this);
					YouLianHttpApi.searchShangjia(searchKey,Global.getLocCityId(getApplicationContext()), 
							createSearchShangjiaSuccessListener(), createMyReqErrorListener());
					break;
					
				case 1: // 活动
					SimpleProgressDialog.show(this);
					YouLianHttpApi.searchAct(searchKey,Global.getLocCityId(getApplicationContext()), 
							createSearchActSuccessListener(), createMyReqErrorListener());
					break;
					
				case 2: //会员卡
					SimpleProgressDialog.show(this);
					YouLianHttpApi.searchCards(searchKey,Global.getLocCityId(getApplicationContext()), 
							createSearchCardsSuccessListener(), createMyReqErrorListener());
					break;

				default:
					break;
				}
				break;
			case R.id.ib_right:
//				Intent mCaptureActivity = new Intent(this, CaptureActivity.class);
//				startActivityForResult(mCaptureActivity,0);
				
				Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.right_in);
				right_slide.startAnimation(hyperspaceJumpAnimation);
				right_slide.setVisibility(View.VISIBLE);
				break;
				
			case R.id.back_to_main:
				hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.right_out);
				right_slide.startAnimation(hyperspaceJumpAnimation);
				right_slide.setVisibility(View.GONE);
				break;
			case R.id.back:
				finish();
				break;
			}
			
		}
		/**
		 * ��ʼ������
		 */
		private void InitImageView() {
			cursor = (ImageView) findViewById(R.id.cursor);
			bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.second_goto)
					.getWidth();// ��ȡͼƬ���
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
			offset = (screenW / 3 - bmpW) / 2;// ����ƫ����
			Matrix matrix = new Matrix();
			matrix.postTranslate(offset, 0);
			cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
		}
		/**
		 * ҳ���л�����
		 */
		public class MyOnPageChangeListener implements OnPageChangeListener {

			int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
			int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����

			@Override
			public void onPageSelected(int arg0) {
				Animation animation = null;
				System.out.println("argo:"+arg0);
				switch (arg0) {
				case 0:
					if (currIndex == 1) {
						animation = new TranslateAnimation(one, 0, 0, 0);
					} else if (currIndex == 2) {
						animation = new TranslateAnimation(two, 0, 0, 0);
					}
					break;
				case 1:
					if (currIndex == 0) {
						animation = new TranslateAnimation(offset, one, 0, 0);
					} else if (currIndex == 2) {
						animation = new TranslateAnimation(two, one, 0, 0);
					}
					break;
				case 2:
					if (currIndex == 0) {
						animation = new TranslateAnimation(offset, two, 0, 0);
					} else if (currIndex == 1) {
						animation = new TranslateAnimation(one, two, 0, 0);
					}
					break;
				}
				for (int i = 0; i < groupCount; i++) {
					RadioButton button = (RadioButton) radioGroup.getChildAt(i);
					if(i==arg0){
						button.setTextColor(getResources().getColor(R.color.red));
						button.setEnabled(false);
					}else{
						button.setTextColor(getResources().getColor(R.color.blue));
						button.setEnabled(true);
					}
				}
				
				currIndex = arg0;
				if(animation!=null){
					animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
					animation.setDuration(300);
					cursor.startAnimation(animation);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
			
			public void changeView(int index){
			}
		}
		
		private Response.Listener<String> createSearchShangjiaSuccessListener() {
			return new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					SimpleProgressDialog.dismiss();
					Log.i(TAG, "success:" + response);
					if (response != null) {
						try {
							JSONObject o = new JSONObject(response);
							int status = o.optInt("status");
							if (status == 1) {
								List<Customer> customers = Customer.parseList(o);
								shangjiaListView.setData(customers);
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
		
		
		private Response.Listener<String> createSearchActSuccessListener() {
			return new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					SimpleProgressDialog.dismiss();
					Log.i(TAG, "success:" + response);
					if (response != null) {
						try {
							JSONObject o = new JSONObject(response);
							int status = o.optInt("status");
							if (status == 1) {
								List<Customer> customers = Customer.parseList(o);
								shangjiaListView.setData(customers);
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
		

		
		private Response.Listener<String> createSearchCardsSuccessListener() {
			return new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					SimpleProgressDialog.dismiss();
					Log.i(TAG, "success:" + response);
					if (response != null) {
						try {
							JSONObject o = new JSONObject(response);
							int status = o.optInt("status");
							if(status == 1){
								JSONArray array = o.optJSONArray("result");
								int len = array.length();
								List<Card> cards = new ArrayList<Card>();
								for(int i=0; i<len; i++){
									JSONObject oo = array.getJSONObject(i);
									cards.add(Card.parse(oo));
								}
								cardListView.setData(cards);
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
		private Response.ErrorListener createMyReqErrorListener() {
			return new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					SimpleProgressDialog.dismiss();
					Log.i(TAG, "error");
					Toast.makeText(getApplicationContext(), "请求失败",
							Toast.LENGTH_SHORT).show();
				}
			};
		} 
}
