package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Ad;
import com.example.youlian.mode.SubjectActivity;
import com.example.youlian.view.TemplateFive;
import com.example.youlian.view.TemplateFour;
import com.example.youlian.view.TemplateOne;
import com.example.youlian.view.TemplateSix;
import com.example.youlian.view.TemplateThree;
import com.example.youlian.view.TemplateTwo;


public class TabFirstPage extends Activity implements OnClickListener {
	
	protected static final String TAG = "TabPie";
	private LinearLayout container;
	private Button bt_membercard;
	private Button bt_youhuiquan;
	private Gallery image_wall_gallery;
	private ImageAdapter adapter;
	private LinearLayout pointLinear;
	private int indicatorPositon;
	private LinearLayout linear_act;
	private LinearLayout linear_seller;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		

		setContentView(R.layout.activity_tab_pie);	
		
		initViews(); 
		// 获取广告轮播图
		YouLianHttpApi.getAdvertisement("0", createGetAdSuccessListener(), createGetAdErrorListener());
        // 活动主题
        YouLianHttpApi.getSubjectActivity(createMyReqSuccessListener(), createMyReqErrorListener());
	}
	
	
	private void initViews() {
		container = (LinearLayout)this.findViewById(R.id.container);
		bt_membercard = (Button)this.findViewById(R.id.bt_membercard);
		bt_membercard.setOnClickListener(this);
		
		bt_youhuiquan = (Button)this.findViewById(R.id.bt_youhuiquan);
		bt_youhuiquan.setOnClickListener(this);
		
		
		image_wall_gallery = (Gallery)this.findViewById(R.id.image_wall_gallery);
		adapter = new ImageAdapter(getApplicationContext());
		image_wall_gallery.setAdapter(adapter);
		
		image_wall_gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				changePointView(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		pointLinear = (LinearLayout) this
				.findViewById(R.id.gallery_point_linear);
		
		linear_act = (LinearLayout)this.findViewById(R.id.linear_act);
		linear_act.setOnClickListener(this);
		linear_seller = (LinearLayout)this.findViewById(R.id.linear_seller);
		linear_seller.setOnClickListener(this);
		
	}
	
	
	public void changePointView(int cur) {
		View view = pointLinear.getChildAt(indicatorPositon);
		View curView = pointLinear.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.screen_indicator_off);
			curPointView.setBackgroundResource(R.drawable.screen_indicator_on);
			indicatorPositon = cur;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_membercard:
			Intent i = new Intent(getApplicationContext(), MembershipActivity.class);
			startActivity(i);
			break;
		case R.id.bt_youhuiquan:
			 i = new Intent(getApplicationContext(), YouhuiQuanActivity.class);
			startActivity(i);
			break;
			
		case R.id.linear_act:
			 i = new Intent(getApplicationContext(), AllSellerActivity.class);
			startActivity(i);
			break;
		case R.id.linear_seller:
			 i = new Intent(getApplicationContext(), YouhuiQuanActivity.class);
			startActivity(i);
			break;
			
		default:
			break;
		}
	}
	
	
	class ImageAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<Ad> ads = new ArrayList<Ad>();

		public ImageAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return ads.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_gallery, null);
				vh = new ViewHolder();
				vh.imageView = (ImageView) convertView
						.findViewById(R.id.gallery_image);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			setValue(vh, position);
			return convertView;
		}
		
		public void add(List<Ad> ads){
			this.ads.addAll(ads);
			notifyDataSetChanged();
		}

		private void setValue(ViewHolder vh, int position) {
			Ad ad = ads.get(position);
			if(!TextUtils.isEmpty(ad.pic)){
				ImageLoader imageLoader = MyVolley.getImageLoader();
	            imageLoader.get(ad.pic, 
	                           ImageLoader.getImageListener(vh.imageView, 
	                                                         R.drawable.guanggao, 
	                                                         R.drawable.guanggao));
			}
			
			changePointView(position);
		}

		protected  class ViewHolder {
			int tag;
			ImageView imageView;
		}
	}


	private Response.Listener<String> createGetAdSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	Log.i(TAG, "success guanggao:" + response);
            	try {
            		List<Ad> ads = Ad.parse(response);
            		adapter.add(ads);
            		
            		addPoint(ads.size());
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }

        };
    }
	

	private void addPoint(int size) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 0, 0, 0);
		for (int i = 0; i < size; i++) {
			ImageView pointView = new ImageView(
					getApplicationContext());
			pointView.setLayoutParams(params);
			if (i == 0) {
				pointView
						.setBackgroundResource(R.drawable.screen_indicator_on);
			} else
				pointView
						.setBackgroundResource(R.drawable.screen_indicator_off);
			pointLinear.addView(pointView);
		}
	}


    private Response.ErrorListener createGetAdErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Log.i(TAG, "error");
            }
        };
    }
    
    
    private Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	Log.i(TAG, "success:" + response);
            	try {
            		List<SubjectActivity> list = SubjectActivity.parse(response);
            		if(list != null)
            		for(int i=0; i<list.size(); i++){
            			SubjectActivity sub = list.get(i);
            			switch (sub.activeTemplate) {
						case Global.TEMPLATE_ONE:
							TemplateOne one = new TemplateOne(getApplicationContext());
							container.addView(one);
							
//							one.setData(sub.pics);
							break;
						case Global.TEMPLATE_TWO:
							TemplateTwo two = new TemplateTwo(getApplicationContext());
							container.addView(two);
							
							break;
						case Global.TEMPLATE_THREE:
							TemplateThree three = new TemplateThree(getApplicationContext());
							container.addView(three);
							break;
						case Global.TEMPLATE_FOUR:
							TemplateFour four = new TemplateFour(getApplicationContext());
							container.addView(four);
							break;
						case Global.TEMPLATE_FIVE:
							TemplateFive five = new TemplateFive(getApplicationContext());
							container.addView(five);
							break;
						case Global.TEMPLATE_SIX:
							TemplateSix six = new TemplateSix(getApplicationContext());
							container.addView(six);
							break;

						default:
							break;
						}
            			
            		}
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Log.i(TAG, "error");
            }
        };
    }
}
