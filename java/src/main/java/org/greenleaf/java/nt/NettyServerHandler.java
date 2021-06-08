package org.greenleaf.java.nt;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class NettyServerHandler extends SimpleChannelInboundHandler<Object> {
    static AtomicInteger count = new AtomicInteger(1);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyServerHandler channelActive " + ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("NettyServerHandler channelRead0 " + ctx.channel().id());
        String body = (String) msg;
        System.out.println(count.getAndIncrement() + ":" + body);
        ctx.writeAndFlush("Welcome to Netty.$_");
    }
}