package org.greenleaf.netty.protocal;

import lombok.Data;
import lombok.ToString;
import org.greenleaf.netty.utils.IDGeneratorUtils;

import java.io.Serializable;

/**
 * Created by wangyonghua on 19-7-25.
 */
@Data
@ToString
public abstract class PackageStruct<T extends Serializable> implements Serializable {

    public static final int HEADER_SIZE = 10;
    private static final long serialVersionUID = 1L;

    private byte cmd;
    private final byte version = 1;
    private final int  messageId;
    private int bodySize;
    protected T body;

    public PackageStruct(byte cmd, T body) {
        this.cmd = cmd;
        this.body = body;
        messageId = IDGeneratorUtils.getMessageId();
    }
}
