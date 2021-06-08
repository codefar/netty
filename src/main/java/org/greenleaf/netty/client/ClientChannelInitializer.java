package org.greenleaf.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.greenleaf.netty.codecs.NettyMessageDecoder;
import org.greenleaf.netty.codecs.NettyMessageEncoder;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangyonghua on 2019-08-27.
 */
public class ClientChannelInitializer extends ChannelInitializer {

    EventExecutorGroup logicGroup = new DefaultEventExecutorGroup(16);

    private boolean ssl = false;

    public ClientChannelInitializer() { }

    @Override
    protected void initChannel(Channel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // Add SSL handler first to encrypt and decrypt everything.
        // In this example, we use a bogus certificate in the server side
        // and accept any invalid certificates in the client side.
        // You will need something more complicated to identify both
        // and server in the real world.
        if (ssl) {
            final SslContext sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            pipeline.addLast(sslCtx.newHandler(socketChannel.alloc(), NettyClient.HOST, NettyClient.PORT));
        }
        pipeline.addLast("encode", new NettyMessageEncoder());
        pipeline.addLast("decode", new NettyMessageDecoder());
        pipeline.addLast("IdleStateHandler", new IdleStateHandler(5, 5, 0));
        pipeline.addLast("heartBeatHandler", new HeartBeatHandler());
        pipeline.addLast(new ClientHandler());
    }

}
