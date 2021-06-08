package org.greenleaf.netty.client;

import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelHelper {

    private static final AttributeKey<Map<Integer, ResultFuture>> RESULT_FUTURE_KEY = AttributeKey
        .valueOf(ConcurrentHashMap.class.getName());

    private static final AttributeKey<AESCrypt> CRYPT_ATTRIBUTE_KEY = AttributeKey.valueOf(AESCrypt.class.getName());

    public static Map<Integer, ResultFuture> getResultFutureMap(Channel channel) {
        return channel.attr(RESULT_FUTURE_KEY).get();
    }

    public static void setResultFutureMap(Channel channel) {
        channel.attr(RESULT_FUTURE_KEY).set(new ConcurrentHashMap<Integer, ResultFuture>());
    }

    public static void setCryptValue(Channel channel, AESCrypt crypt) {
        channel.attr(CRYPT_ATTRIBUTE_KEY).set(crypt);
    }

    public static AESCrypt getAESCrypt(Channel channel) {
        return channel.attr(CRYPT_ATTRIBUTE_KEY).get();
    }
}