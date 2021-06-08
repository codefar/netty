package org.greenleaf.netty.client;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.greenleaf.netty.protocal.HeartBeat;

/**
 * Created by wangyonghua on 2019-08-27.
 */
public class HeartBeatHandler extends ChannelDuplexHandler {

    private HeartBeat.Tick tick = new HeartBeat.Tick();

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE || e.state() == IdleState.WRITER_IDLE) {
                tick.increase();
                ctx.writeAndFlush(new HeartBeat(tick));
                System.out.println("send heart beat");;
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
