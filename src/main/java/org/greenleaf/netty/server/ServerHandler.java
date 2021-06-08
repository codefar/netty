package org.greenleaf.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.greenleaf.netty.protocal.HeartBeat;
import org.greenleaf.netty.session.SessionContext;
import org.greenleaf.netty.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    EventExecutorGroup logicGroup = new DefaultEventExecutorGroup(16);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SessionContext session = SessionManager.addSession(ctx.channel().id().asLongText());
        session.addAttribute(SessionContext.KEY_CHANNEL, ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        SessionManager.removeSession(ctx.channel().id().asLongText());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        logger.info("server receive channelRead {}", msg);
        if (msg instanceof HeartBeat) {
            ctx.writeAndFlush(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("exceptionCaught", cause);
    }
}