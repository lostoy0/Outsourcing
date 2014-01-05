package com.example.youlian;

import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.mode.SubjectActivity;
import com.example.youlian.view.TemplateFive;
import com.example.youlian.view.TemplateFour;
import com.example.youlian.view.TemplateOne;
import com.example.youlian.view.TemplateSix;
import com.example.youlian.view.TemplateThree;
import com.example.youlian.view.TemplateTwo;


public class TabFirstPage extends Activity  {
	
	protected static final String TAG = "TabPie";
	private LinearLayout container;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		

		setContentView(R.layout.activity_tab_pie);	
		
		initViews(); 
		
         
        YouLianHttpApi.getSubjectActivity(createMyReqSuccessListener(), createMyReqErrorListener());
	}
	
	
	private void initViews() {
		container = (LinearLayout)this.findViewById(R.id.container);
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
