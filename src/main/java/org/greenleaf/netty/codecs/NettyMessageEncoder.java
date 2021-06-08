package org.greenleaf.netty.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.greenleaf.netty.protocal.PackageStruct;
import org.greenleaf.netty.utils.ProtostuffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


public class NettyMessageEncoder extends MessageToByteEncoder<PackageStruct<? extends Serializable>> {

    Logger logger = LoggerFactory.getLogger(NettyMessageEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, PackageStruct<? extends Serializable> msg, ByteBuf out) throws Exception {
        byte[] bodyBytes = ProtostuffUtil.serialize(msg.getBody());
        msg.setBodySize(bodyBytes.length);

        out.writeInt(PackageStruct.HEADER_SIZE + bodyBytes.length);

        out.writeByte(msg.getCmd());
        out.writeByte(msg.getVersion());
        out.writeInt(msg.getMessageId());
        out.writeInt(msg.getBodySize());

        out.writeBytes(bodyBytes);
        logger.info("encode {}", msg);
    }
}