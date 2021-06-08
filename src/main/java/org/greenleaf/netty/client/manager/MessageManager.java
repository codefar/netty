package org.greenleaf.netty.client.manager;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import io.netty.channel.ChannelFutureListener;
import org.greenleaf.netty.session.SessionContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangyonghua on 2019-08-28.
 */
public final class MessageManager {

    public static final ConcurrentHashMap<Integer, ChannelFutureListener> sessionMap = new ConcurrentHashMap<>();

}
