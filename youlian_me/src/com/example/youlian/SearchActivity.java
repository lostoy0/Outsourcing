package com.example.youlian;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.common.Configure;
import com.example.youlian.mode.Customer;
import com.example.youlian.util.PreferencesUtils;
import com.example.youlian.util.Utils;
import com.example.youlian.view.CutomListview;
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
	    private ListView actionListView,cardListView;
	    private CutomListview storeListView;
	    private EditText searchEdit;
	    private ImageButton twoCodeBtn,searchBtn;
	    private Button btn_cate; 
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
			twoCodeBtn = (ImageButton) findViewById(R.id.two_code);
			searchBtn = (ImageButton) findViewById(R.id.search_btn);
			btn_cate =  (Button) findViewById(R.id.btn_cate);
			LayoutInflater mInflater = getLayoutInflater();
			
			
			View lay_all = mInflater.inflate(R.layout.listview, null);
			View lay_nearby = mInflater.inflate(R.layout.listview, null);
			View lay_favourable = mInflater.inflate(R.layout.listview, null);
			
			storeListView = new CutomListview(getApplicationContext());
			
			actionListView = (ListView)lay_nearby.findViewById(R.id.myListView);
			cardListView = (ListView)lay_favourable.findViewById(R.id.myListView);
			
			
			listViews = new ArrayList<View>();
			listViews.add(storeListView);
			listViews.add(lay_nearby);
			listViews.add(lay_favourable);
		}
		private void initListener(){
	    	radioGroup.setOnCheckedChangeListener(this);
//	    	storeListView.setOnItemClickListener(this);
	    	actionListView.setOnItemClickListener(this);
	    	cardListView.setOnItemClickListener(this);
	    	myPagerAdapter = new MyPagerAdapter(listViews);
	    	mPager.setAdapter(myPagerAdapter);
			mPager.setCurrentItem(0);
			mPager.setOnPageChangeListener(new MyOnPageChangeListener());
			twoCodeBtn.setOnClickListener(this);
			searchBtn.setOnClickListener(this);
			btn_cate.setOnClickListener(this);
	    }
		/**
		 *
		 * @param type
		 */
		private void synConnection(int type){
			SimpleProgressDialog.show(this);
		}
		
		/**
		 * login error...
		 */
		private void getCardsError() {
			switch (currIndex) {
			

			}	
		}
		private void noCard(){
		
			
			
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
				YouLianHttpApi.searchCustomer(searchKey, createSearchSuccessListener(), createMyReqErrorListener());
				 
				
				break;
			case R.id.two_code:
//				Intent mCaptureActivity = new Intent(this, CaptureActivity.class);
//				startActivity(mCaptureActivity);
				break;
			case R.id.btn_cate:
//				ArrayList<CardsResult> card = new ArrayList<CardsResult>();
//				if("1".equals(searchType)){
//					if(stores!=null)
//					card.addAll(stores);
//				}else if("2".equals(searchType)){
//					if(cards!=null)
//					card.addAll(cards);
//				}
//				if(Utils.isArrayNotNull(card)){
//					String[] cateNums = new String[14];
//					cateNums[0] = String.valueOf(card.size());
//					int i=0,j=0,k=0,l=0,m=0,n=0,a=0,b=0,c=0,d=0,e=0,f=0,g=0;
//					for (CardsResult cardsResult : card) {
//						if("1".equals(cardsResult.getCategory_id()))i++;
//						if("2".equals(cardsResult.getCategory_id()))j++;
//						if("3".equals(cardsResult.getCategory_id()))k++;
//						if("4".equals(cardsResult.getCategory_id()))l++;
//						if("5".equals(cardsResult.getCategory_id()))m++;
//						if("6".equals(cardsResult.getCategory_id()))n++;
//						if("7".equals(cardsResult.getCategory_id()))a++;
//						if("8".equals(cardsResult.getCategory_id()))b++;
//						if("9".equals(cardsResult.getCategory_id()))c++;
//						if("10".equals(cardsResult.getCategory_id()))d++;
//						if("11".equals(cardsResult.getCategory_id()))e++;
//						if("12".equals(cardsResult.getCategory_id()))f++;
//						if("13".equals(cardsResult.getCategory_id()))g++;
//					}
//					cateNums[1] = String.valueOf(i);
//					cateNums[2] = String.valueOf(j);
//					cateNums[3] = String.valueOf(k);
//					cateNums[4] = String.valueOf(l);
//					cateNums[5] = String.valueOf(m);
//					cateNums[6] = String.valueOf(n);
//					cateNums[7] = String.valueOf(a);
//					cateNums[8] = String.valueOf(b);
//					cateNums[9] = String.valueOf(c);
//					cateNums[10] = String.valueOf(d);
//					cateNums[11] = String.valueOf(e);
//					cateNums[12] = String.valueOf(f);
//					cateNums[13] = String.valueOf(g);
//					
//					Intent mCategoryActivity = new Intent(this, CategoryActivity.class);
//					mCategoryActivity.putExtra("cate_id", cate_id);
//					mCategoryActivity.putExtra("cate_num", cateNums);
//					startActivityForResult(mCategoryActivity, 0);
//				}else{
//					String[]	cateNums = {"0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
//					
//					Intent mCategoryActivity = new Intent(this, CategoryActivity.class);
//					mCategoryActivity.putExtra("cate_id", cate_id);
//					mCategoryActivity.putExtra("cate_num", cateNums);
//					startActivityForResult(mCategoryActivity, 0);
//				}
				
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
		/**
		 *ˢ�����
		 */
		private void onComplete() {
			
		}
		
		private Response.Listener<String> createSearchSuccessListener() {
			return new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.i(TAG, "success:" + response);
					if (response != null) {
						try {
							JSONObject o = new JSONObject(response);
							int status = o.optInt("status");
							if (status == 1) {
								List<Customer> customers = Customer.parseList(o);
								storeListView.setData(customers);
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
					Log.i(TAG, "error");
					Toast.makeText(getApplicationContext(), "请求失败",
							Toast.LENGTH_SHORT).show();
				}
			};
		} 
}
