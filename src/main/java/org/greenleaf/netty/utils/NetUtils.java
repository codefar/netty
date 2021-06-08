package org.greenleaf.netty.utils;

import java.net.InetSocketAddress;

public class NetUtils {
    /**
     * 地址转string
     *
     * @param address ip & port
     * @return ip:port字符串类型
     */
    public static String toAddressString(InetSocketAddress address) {
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }
}