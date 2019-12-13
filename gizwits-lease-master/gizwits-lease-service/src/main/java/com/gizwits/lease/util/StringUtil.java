package com.gizwits.lease.util;

/**
 * Description:
 * Created by Sunny on 2019/12/13 14:41
 */
public class StringUtil {

    /**
     * 加密，把一个字符串在原有的基础上+1
     * @param data 需要解密的原字符串
     * @return 返回解密后的新字符串
     */
    public static String encode(String data) {
    //把字符串转为字节数组
        byte[] b = data.getBytes();
        //遍历
        for(int i=0;i<b.length;i++) {
            b[i] += 1;//在原有的基础上+1
        }
        return new String(b);
    }
    /**
     * 解密：把一个加密后的字符串在原有基础上-1
     * @param data 加密后的字符串
     * @return 返回解密后的新字符串
     */
    public static String decode(String data) {
        //把字符串转为字节数组
        byte[] b = data.getBytes();
        //遍历
        for(int i=0;i<b.length;i++) {
            b[i] -= 1;//在原有的基础上-1
        }
        return new String(b);
    }
    public static void main(String[] args) {
        //加密英文
        String data = "mobile_15062323285";
        String result = encode(data);
        System.out.println("加密后:"+result);
        //解密
        String str = decode(result);
        System.out.println("解密后:"+str.substring(7,18));
    }
}
