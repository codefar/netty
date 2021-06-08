package org.greenleaf.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.greenleaf.netty.codecs.NettyMessageDecoder;
import org.greenleaf.netty.codecs.NettyMessageEncoder;

/**
 * Created by wangyonghua on 2019-08-27.
 */
public class ServerChannelInitializer extends ChannelInitializer {

    private boolean ssl = false;

    public ServerChannelInitializer() { }

    @Override
    protected void initChannel(Channel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        if (ssl) {
            SelfSignedCertificate ssc = new SelfSignedCertificate("www.davy.com");
            final SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
                    .build();
            System.out.println(sslCtx.cipherSuites());
            System.out.println(sslCtx.applicationProtocolNegotiator());
            pipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));
        }
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast("encode", new NettyMessageEncoder());
        pipeline.addLast("decode", new NettyMessageDecoder());
        pipeline.addLast("business", new ServerHandler());
    }
}
