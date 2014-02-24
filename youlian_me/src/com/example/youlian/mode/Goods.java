package com.example.youlian.mode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.youlian.common.Constants;

public class Goods {
	
	/**购物车ID	*/
	public String id;
	/**商品数量*/
	public int quantity;	
	/**商品ID	商品为优惠券中的有价券*/
	public String goodsId;	
	/**商品名称*/
	public String goodsName;
	/**商品单价	需要U币的数量*/
	public int goodsPrice;	
	/**商品图片URL*/
	public String goodsPicUrl;		

	public static Goods from(JSONObject json) throws JSONException {
		Goods goods = null;
		if(json != null) {
			goods = new Goods();
			goods.id = json.optString("id");
			goods.goodsId = json.optString("goodsId");
			goods.goodsName = json.optString("goodsName");
			goods.goodsPicUrl = json.optString("goodsPicUrl");
			goods.goodsPrice = json.optInt("goodsPrice");
			goods.quantity = json.optInt("quantity");
		}
		return goods;
	}
	
	public static List<Goods> get(JSONObject object) throws JSONException {
		List<Goods> goodsList = null;
		if(object != null) {
			if(1 == object.optInt(Constants.key_status)) {
				JSONArray array = object.optJSONArray(Constants.key_result);
				if(array != null && array.length() > 0) {
					goodsList = new ArrayList<Goods>();
					for(int i=0; i<array.length(); i++) {
						Goods goods = Goods.from(array.getJSONObject(i));
						if(goods != null) {
							goodsList.add(goods);
						}
					}
				}
			}
		}
		return goodsList;
	}
}
