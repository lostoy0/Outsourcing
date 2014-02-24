package com.example.youlian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.youlian.adapter.CardListAdapter;
import com.example.youlian.app.MyVolley;
import com.example.youlian.mode.Card;
import com.example.youlian.util.YlLogger;

/**
 * 卡片列表
 * @author raymond
 *
 */
public class CardListActivity extends BaseActivity implements OnItemClickListener {
	private static YlLogger mLogger = YlLogger.getLogger(CardListActivity.class.getSimpleName());

	private static final int REQ_CODE = 0x1000;
	
	private ListView mListView;
	private CardListAdapter mAdapter;
	
	private ArrayList<Card> mCards;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_list);
		
		mCards = new ArrayList<Card>();
		
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		((TextView) findViewById(R.id.tv_title)).setText("我的会员卡");
		
		mListView = (ListView) findViewById(android.R.id.list);
		mListView.setOnItemClickListener(this);
		mAdapter = new CardListAdapter(this, mCards, MyVolley.getImageLoader());
		mListView.setAdapter(mAdapter);
		
		refreshData();
	}
	
	private void refreshData() {
		mCards.clear();
		YouLianHttpApi.getCardList(Global.getUserToken(this), createGetCardListSuccessListener(), createGetCardListErrorListener());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Intent intent = new Intent(this, CardActivity.class);
		
		Card card = mCards.get(position);
		
		intent.putExtra("card_id", card.card_id);
		intent.putExtra("card_name", card.card_name);
		intent.putExtra("applyWay", card.applyWay);
		intent.putExtra("balanceUrl", card.balanceUrl);
		intent.putExtra("countUrl", card.countUrl);
		
		startActivityForResult(intent, REQ_CODE);
	}

	private Response.Listener<String> createGetCardListSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(TextUtils.isEmpty(response)) {
					mLogger.i("response is null");
				} else {
					mLogger.i(response);
					
					try {
						List<Card> cards = Card.parse(response);
						if(cards != null && cards.size() > 0) {
							mCards.addAll(cards);
							mAdapter.notifyDataSetChanged();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private Response.ErrorListener createGetCardListErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mLogger.e(error.getMessage());
            }
        };
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQ_CODE && resultCode == RESULT_OK) {
			if(data.getBooleanExtra("rm", false)) {
				refreshData();
			}
		}
	}
}
