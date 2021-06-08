package org.greenleaf.netty.session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class SessionManager {

    private static final ConcurrentHashMap<String, SessionContext> sessionMap = new ConcurrentHashMap<>();
    /**
     * 注册Session，并返回新注册的HttpSession对象
     *
     * @return
     */
    public static SessionContext addSession(String sessionId) {
        SessionContext session = new SessionContext(sessionId);
        sessionMap.put(sessionId, session);
        return session;
    }

    /**
     * 判断当前服务端是否有该 session id 的记录
     */
    public static boolean containsSession(String sessionId) {
        return sessionMap.containsKey(sessionId);
    }

    public static SessionContext getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    private static String getSessionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void removeSession(String sessionId) {
        sessionMap.remove(sessionId);
    }
}