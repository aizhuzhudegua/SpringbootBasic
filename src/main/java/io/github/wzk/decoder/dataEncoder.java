package io.github.wzk.decoder;

import io.github.wzk.entity.dto.transData;
import io.github.wzk.utils.ByteObjConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class dataEncoder extends MessageToByteEncoder<transData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, transData msg, ByteBuf out) throws Exception {
        byte[] datas = ByteObjConverter.ObjectToByte(msg);
        out.writeBytes(datas);
        ctx.flush();
    }
}
