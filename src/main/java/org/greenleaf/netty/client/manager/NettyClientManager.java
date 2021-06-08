package org.greenleaf.netty.client.manager;

import com.sun.codemodel.internal.util.UnicodeEscapeWriter;
import com.sun.tools.doclets.formats.html.AbstractExecutableMemberWriter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.greenleaf.netty.client.ClientChannelInitializer;
import org.greenleaf.netty.client.NettyClient;

import javax.net.ssl.SSLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by wangyonghua on 2019-08-28.
 */
public final class NettyClientManager {

    public static final String HOST = System.getProperty("host", "127.0.0.1");
    public static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

    private static final NettyClientManager instance = new NettyClientManager();
    private final FixedChannelPool channelPool;

    private NettyClientManager() {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .handler(new ClientChannelInitializer())
                .remoteAddress(HOST, PORT);
        channelPool = new FixedChannelPool(bootstrap, new ClientChannelPoolHandler(), 4);
    }

    public void resetConnection() {

    }

    public void connection() {

    }

    public void send() {
        // 从连接池拿到连接
        Channel channel = null;
        try {
            channel = this.channelPool.acquire().get();
            // 写出数据
            channel.write("xxxx");
            // 连接放回连接池,这里一定记得放回去
            this.channelPool.release(channel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static NettyClientManager getInstance() {
        return instance;
    }

    public static void main(String[] args) {
    }
}
