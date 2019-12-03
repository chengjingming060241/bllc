package com.gizwits.boot.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * zhl
 */
public class SignUtils {
	// 生产随机字符串
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String sign(String jsapi_ticket, String nonce_str,
			String timestamp, String url) {
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			System.out.println(signature);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signature;
	}

	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 微信公众号支付签名算法(详见:http://pay.weixin.qq.com/wiki/doc/api/index.php?chapter=4_3)
	 * @param packageParams 原始参数
	 * @param signKey 加密Key(即 商户Key)
	 * @return 签名字符串
	 */
	public static String createSign(Map<String, String> packageParams, String signKey) {
		SortedMap<String, String> sortedMap = new TreeMap<String, String>();
		sortedMap.putAll(packageParams);

		List<String> keys = new ArrayList<String>(packageParams.keySet());
		Collections.sort(keys);


		StringBuffer toSign = new StringBuffer();
		for (String key : keys) {
			String value = packageParams.get(key);
			if (null != value && !"".equals(value) && !"sign".equals(key)
					&& !"key".equals(key)) {
				toSign.append(key + "=" + value + "&");
			}
		}
		toSign.append("key=" + signKey);
		System.out.println(toSign.toString());
		String sign = DigestUtils.md5Hex(toSign.toString())
				.toUpperCase();
		return sign;
	}
}
