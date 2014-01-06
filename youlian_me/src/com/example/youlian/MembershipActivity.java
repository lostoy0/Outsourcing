package com.example.youlian;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

public class MembershipActivity extends Activity implements OnClickListener{

    protected static final String TAG = "MembershipActivity";
	private ImageButton back;
	private TextView tv_title;
	private ListView listview;
	private MyAdapter adapter;
	private LayoutInflater inflater;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_membership);
        
        initViews();
        
        YouLianHttpApi.getMemberCard(null, null, createMyReqSuccessListener(), createMyReqErrorListener());
    }

	private void initViews() { 
		back = (ImageButton)this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.membership);
		inflater = LayoutInflater.from(getApplicationContext());
		listview = (ListView) this.findViewById(R.id.listview);
		adapter = new MyAdapter(getApplicationContext());
		listview.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
	
	
	private class MyAdapter extends BaseAdapter {

		public MyAdapter(Context context) {
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
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_membership,
						parent, false);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
				holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
				holder.iv_star_one = (ImageView) convertView.findViewById(R.id.iv_star_one);
				holder.iv_star_two = (ImageView) convertView.findViewById(R.id.iv_star_two);
				holder.iv_star_three = (ImageView) convertView.findViewById(R.id.iv_star_three);
				holder.iv_star_four = (ImageView) convertView.findViewById(R.id.iv_star_four);
				holder.iv_star_five = (ImageView) convertView.findViewById(R.id.iv_star_five);
				holder.iv_online_chong = (ImageView) convertView.findViewById(R.id.iv_online_chong);
				holder.tv_cardname = (TextView) convertView.findViewById(R.id.tv_cardname);
				holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			setValue(holder, position);

			return convertView;
		}

		public void setValue(ViewHolder holder, int position) {
			
		}

		class ViewHolder {
			public ImageView iv_icon;
			public TextView tv_title;
			public ImageView iv_star_one, iv_star_two, iv_star_three, iv_star_four, iv_star_five;
			public ImageView iv_online_chong;
			TextView tv_cardname, tv_desc;
		}

	


	}
	
	
	private Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	Log.i(TAG, "success:" + response);
            	
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
