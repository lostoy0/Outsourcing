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
	public double price;
	/**数量*/
	public int quantity;	
	/**热购券ID，当isFav=1时不存在 */
	public String productId;
	/**是否为热购券0是 1否（表示U币即充值）*/
	public int isFav;
	
	public static OrderDetail from(JSONObject json) {
		OrderDetail detail = null;
		if(json != null) {
			detail = new OrderDetail();
			detail.picUrl = json.optString("picUrl");
			detail.price = json.optDouble("price");
			detail.productName = json.optString("productName");
			detail.quantity = json.optInt("quantity");
			detail.simpleDescription = json.optString("simpleDescription");
			detail.productId = json.optString("productId");
			detail.isFav = json.optInt("isFav");
		}
		return detail;
	}
}
