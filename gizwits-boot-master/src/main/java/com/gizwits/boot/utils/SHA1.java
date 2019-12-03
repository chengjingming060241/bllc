/**
 * 
 * God bless all goes well! 
 * 
 *
 * Author   JimLao 
 * E-mail   developer.lao@gmail.com
 * Created  on Apr 9, 2015 4:05:01 PM
 */
package com.gizwits.boot.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * SHA1 签名
 */
public class SHA1 {
	/**
	 * 排序并生成SHA1签名
	 */
	public static String gen(String... arr) throws NoSuchAlgorithmException {
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (String a : arr) {
			sb.append(a);
		}

		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		sha1.update(sb.toString().getBytes());
		byte[] output = sha1.digest();
		return bytesToHex(output);
	}

	/**
	 * 生成SHA1加密签名=JSAPITicket
	 * @param arr "jsapi_ticket", "noncestr" , "timestamp" , "url"
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String jsGen(String... arr) throws NoSuchAlgorithmException {
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (String a : arr) {
			sb.append(a);
			if (i++ != arr.length - 1) {
				sb.append('&');
			}
		}

		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		sha1.update(sb.toString().getBytes());
		byte[] output = sha1.digest();
		return bytesToHex(output);
	}

	/**
	 * 按list排序生成加密
	 * @param arr
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String genByList(List<String> arr) throws NoSuchAlgorithmException {
		StringBuilder sb = new StringBuilder();
		for (String a : arr) {
			sb.append(a);
		}

		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		sha1.update(sb.toString().getBytes());
		byte[] output = sha1.digest();
		return bytesToHex(output);
	}

	protected static String bytesToHex(byte[] b) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}
}
