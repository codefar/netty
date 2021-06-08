package org.greenleaf.java.nt.pc;

import java.io.Serializable;

public class RpcException extends RuntimeException implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int code;

    public RpcException(String message) {
        super(message);
    }

    public RpcException(int code, String message) {
        super(message);
        this.code = code;
    }

    public RpcException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public RpcException(String message, Throwable t) {
        super(message, t);
    }

    public int getCode() {
        return code;
    }
}