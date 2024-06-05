package io.github.wzk.core;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    public static Map<String,ChannelHandlerContext> onlineUsers = new HashMap<String, ChannelHandlerContext>();
    public static void add(String uid,ChannelHandlerContext ctx){
        onlineUsers.put(uid,ctx);
    }

    public static void remove(String uid){
        onlineUsers.remove(uid);
    }

    public static ChannelHandlerContext getContext(String uid){
        return onlineUsers.get(uid);
    }
}
