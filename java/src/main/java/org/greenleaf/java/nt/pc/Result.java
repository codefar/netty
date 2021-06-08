package org.greenleaf.java.nt.pc;

import java.io.Serializable;

public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息code
     */
    private int code;

    /**
     * 描述信息
     */
    private String message;

    /**
     * 消息内容
     */
    private Object data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result build(ResultEnum resultEnum) {
        return build(resultEnum, null);
    }

    public static Result build(ResultEnum resultEnum, Object data) {
        return new Result(resultEnum.getCode(), resultEnum.getMessage(), data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" + "code=" + code + ", message='" + message + '\'' + ", data=" + data + '}';
    }
}
