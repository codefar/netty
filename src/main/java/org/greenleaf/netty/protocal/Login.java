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
public class Login extends PackageStruct<Login.LoginParam> {


    public Login(LoginParam loginParama) {
        super(PackageType.CMD_LOGIN, loginParama);
    }

    @Data
    public static class LoginParam implements Serializable {
        private String name;
        private String passwd;
    }
}
