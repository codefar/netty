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
public class HeartBeat extends PackageStruct<HeartBeat.Tick> implements Serializable {

    public HeartBeat(Tick tick) {
        super(PackageType.CMD_HEARTBEAT, tick);
    }

    public static class Tick implements Serializable {

        private static final long serialVersionUID = 1L;
        /**
         * 心跳次数统计
         */
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int increase() {
            return ++count;
        }

        @Override
        public String toString() {
            return "{count=" + count + "}";
        }
    }
}
