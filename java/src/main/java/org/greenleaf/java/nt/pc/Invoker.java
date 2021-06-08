package org.greenleaf.java.nt.pc;

public interface Invoker {

    Result invoke(Invocation invocation) throws RpcException;

}