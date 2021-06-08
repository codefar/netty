package org.greenleaf.netty.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.greenleaf.netty.protocal.PackageStruct;
import org.greenleaf.netty.protocal.HeartBeat;
import org.greenleaf.netty.protocal.PackageType;
import org.greenleaf.netty.utils.ProtostuffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by wangyonghua on 2019-08-27.
 */
public class NettyMessageDecoder extends ByteToMessageDecoder {

    Logger logger = LoggerFactory.getLogger(NettyMessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }

        in.markReaderIndex();
        int dataLength = in.readInt();
//        if (dataLength > 1000_000_000) {
//            throw new IllegalStateException("The encoded size exceeds 1GB limit, current size : " + dataLength);
//        } else if (dataLength < 1) {
//            throw new IllegalStateException("The encoded size is too small, current size : " + dataLength);
//        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);

        logger.info("dataLength {} data {}" , dataLength, data);

        // 解析请求header
        ByteBuffer messageBuf = ByteBuffer.wrap(data);
        byte cmd = messageBuf.get();
        byte version = messageBuf.get();
        int messageId = messageBuf.getInt();
        int bodySize = messageBuf.getInt();

        byte[] bodyData = new byte[bodySize];
        messageBuf.get(bodyData);

        logger.info("cmd {} version {} messageId {} bodySize {} bodyData {}", cmd , version , messageId , bodySize, bodyData);

        switch (cmd) {
//            case PackageType.CMD_MESSAGE:
//                out.add(ProtostuffUtil.deserializer(data, TextMessage.class));
//                break;
//            case PackageType.CMD_ACK:
//                out.add(ProtostuffUtil.deserializer(data, Ack.class));
//                break;
            case PackageType.CMD_HEARTBEAT:
                out.add(new HeartBeat(ProtostuffUtil.deserializer(bodyData, HeartBeat.Tick.class)));
                break;
//            case PackageType.CMD_LOGIN:
//                out.add(ProtostuffUtil.deserializer(data, Login.class));
//                break;
//            case PackageType.CMD_LOGIN_OUT:
//                out.add(ProtostuffUtil.deserializer(data, LoginOut.class));
//                break;
            default:
                System.err.println("unkown package");
                break;
        }
    }
}
