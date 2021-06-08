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
public class LoginOut extends PackageStruct<LoginOut.LoginOutParam> {

    public LoginOut(LoginOutParam loginOutParam) {
        super(PackageType.CMD_LOGIN_OUT, loginOutParam);
    }

    @Data
    public class LoginOutParam implements Serializable {
        private String name;
        private String passwd;
    }
}
