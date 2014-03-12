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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
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
import com.example.youlian.mode.Act;
import com.example.youlian.mode.Card;
import com.example.youlian.mode.City;
import com.example.youlian.mode.Customer;
import com.example.youlian.mode.YouhuiQuan;
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
		private ListView listview_all;
		private LayoutInflater inflater;
		private MyAdapterAll adapterAll;
		
		public List<Customer> mHandleCustomers = new ArrayList<Customer>();
		private List<Customer> mCustomers = new ArrayList<Customer>();
		
		public List<String> parts = new ArrayList<String>();
		
		
		
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
			inflater = LayoutInflater.from(getApplicationContext());
	    	radioGroup = (RadioGroup)findViewById(R.id.my_Radiogroup);
	    	groupCount = radioGroup.getChildCount();
	    	searchEdit =(EditText)findViewById(R.id.search_edit);
	    	cursor = (ImageView) findViewById(R.id.cursor);
			mPager = (ViewPager) findViewById(R.id.vPager);
			ib_right = (ImageButton) findViewById(R.id.ib_right);
			searchBtn = (ImageButton) findViewById(R.id.search_btn);
			
			back = (ImageButton) this.findViewById(R.id.back);
			back.setOnClickListener(this);
			ib_right.setVisibility(View.VISIBLE);
			ib_right.setOnClickListener(this);
			
			
			
			shangjiaListView = new ListviewShangjia(getApplicationContext());
			
			actionListView = new ListviewAct(getApplicationContext());
			cardListView = new ListviewCards(getApplicationContext());
			
			
			
			listViews = new ArrayList<View>();
			listViews.add(shangjiaListView);
			listViews.add(actionListView);
			listViews.add(cardListView);
			
			
			right_slide = this.findViewById(R.id.right_slide);
			right_slide.setVisibility(View.INVISIBLE);
			back_to_main = (Button)right_slide.findViewById(R.id.back_to_main);
			back_to_main.setOnClickListener(this);
			
			
			
			tv_title = (TextView) this.findViewById(R.id.tv_title);
			tv_title.setText(R.string.search);
			
			
			parts.add(getString(R.string.all_size, 0));
			parts.add(getString(R.string.living_service, shenghuoService));
			parts.add(getString(R.string.meili_liren, meiliLiren));
			parts.add(getString(R.string.xiuxian_yulei, xiuxianYule));
			parts.add(getString(R.string.canyin_meishi, canyinMeishi));
			parts.add(getString(R.string.gouwu_buy, guangjieGouwu));
			parts.add(getString(R.string.other, otherMe));
			
			listview_all = (ListView) right_slide.findViewById(R.id.listview_all);
			adapterAll = new MyAdapterAll(getApplicationContext());
			listview_all.setAdapter(adapterAll);

			listview_all.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
							if(position < parts.size()){
								switch (currIndex) {
								case 0:
									processShangjia(position);
									break;
								case 1:
									processActs(position);							
									break;
								case 2:
									processCards(position);
									break;

								default:
									break;
								}
								
							}			
							
					hideFuceng();
				}

				
			});
		}
		
		protected void processActs(int position) {
			handleActs.clear();
			switch (position) {
			case 0:
				handleActs.addAll(acts);
				break;
			case ShangjiaActivity.shenghuo_service:
				initShenghuoListByAct(ShangjiaActivity.shenghuo_service);
				break;
			case ShangjiaActivity.meili_liren:
				initShenghuoListByAct(ShangjiaActivity.meili_liren);
				break;
			case ShangjiaActivity.xiuxian_yule:
				initShenghuoListByAct(ShangjiaActivity.xiuxian_yule);
				break;
			case ShangjiaActivity.canyin_meishi:
				initShenghuoListByAct(ShangjiaActivity.canyin_meishi);
				break;
			case ShangjiaActivity.guangjie_gouwu:
				initShenghuoListByAct(ShangjiaActivity.guangjie_gouwu);
				break;
			case ShangjiaActivity.other:
				initShenghuoListByAct(ShangjiaActivity.other);
				break;
			default:
				break;
			}
			actionListView.setData(handleActs);
		}
		
		
		protected void processCards(int position) {
			handlecards.clear();
			switch (position) {
			case 0:
				handlecards.addAll(cards);
				break;
			case ShangjiaActivity.shenghuo_service:
				initShenghuoListByCard(ShangjiaActivity.shenghuo_service);
				break;
			case ShangjiaActivity.meili_liren:
				initShenghuoListByCard(ShangjiaActivity.meili_liren);
				break;
			case ShangjiaActivity.xiuxian_yule:
				initShenghuoListByCard(ShangjiaActivity.xiuxian_yule);
				break;
			case ShangjiaActivity.canyin_meishi:
				initShenghuoListByCard(ShangjiaActivity.canyin_meishi);
				break;
			case ShangjiaActivity.guangjie_gouwu:
				initShenghuoListByCard(ShangjiaActivity.guangjie_gouwu);
				break;
			case ShangjiaActivity.other:
				initShenghuoListByCard(ShangjiaActivity.other);
				break;
			default:
				break;
			}
			cardListView.setData(handlecards);
		}
		private void processShangjia(int position) {
			mHandleCustomers.clear();
			switch (position) {
			case 0:
				mHandleCustomers.addAll(mCustomers);
				break;
			case ShangjiaActivity.shenghuo_service:
				initShenghuoList(ShangjiaActivity.shenghuo_service);
				break;
			case ShangjiaActivity.meili_liren:
				initShenghuoList(ShangjiaActivity.meili_liren);
				break;
			case ShangjiaActivity.xiuxian_yule:
				initShenghuoList(ShangjiaActivity.xiuxian_yule);
				break;
			case ShangjiaActivity.canyin_meishi:
				initShenghuoList(ShangjiaActivity.canyin_meishi);
				break;
			case ShangjiaActivity.guangjie_gouwu:
				initShenghuoList(ShangjiaActivity.guangjie_gouwu);
				break;
			case ShangjiaActivity.other:
				initShenghuoList(ShangjiaActivity.other);
				break;
			default:
				break;
			}
			shangjiaListView.setData(mHandleCustomers);
		}
		
		protected void hideFuceng() {
			backToMain();
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
		
		
		protected void initShenghuoList(int shenghuoService) {
			int size = mCustomers.size();
			for(int i=0;i<size; i++){
				Customer c = mCustomers.get(i);
				if(shenghuoService == Integer.parseInt(c.customerKindId)){
					mHandleCustomers.add(c);
				}
			}
		}
		
		
		protected void initShenghuoListByCard(int shenghuoService) {
			int size = cards.size();
			for(int i=0;i<size; i++){
				Card c = cards.get(i);
				if(shenghuoService == Integer.parseInt(c.category_id)){
					handlecards.add(c);
				}
			}
		}
		
		
		protected void initShenghuoListByAct(int shenghuoService) {
			int size = acts.size();
			for(int i=0;i<size; i++){
				Act c = acts.get(i);
				if(shenghuoService == Integer.parseInt(c.category_id)){
					handleActs.add(c);
				}
			}
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
				backToMain();
				break;
			case R.id.back:
				finish();
				break;
			}
			
		}
		private void backToMain() {
			Animation hyperspaceJumpAnimation;
			hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.right_out);
			right_slide.startAnimation(hyperspaceJumpAnimation);
			right_slide.setVisibility(View.GONE);
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
				refreshCategory();
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
								mCustomers.clear();
								mHandleCustomers.clear();
								
								mCustomers.addAll(Customer.parseList(o));
								shangjiaListView.setData(mCustomers);
								
								mHandleCustomers.clear();
								mHandleCustomers.addAll(mCustomers);
								initCategoryNum();
								
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
		
		public void refreshCategory() {
			switch (currIndex) {
			case 0:
				initCategoryNum();
				break;
			case 1:
				initCategoryNumByAct();
				break;
			case 2:
				initCategoryNumByCard();
				break;

			default:
				break;
			}
		}

		public List<Card> cards = new ArrayList<Card>();
		public List<Card> handlecards = new ArrayList<Card>();
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
								acts.clear();
								JSONArray array = o.optJSONArray("result");
								int len = array.length();
								for(int i=0; i<len; i++){
									JSONObject oo = array.getJSONObject(i);
									acts.add(Act.parse(oo));
								}
								
								handleActs.clear();
								handleActs.addAll(acts);
								
								actionListView.setData(acts);
								
								initCategoryNumByAct();
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
								cards.clear();
								for(int i=0; i<len; i++){
									JSONObject oo = array.getJSONObject(i);
									cards.add(Card.parse(oo));
								}
								cardListView.setData(cards);
								
								handlecards.clear();
								handlecards.addAll(cards);
								initCategoryNumByCard();
								
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
		protected void initCategoryNumByCard() {
			shenghuoService = 0;
			meiliLiren = 0;
			xiuxianYule = 0;
			canyinMeishi = 0;
			guangjieGouwu = 0;
			otherMe = 0;
			int size = cards.size();
			for(int i=0;i<size; i++){
				Card c = cards.get(i);
				int type = Integer.parseInt(c.category_id);
				switch (type) {
				case ShangjiaActivity.shenghuo_service:
					shenghuoService++;
					break;
				case ShangjiaActivity.meili_liren:
					meiliLiren++;	
					break;
				case ShangjiaActivity.xiuxian_yule:
					xiuxianYule++;
					break;
				case ShangjiaActivity.canyin_meishi:
					canyinMeishi++;
					break;
				case ShangjiaActivity.guangjie_gouwu:
					guangjieGouwu++;
					break;
				case ShangjiaActivity.other:
					otherMe++;
					break;
				default:
					break;
				}
			}
			parts.clear();
			parts.add(getString(R.string.all_size, size));
			parts.add(getString(R.string.living_service, shenghuoService));
			parts.add(getString(R.string.meili_liren, meiliLiren));
			parts.add(getString(R.string.xiuxian_yulei, xiuxianYule));
			parts.add(getString(R.string.canyin_meishi, canyinMeishi));
			parts.add(getString(R.string.gouwu_buy, guangjieGouwu));
			parts.add(getString(R.string.other, otherMe));
			adapterAll.notifyDataSetChanged();
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
		
		
		
		private class MyAdapterAll extends BaseAdapter {

			public MyAdapterAll(Context context) {
			}

			@Override
			public int getCount() {
				return 10;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = inflater.inflate(R.layout.item_all, parent, false);
				ImageView iv = (ImageView) convertView.findViewById(R.id.iv_icon);
				TextView tv = (TextView) convertView.findViewById(R.id.tv_title);
				if(position < parts.size()){
					tv.setText(parts.get(position));
					switch (position) {
					case 0:
						iv.setImageResource(R.drawable.iv_all);
						break;
					case 1:
						iv.setImageResource(R.drawable.living_service);				
						break;
					case 2:
						iv.setImageResource(R.drawable.meili_liren);
						break;
					case 3:
						iv.setImageResource(R.drawable.xiuxian_yule);
						break;
					case 4:
						iv.setImageResource(R.drawable.canyin_meishi);
						break;
					case 5:
						iv.setImageResource(R.drawable.guangjie_gouwu);
						break;
					case 6:
						iv.setImageResource(R.drawable.iv_other);
						break;

					default:
						break;
					}
					iv.setVisibility(View.VISIBLE);
				}else{
					iv.setVisibility(View.GONE);
				}
				
				
				return convertView;
			}

			class ViewHolder {
				public ImageView iv_icon;
				public TextView tv_title;
				public ImageView iv_star_one, iv_star_two, iv_star_three,
						iv_star_four, iv_star_five;
				public ImageView iv_online_chong;
				TextView tv_cardname, tv_desc;
			}
		}
		

		int shenghuoService;
		int meiliLiren;
		int xiuxianYule;
		int canyinMeishi;
		int guangjieGouwu;
		int otherMe;
		private void initCategoryNum() {
			shenghuoService = 0;
			meiliLiren = 0;
			xiuxianYule = 0;
			canyinMeishi = 0;
			guangjieGouwu = 0;
			otherMe = 0;
			int size = mHandleCustomers.size();
			for(int i=0;i<size; i++){
				Customer c = mHandleCustomers.get(i);
				int type = Integer.parseInt(c.customerKindId);
				switch (type) {
				case ShangjiaActivity.shenghuo_service:
					shenghuoService++;
					break;
				case ShangjiaActivity.meili_liren:
					meiliLiren++;	
					break;
				case ShangjiaActivity.xiuxian_yule:
					xiuxianYule++;
					break;
				case ShangjiaActivity.canyin_meishi:
					canyinMeishi++;
					break;
				case ShangjiaActivity.guangjie_gouwu:
					guangjieGouwu++;
					break;
				case ShangjiaActivity.other:
					otherMe++;
					break;
				default:
					break;
				}
			}
			parts.clear();
			parts.add(getString(R.string.all_size, size));
			parts.add(getString(R.string.living_service, shenghuoService));
			parts.add(getString(R.string.meili_liren, meiliLiren));
			parts.add(getString(R.string.xiuxian_yulei, xiuxianYule));
			parts.add(getString(R.string.canyin_meishi, canyinMeishi));
			parts.add(getString(R.string.gouwu_buy, guangjieGouwu));
			parts.add(getString(R.string.other, otherMe));
			adapterAll.notifyDataSetChanged();
		}
		
		
		public List<Act> acts = new ArrayList<Act>();
		public List<Act> handleActs = new ArrayList<Act>();
		public void initCategoryNumByAct() {
			shenghuoService = 0;
			meiliLiren = 0;
			xiuxianYule = 0;
			canyinMeishi = 0;
			guangjieGouwu = 0;
			otherMe = 0;
			int size = acts.size();
			for(int i=0;i<size; i++){
				Act c = acts.get(i);
				int type = Integer.parseInt(c.category_id);
				switch (type) {
				case ShangjiaActivity.shenghuo_service:
					shenghuoService++;
					break;
				case ShangjiaActivity.meili_liren:
					meiliLiren++;	
					break;
				case ShangjiaActivity.xiuxian_yule:
					xiuxianYule++;
					break;
				case ShangjiaActivity.canyin_meishi:
					canyinMeishi++;
					break;
				case ShangjiaActivity.guangjie_gouwu:
					guangjieGouwu++;
					break;
				case ShangjiaActivity.other:
					otherMe++;
					break;
				default:
					break;
				}
			}
			parts.clear();
			parts.add(getString(R.string.all_size, size));
			parts.add(getString(R.string.living_service, shenghuoService));
			parts.add(getString(R.string.meili_liren, meiliLiren));
			parts.add(getString(R.string.xiuxian_yulei, xiuxianYule));
			parts.add(getString(R.string.canyin_meishi, canyinMeishi));
			parts.add(getString(R.string.gouwu_buy, guangjieGouwu));
			parts.add(getString(R.string.other, otherMe));
			adapterAll.notifyDataSetChanged();
		}
}
