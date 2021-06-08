package org.greenleaf.java.nt.pc;

import java.io.Serializable;

public class Request extends Message<Call> implements Serializable {

    private static final long serialVersionUID = 1L;

    public Request() {
        super();
    }

    public Request(int messageId, byte state, int bodySize, int encryptedBodySize, int compressedBodySize, Call body) {
        super(messageId, state, bodySize, encryptedBodySize, compressedBodySize, body);
    }

    public Request(int messageId, byte state, int bodySize, int encryptedBodySize, int compressedBodySize, Call body,
            SerializeType serializeType) {
        super(messageId, state, bodySize, encryptedBodySize, compressedBodySize, body, serializeType);
    }
}
