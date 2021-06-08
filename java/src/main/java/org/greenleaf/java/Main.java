package org.greenleaf.java;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by wangyonghua on 2019-08-28.
 */
public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        SortedMap<Object, Object> requestParams = new TreeMap<Object, Object>();
        requestParams.put("version", "1");
        requestParams.put("userName", "123456");
        requestParams.put("password", "123456");

        StringBuffer requestParamsStringBuffer = new StringBuffer();
        System.out.println("排序之后的Key Value：");
        for (Map.Entry<Object, Object> entry : requestParams.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            requestParamsStringBuffer.append(entry.getKey() + "" + entry.getValue());
        }
        System.out.println(requestParamsStringBuffer.toString());
        String md5 = EncryUtils.md5(requestParamsStringBuffer.toString());

        System.out.println("MD5:" + md5);

        KeyPair keyPair = EncryUtils.createRsaKeypair();
        String sign = EncryUtils.createRSASignatureHex(md5.getBytes(), keyPair.getPrivate());

        System.out.println("sign:" + sign);
        System.out.println("sign md5:" + EncryUtils.md5(sign));

        System.out.println(EncryUtils.verifySignature(md5.getBytes(), sign.getBytes(), keyPair.getPublic()));
    }
}
