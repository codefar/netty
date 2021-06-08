package org.greenleaf.netty.protocal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by wangyonghua on 19-7-25.
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ack extends PackageStruct implements Serializable {

    public Ack(int messageId) {
        super(PackageType.CMD_ACK, messageId);
    }
}
