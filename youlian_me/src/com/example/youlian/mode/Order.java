package com.example.youlian.mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.youlian.common.Constants;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**订单ID*/
	public String id;	
	/**订单号*/
	public String orderNo;	
	/**订单中商品总个数*/
	public int countQuantity;
	/**订单总额（U币）	当订单首字母是R时表示是充值订单，那么这个总额是RMB*/
	public float youcoinCount;	
	/**订单状态	0:已支付,1:未支付,2:已取消,3:已关闭*/
	public int status;	
	
	public List<OrderDetail> orderDetailList;
	
	public static Order from(JSONObject json) throws JSONException {
		Order order = null;
		if(json != null) {
			order = new Order();
			order.countQuantity = json.optInt("");
			order.id = json.optString("");
			order.orderNo = json.optString("");
			order.status = json.optInt("");
			order.youcoinCount = json.optInt("");
			JSONArray array = json.optJSONArray("");
			if(array != null && array.length() > 0) {
				List<OrderDetail> details = new ArrayList<OrderDetail>();
				for(int i=0; i<array.length(); i++) {
					OrderDetail detail = OrderDetail.from(array.getJSONObject(i));
					if(detail != null) {
						details.add(detail);
					}
				}
				order.orderDetailList = details;
			}
		}
		return order;
	}
	
	public static List<Order> getOrders(JSONObject json) throws JSONException {
		List<Order> orders = null;
		if(json != null) {
			JSONArray array = json.optJSONArray(Constants.key_result);
			if(array != null && array.length() > 0) {
				orders = new ArrayList<Order>();
				for(int i=0; i<array.length(); i++) {
					Order order = Order.from(array.getJSONObject(i));
					if(order != null) {
						orders.add(order);
					}
				}
			}
		}
		return orders;
	}
	
}
