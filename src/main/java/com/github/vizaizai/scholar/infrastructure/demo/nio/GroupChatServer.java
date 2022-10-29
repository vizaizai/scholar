package com.github.vizaizai.scholar.infrastructure.demo.nio;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author liaochongwei
 * @date 2022/1/26 13:52
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    // 初始化
    public GroupChatServer() {

        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置为非阻塞模式
            listenChannel.configureBlocking(false);
            // 将ServerSocketChannel注入到selector上，关注OP_ACCEPT事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 监听
    public void listen() {
        try {
            while (true) {
                int count = selector.select(2000);
                // 有事件处理
                if (count > 0 ){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 监听到accept
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            //将socketChannel注入到socketChannel上
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }
                        // 监听到read
                        if (key.isReadable()) {
                            // 处理读
                            this.readData(key);
                        }

                        // 当前key删除。防止重复
                        iterator.remove();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    // 读取消息
    private void readData(SelectionKey key) {
        SocketChannel socketChannel = null;
        try {
            // 获取Channel
            socketChannel = (SocketChannel)key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("from客户端：" + msg);
                // 向其它客户端转发消息
                this.sendToOtherClients(msg, socketChannel);

            }

        }catch (Exception e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() +"离线了");
                // 取消注册
                key.cancel();
                // 关闭通道
                socketChannel.close();
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // 转发给其它客户端
    private void sendToOtherClients(String msg, SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中...");
        // 遍历所有注入到Selector上的selfChannel

        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != selfChannel) {
                SocketChannel dest = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 写入通道
                dest.write(buffer);
            }

        }
    }

    public static void main(String[] args) {

        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
