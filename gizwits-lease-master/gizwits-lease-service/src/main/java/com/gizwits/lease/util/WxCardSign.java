package com.gizwits.lease.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WxCardSign {
    public static void main(String[] args) {
        String api_ticket = "9KwiourQPRN3vx3Nn1c_iTb_yMHDXaai2YArsotQ_Lu6ignCuLZFiNUuvYDljqsSPZvu8a81qQ4Af93gVX1LTw";
        String card_id = "pi4yO01GO_hh99iPyLeahJJNcNKY";

        Map<String, String> ret = sign(api_ticket, card_id);
        System.out.println(JSON.toJSONString(ret));
    }

    public static Map<String, String> sign(String api_ticket, String card_id) {
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();

        List<String> stringList = new ArrayList<>();
        stringList.add(api_ticket);
        stringList.add(timestamp);
        stringList.add(nonce_str);
        stringList.add(card_id);
        Collections.sort(stringList);
        String orderedParam = StringUtils.join(stringList, "");
        System.out.println(orderedParam);

        String signature = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(orderedParam.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        Map<String, String> ret = new HashMap<>();
        ret.put("apiTicket", api_ticket);
        ret.put("timestamp", timestamp);
        ret.put("nonceStr", nonce_str);
        ret.put("cardId", card_id);
        ret.put("signature", signature);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
