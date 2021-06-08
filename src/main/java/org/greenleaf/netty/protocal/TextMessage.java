package org.greenleaf.netty.protocal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by wangyonghua on 19-7-25.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TextMessage extends PackageStruct<String> {

    public TextMessage(String content) {
        super(PackageType.CMD_MESSAGE, content);
    }
}
