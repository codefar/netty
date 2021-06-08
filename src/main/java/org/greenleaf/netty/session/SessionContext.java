package org.greenleaf.netty.session;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyonghua on 19-7-26.
 */
public class SessionContext {

    public static final String KEY_CHANNEL = "KEY_CHANNEL";
    public Channel channel;

    private String id;

    private Map<String, Object> attributes = new HashMap<>();

    public SessionContext(String id) {
        this.id = id;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void addAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public String getId() {
        return id;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
}
