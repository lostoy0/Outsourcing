package com.example.youlian.mode;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.example.youlian.common.Constants;

/**
 * 1）返回的结果如果type为1表示为点对点的推送消息，
 * 该类消息没有标题和简介，需要客户端自行处理（如：标题为内容的前10个字符加...）；
 * 2）只有type为1的消息才有跳转链接属性；
 * 3）登陆状态下才有两种类型的消息，非登陆状态只有公告类消息
 * 4）只有登录状态才能进行删除和修改为已读的操作
 * @author Raymond
 * @date 2014-3-12 下午10:24:01
 */
public class MsgCenterResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**消息ID*/
	public String id;
	/**消息标题*/
	public String title;
	/**消息简介*/
	public String introduction;
	/**消息详情*/
	public String content;
	/**消息时间（年月日）*/
	public String time;
	/**0为公告消息 1为点对点推送的消息*/
	public int type;
	/**0:未读 1：已读 -1:未登录或其他表示无用状态*/
	public int show_status;
	/**链接类型 -1:不跳转  0:会员卡 1:优惠券 2:签到活动 3:普通活动*/
	public int linkType;	
	/**链接的ID*/
	public String linkId;	
	
	public static MsgCenterResult from(JSONObject json) {
		MsgCenterResult result = null;
		if(json != null) {
			result = new MsgCenterResult();
			result.content = json.optString("content");
			result.id = json.optString("id");
			result.introduction = json.optString("introduction");
			result.linkId = json.optString("linkId");
			result.linkType = json.optInt("linkType");
			result.show_status = json.optInt("show_status");
			result.time = json.optString("time");
			result.title = json.optString("title");
			result.type = json.optInt("type");
		}
		return result;
	}
	
	public static ArrayList<MsgCenterResult> getList(String response) throws JSONException {
		ArrayList<MsgCenterResult> results = null;
		if(!TextUtils.isEmpty(response)) {
			JSONObject responseJsonObject = new JSONObject(response);
			if("1".equals(responseJsonObject.opt(Constants.key_status))) {
				JSONArray array = responseJsonObject.optJSONArray(Constants.key_result);
				if(array != null && array.length() > 0) {
					results = new ArrayList<MsgCenterResult>();
					for(int i=0; i<array.length(); i++) {
						results.add(MsgCenterResult.from(array.getJSONObject(i)));
					}
				}
			}
		}
		return results;
	}
}
