package org.greenleaf.netty.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangyonghua on 19-7-25.
 */
public final class ProtostuffUtil {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    public static <T> byte[] serialize(T data) throws Exception {
        Schema<T> schema = (Schema<T>) getSchema(data.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate();
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(data, schema, buffer);
        } finally {
            buffer.clear();
        }
        return protostuff;
    }

    /**
     * serializer
     */
    public static <T> byte[] serializer(T message, Class<T> clazz) {
        Schema<T> schema = getSchema(clazz);
        return ProtostuffIOUtil.toByteArray(message, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    /**
     * deserializer
     */
    public static <T> T deserializer(byte[] bytes, Class<T> tClass) {
        Schema<T> schema = getSchema(tClass);
        T message = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, message, schema);
        return message;
    }

    private static <T> Schema<T> getSchema(Class<T> tClass) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(tClass);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(tClass);
            cachedSchema.put(tClass, schema);
        }
        return schema;
    }
}
