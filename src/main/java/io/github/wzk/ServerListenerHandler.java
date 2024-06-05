package io.github.wzk;

import com.alibaba.fastjson.JSON;
import io.github.wzk.core.ApplicationContext;
import io.github.wzk.entity.dto.MessageType;
import io.github.wzk.entity.dto.transData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ServerListenerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端所传输的消息
        String content = msg.text();
        transData obj = JSON.parseObject(content,transData.class);
        // 根据不同的类型来处理不同的业务
        log.info(MessageType.TYPE_AUTH.value()+"");
        if(MessageType.TYPE_AUTH.value() == obj.getMsgType()) {
            log.info("收到来自channel 为[" + obj.getID() + "]的认证消息");
//            ApplicationContext.add(obj.getID(), ctx);
        }
        if(MessageType.TYPE_START.value() == obj.getMsgType()) {
            log.info("收到来自channel 为[" + obj.getID() + "]的认证消息");
//            ApplicationContext.add(obj.getID(), ctx);
        }
//        }else if(MessageType.TYPE_HEARTBEAT.value() == obj.getMsgType()){
//            // System.out.println("收到来自channel 为["+ctx.channel()+"]的心跳包");
//        }else if(MessageType.TYPE_GROUP_CHAT.value() == obj.getMsgType()){
//            System.out.println("收到来自channel 为["+ctx.channel()+"]的群聊消息");
//            Map<String,ChannelHandlerContext> onlineUsers = ApplicationContext.onlineUsers;
//            for(String id:onlineUsers.keySet()) {
//                TextWebSocketFrame newMsg = new TextWebSocketFrame(content);
//                onlineUsers.get(id).writeAndFlush(newMsg);
//            }
//
//        }


    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //接收到请求
        log.info("有新的客户端链接：[{}]", ctx.channel().id().asLongText());
        WebSocketServer.arrayList.add(ctx.channel());
//        UserConnectPool.getChannelGroup().add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String chanelId = ctx.channel().id().asShortText();
        log.info("客户端被移除：channel id 为："+chanelId);
//        UserConnectPool.getChannelGroup().remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生了异常后关闭连接，同时从channelgroup移除
        ctx.channel().close();
//        UserConnectPool.getChannelGroup().remove(ctx.channel());
    }
}

