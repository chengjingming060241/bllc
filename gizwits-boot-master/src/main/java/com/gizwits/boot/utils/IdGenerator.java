package com.gizwits.boot.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 生成唯一ID
 * 长度16位
 * 
 */
public class IdGenerator {

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };


	public static Long getBigIntId() {
		Long maxNum = System.currentTimeMillis();
		Long randNum = getRandNum();
		return maxNum * 1000 + randNum;
	}
	
	/**
	    * @Title: getBigIntId15
	    * @Description: 获取15位大整形数据ID号
	    * @return Long    15位长整形
	 */
	public static Long getBigIntId15() {
		Long maxNum = System.currentTimeMillis();
		Long randNum = getRandNum();
		return maxNum * 100 + randNum;
	}
	
	// 获取随机数
	private static Long getRandNum() {
		try {
			Long incrNum = Math.round(Math.random() * 900);
			return incrNum;
		} catch (Exception ex) {
			Long incrNum = Math.round(Math.random() * 900);
			return incrNum;
		}
	}

	public static String getRandomString(int randomLength){
		List list = Arrays.asList(chars);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(chars.length-1-randomLength, chars.length-1);
		return result;
	}

	public static  String generateOrderNo() {
		int random4 = (int)(Math.random() * (9999-1000+1)) +1000;
		StringBuffer sb = new StringBuffer("10").append(System.currentTimeMillis()).append(random4);
		return sb.toString();
	}

	public static String generateTradeNo(String orderId) {
		if(orderId.length()>19){
			orderId = orderId.substring(0,19);
		}
		return orderId + System.currentTimeMillis()/1000;
	}

}
