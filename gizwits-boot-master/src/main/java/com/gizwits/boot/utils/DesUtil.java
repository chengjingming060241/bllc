package com.gizwits.boot.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
*@Author rongmc 
*@Date 16/6/29 10:37
* DES安全编码工具类
*/
public  class DesUtil {

	public static final String ALGORITHM = "DES";
	public static final String PRE_SEED="82222777";

	/**
	 * 转换密钥<br>
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	/**
	 * 解密
	 * 
	 * @param encryptStr
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptStr, String key) throws Exception {

		byte[]  data=decryptBASE64(encryptStr);
		if(data == null ){
			return "";
		}
		Key k = toKey(decryptBASE64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		String decryptStr = new String(cipher.doFinal(data));
		return decryptStr;
	}

	/**
	 * 加密
	 * 
	 * @param inputStr
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String inputStr, String key) throws Exception {
		byte[] data = inputStr.getBytes() ;
		Key k = toKey(decryptBASE64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		String encryptStr=encryptBASE64(cipher.doFinal(data));
		return encryptStr;
	}

	/**
	 * 生成密钥
	 * 
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static String initKey(String seed) throws Exception {

		SecureRandom secureRandom = null;

		String seeds = PRE_SEED+seed ;

		secureRandom = new SecureRandom(seeds.getBytes());

		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
		kg.init(secureRandom);

		SecretKey secretKey = kg.generateKey();

		String key = encryptBASE64(secretKey.getEncoded());
		System.err.println("密钥:" + key);

		return key;

	}


	/**
	 * BASE64解密
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] decryptBASE64(String key) throws Exception {
		byte[] keyByte= Base64Util.decode(key);
		return keyByte;
	}

	/**
	 * BASE64加密
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static String encryptBASE64(byte[] key) throws Exception {
		return Base64Util.encode(key);
	}
}
