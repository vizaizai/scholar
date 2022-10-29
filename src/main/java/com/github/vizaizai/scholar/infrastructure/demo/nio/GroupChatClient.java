package com.github.vizaizai.scholar.infrastructure.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author liaochongwei
 * @date 2022/1/26 14:46
 */
public class GroupChatClient {
    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() throws IOException {
        selector =  Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString();

        System.out.println(username + "is ok...");
    }

    // 向服务器发送消息
    public void sendMsg(String msg) {
        msg = username + "说:" + msg;

        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        }catch (Exception e) {

        }
    }

    // 读取来自服务的的消息
    public void readMsg() {
        try {
            int count = selector.select();
            if (count > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }else {
                        System.out.println("无可以通道");
                    }
                    iterator.remove();
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException {
        GroupChatClient groupChatClient = new GroupChatClient();

       new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(3000);
                }catch (Exception e) {

                }
                groupChatClient.readMsg();
            }
        }).start();

        groupChatClient.sendMsg("hey,im client1");
    }


}
