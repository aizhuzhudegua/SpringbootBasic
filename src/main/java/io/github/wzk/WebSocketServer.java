package io.github.wzk;

import com.alibaba.fastjson.JSON;
import io.github.wzk.core.ApplicationContext;
import io.github.wzk.entity.dto.transData;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author DELL
 * @date 2020/5/20 18:14
 */
@Component
public class WebSocketServer implements Runnable{
    public static List<Channel> arrayList = new ArrayList<>();
    private Integer port = 9002;
    @Override
    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();  //8个

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec());

                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpObjectAggregator(1024*64));

                            //===========================增加心跳支持==============================

                            /**
                             * 针对客户端，如果在1分钟时间内没有向服务端发送读写心跳（ALL），则主动断开连接
                             * 如果有读空闲和写空闲，则不做任何处理
                             */
                            pipeline.addLast(new IdleStateHandler(8,10,12));
                            //自定义的空闲状态检测的handler
                            pipeline.addLast(new HeartBeatHandler());
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                            //自定义的handler
                            pipeline.addLast(new ServerListenerHandler());
                        }
                    });

            System.out.println("netty 服务器启动");
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            executor.scheduleAtFixedRate(() -> {
                for (Channel channel : arrayList) {
                    if (channel.isActive()) {
                        transData data = new transData(ApplicationContext.onlineUsers);
                        TextWebSocketFrame newMsg = new TextWebSocketFrame(JSON.toJSONString(data));
//                onlineUsers.get(id).writeAndFlush(newMsg);
                        channel.writeAndFlush(newMsg);
                    }
                }
            }, 0, 3, TimeUnit.SECONDS);
            //监听关闭
            channelFuture.channel().closeFuture().sync();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
