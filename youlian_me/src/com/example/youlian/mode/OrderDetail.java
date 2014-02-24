package com.example.youlian.mode;

import java.io.Serializable;

import org.json.JSONObject;

public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**商品名称*/
	public String productName;
	/**商品介绍	当订单首字母是R时表示是充值订单，那么这个商品介绍为空*/
	public String simpleDescription;
	/**商品图片URL	当订单首字母是R时表示是充值订单，那么这个商品图片URL为空*/
	public String picUrl;
	/**单价（U币）	当订单首字母是R时表示是充值订单，那么这个单价是一个U币价值多少RMB*/
	public float price;
	/**数量*/
	public int quantity;	
	
	public static OrderDetail from(JSONObject json) {
		OrderDetail detail = null;
		if(json != null) {
			detail = new OrderDetail();
			detail.picUrl = json.optString("picUrl");
			detail.price = json.optInt("price");
			detail.productName = json.optString("productName");
			detail.quantity = json.optInt("quantity");
			detail.simpleDescription = json.optString("simpleDescription");
		}
		return detail;
	}
}
