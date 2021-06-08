package org.greenleaf.java.nt.pc;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RpcInvoker implements Invoker {

    private ClientManager clientManager;

    public RpcInvoker() {
        this.clientManager = ClientManager.getInstance();
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        Request request = new Request();
        Call call = new Call();
        call.setService(invocation.getInterfaceName());
        call.setMethod(invocation.getMethodName());
        call.setGroup(invocation.getGroup());
        call.setParams(invocation.getArguments());
        call.setParamTypes(invocation.getParameterTypes());
        call.setAttachment(invocation.getAttachments());

        request.setState(MessageCodec.buildMessageState(MessageType.REQUEST, true, true));
        request.setMessageId(IDGeneratorUtils.getMessageId());
        request.setBody(call);
        Client client = clientManager.getClient();
        int timeout = clientManager.getClientConf().getTimeout();
        // 设置默认的序列化类型
        request.setSerializeType(clientManager.getClientConf().getSerializeType());
        Map<String, String> attachments = invocation.getAttachments();
        if (MapUtils.isNotEmpty(attachments)) {
            String timeOut = attachments.get(invocation.getInterfaceName() + "-timeout");
            if (timeOut != null) {
                timeout = Integer.valueOf(timeOut);
            }

            timeOut = attachments.get(invocation.getMethodName() + "-timeout");
            if (timeOut != null) {
                timeout = Integer.valueOf(timeOut);
            }

            String serializeType = attachments.get(invocation.getInterfaceName() + "-serializeType");

            if (serializeType != null) {
                request.setSerializeType(SerializeType.valueOf(Integer.valueOf(serializeType)));
            }

            serializeType = attachments.get(invocation.getMethodName() + "-serializeType");

            if (serializeType != null) {
                request.setSerializeType(SerializeType.valueOf(Integer.valueOf(serializeType)));
            }
        }

        boolean isAsync = invocation.isAsync();
        Result result = null;
        ResultFuture resultFuture = client.send(request);
        if (isAsync) {
            result = Result.build(ResultEnum.SUCCESS, resultFuture);
        } else {
            try {
                Object resObj = resultFuture.get(timeout, TimeUnit.MILLISECONDS);
                result = Result.build(ResultEnum.SUCCESS, resObj);
            } catch (InterruptedException e) {
                MTLogger.error("future get result interrupted!", e);
                throw new RpcException(ResultEnum.GET_RESULT_FAIL);
            } catch (TimeoutException e) {
                MTLogger.error("get result time out! timeout ->" + timeout, e);
                throw new RpcException(ResultEnum.INVOKE_TIME_OUT);
            }
        }
        return result;
    }
}
