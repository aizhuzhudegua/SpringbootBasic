package io.github.wzk.utils;

import io.netty.buffer.ByteBuf;

public class ByteBufToBytes {
    /**
     * 将ByteBuf转换为字节数组
     */
    public byte[] read(ByteBuf datas) {
        byte[] bytes = new byte[datas.readableBytes()];  // 创建与ByteBuf同样大小的字节数组
        datas.readBytes(bytes);  // 将ByteBuf中的数据读取到字节数组中
        return bytes;
    }
}
