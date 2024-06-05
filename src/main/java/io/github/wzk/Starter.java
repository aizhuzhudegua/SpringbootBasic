package io.github.wzk;

public class Starter {
    public static void main(String[] args) throws Exception {
        new Thread(new NettyServer()).start();
        new Thread(new WebSocketServer()).start();
    }
}
