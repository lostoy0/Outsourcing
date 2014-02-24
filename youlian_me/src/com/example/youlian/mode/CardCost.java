package com.example.youlian.mode;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.youlian.common.Constants;

public class CardCost {
	/**序号*/
	public String id;	
	/**计次商品*/
	public String productName;
	/**剩余次数*/
	public int count;	

	public static CardCost from(JSONObject json) {
		CardCost cost = null;
		if(json != null) {
			cost = new CardCost();
			cost.count = json.optInt("count");
			cost.id = json.optString("id");
			cost.productName = json.optString("productName");
		}
		return cost;
	}
	
	public static ArrayList<CardCost> getList(JSONObject json) throws JSONException {
		ArrayList<CardCost> list = null;
		if(json != null) {
			JSONArray array = json.optJSONArray(Constants.key_result);
			if(array != null && array.length() > 0) {
				list = new ArrayList<CardCost>();
				for(int i=0; i<array.length(); i++) {
					CardCost cost = CardCost.from(array.getJSONObject(i));
					if(cost != null) {
						list.add(cost);
					}
				}
			}
		}
		return list;
	}
}
