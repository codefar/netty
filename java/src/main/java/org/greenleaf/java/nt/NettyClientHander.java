package org.greenleaf.java.nt;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

public class NettyClientHander extends ChannelInboundHandlerAdapter {

    static AtomicInteger count = new AtomicInteger(1);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("NettyClientHander channelRead" + ":" + ctx.channel().id()
         + " " + count.getAndIncrement() + ":" + msg);
    }
}
