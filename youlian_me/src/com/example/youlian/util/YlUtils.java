package com.example.youlian.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.youlian.codec.DigestUtils;

public class YlUtils {
	
	private static char[] HEX = new char[] { 
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
	
	public static String md5(String content) {
		if (content == null)
			return "null";
		return bytesToHexStringWithoutSpace(DigestUtils.md5(content));
	}
	
	public static String md5(byte[] b) {
		if(b == null)
			return "null";
		return bytesToHexStringWithoutSpace(DigestUtils.md5(b));
	}
	
	public static String bytesToHexStringWithoutSpace(byte[] b) {
		return bytesToHexStringWithoutSpace(b, 0, b.length);
	}
	
    public static String bytesToHexStringWithoutSpace(byte[] b, int offset, int len) {
    	if(b == null)
    		return "null";
    	
        int end = offset + len;
        if(end > b.length)
            end = b.length;
        
        StringBuilder sb = new StringBuilder();
        for(int i = offset; i < end; i++) {
            sb.append(HEX[(b[i] & 0xF0) >>> 4])
            	.append(HEX[b[i] & 0xF]);
        }
        return sb.toString();
    }
    
    public static boolean isMobileValid(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
    
}
