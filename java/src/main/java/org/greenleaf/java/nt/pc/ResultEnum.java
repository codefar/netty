package org.greenleaf.java.nt.pc;

public enum ResultEnum {

    SUCCESS(200, "success"), SERVER_ERROR(5001, "服务内部错误"), INVOKE_REMOTE_FAIL(5002, "调用远程服务失败"), GET_RESULT_FAIL(5003,
            "获取返回结果失败"), CONNECTION_CLOSED(5004,
                    "连接关闭了"), METHOD_NOT_EXITS(5005, "方法不存在"), METHOD_NO_ACCESS(5006, "方法安全权限异常"),
    // 当被调用的方法的内部抛出了异常而没有被捕获时，将由此异常接收
    INVOKE_ERROR(5007, "业务端方法抛出异常"), INVOKE_TIME_OUT(5008, "调用接口超时了"), LOGIN_ERROR(5009,
            "未登录，请重新登录"), PERMISSION_NOT_ALLOWED_ERROR(5010, "无权限访问该接口"), TARGET_CLASS_NOT_REGISTER(5011,
                    "接口实现类未注册成功"), UNSUPPORTED_MESSAGE_TYPE(5012, "不支持该消息类型"), DECODE_MESSAGE_ERROR(5013,
                            "消息解码出现错误"), RECONNECTION_ERROR(5014, "连接异常断开，正在重连...");

    private int code;

    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
