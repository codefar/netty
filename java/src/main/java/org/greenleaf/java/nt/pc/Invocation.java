package org.greenleaf.java.nt.pc;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Map;

public class Invocation {

    private String group;

    private String interfaceName;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    private Map<String, String> attachments;

    private InetSocketAddress remoteAddress;

    private boolean isAsync = false;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public void setAsync(boolean async) {
        isAsync = async;
    }

    @Override
    public String toString() {
        return "Invocation{" + "group='" + group + '\'' + ", interfaceName='" + interfaceName + '\'' + ", methodName='"
                + methodName + '\'' + ", parameterTypes=" + Arrays.toString(parameterTypes) + ", arguments="
                + Arrays.toString(arguments) + ", attachments=" + attachments + ", remoteAddress=" + remoteAddress
                + '}';
    }
}
