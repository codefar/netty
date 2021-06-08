package org.greenleaf.netty.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import org.greenleaf.netty.client.manager.MessageManager;
import org.greenleaf.netty.protocal.Ack;
import org.greenleaf.netty.protocal.Login;
import org.greenleaf.netty.protocal.PackageStruct;
import org.greenleaf.netty.session.SessionContext;
import org.greenleaf.netty.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientHandler extends ChannelInboundHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    EventExecutorGroup logicGroup = new DefaultEventExecutorGroup(4);

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
        logger.info("channelRead {}", msg);
        if (msg instanceof PackageStruct) {
            PackageStruct packageStruct = (PackageStruct) msg;
            ctx.writeAndFlush(new Ack(packageStruct.getMessageId()));
        }

        ChannelPromise promise = ctx.newPromise();
        ctx.write(msg, promise);
        promise.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {

            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }

    void doLogin() {
        Login.LoginParam param = new Login.LoginParam();
        param.setName("admin");
        param.setPasswd("admin");
        Login login = new Login(param);

        MessageManager.sessionMap.put(login.getMessageId(), new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

            }
        });
    }
}