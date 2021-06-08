package org.greenleaf.netty.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IDGeneratorUtils {

    private static final int DEFAULT_VALUE = 0;

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(DEFAULT_VALUE);

    public static int getMessageId() {
        final int id = ID_GENERATOR.incrementAndGet();

        if (id == Integer.MAX_VALUE) {
            ID_GENERATOR.set(DEFAULT_VALUE);
        }
        return id;
    }
}