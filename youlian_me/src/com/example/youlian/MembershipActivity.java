package com.example.youlian;

import com.android.volley.toolbox.NetworkImageView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MembershipActivity extends Activity implements OnClickListener{

    private ImageButton back;
	private TextView tv_title;
	private ListView listview;
	private MyAdapter adapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        
        initViews();
        
        
    }

	private void initViews() { 
		back = (ImageButton)this.findViewById(R.id.back);
		back.setOnClickListener(this);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText(R.string.membership);
		
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
	        return 0;
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
//			ViewHolder holder = null;
//			if (convertView == null) {
//				convertView = inflater.inflate(R.layout.item_my_favorite,
//						parent, false);
//				holder = new ViewHolder();
//				holder.iv_channel_icon = (NetworkImageView) convertView.findViewById(R.id.iv_channel_icon);
//				holder.tv_channel_title = (TextView) convertView.findViewById(R.id.tv_channel_title);
//				holder.tv_jiemu_name = (TextView) convertView.findViewById(R.id.tv_jiemu_name);
//				holder.icon = (NetworkImageView) convertView.findViewById(R.id.iv_icon);
//				holder.favorite = (LinearLayout) convertView.findViewById(R.id.linear_cancel);
//				holder.favorite.setOnClickListener(FavoriteActivity.this);
//				holder.play = (LinearLayout) convertView.findViewById(R.id.linear_play);
//				holder.play.setOnClickListener(FavoriteActivity.this);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//
//			setValue(holder, position);

			return convertView;
		}

		public void setValue(ViewHolder holder, int position) {
			
		}

		class ViewHolder {
			public NetworkImageView iv_channel_icon;
			
			public TextView tv_channel_title;
			public TextView tv_jiemu_name;
			public NetworkImageView icon;
			public LinearLayout favorite;
			public LinearLayout play;
		}

	


	}
    
}
