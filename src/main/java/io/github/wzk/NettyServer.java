package io.github.wzk;

import io.github.wzk.config.Config;
import io.github.wzk.decoder.dataDecoder;
import io.github.wzk.decoder.dataEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.time.LocalDate;
@Slf4j
@Component
public class NettyServer implements Runnable {
    private final int port;
    private final Config config;
    public NettyServer() {
        this.config = new Config();
        this.port = config.getNettyServerPort();

    }
    @Override
    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(10 * 1024 * 1024, 0, 4, 0, 4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new dataDecoder());
                            pipeline.addLast(new dataEncoder());
                            ch.pipeline().addLast(new MessageHandler());
                        }
                    });
            log.info("Netty server started on port " + port);
            ChannelFuture future = null;
            try {
                future = bootstrap.bind(port).sync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
