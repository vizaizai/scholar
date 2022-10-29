package com.github.vizaizai.scholar.infrastructure.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author liaochongwei
 * @date 2022/2/10 14:57
 */
public class Client1 {
    public static void main(String[] args) throws InterruptedException {

        // 客户端需要一个事件循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

       try {
           // 创建客户端启动对象
           Bootstrap bootstrap = new Bootstrap();
           bootstrap.group(eventExecutors)
                   .channel(NioSocketChannel.class)
                   .handler(new ChannelInitializer<SocketChannel>() {

                       @Override
                       protected void initChannel(SocketChannel sc) throws Exception {
                           sc.pipeline().addLast(new ClientHandler());
                       }
                   });
           System.out.println("客户端准备好了");
           ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
           //channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("123123",CharsetUtil.UTF_8));
           channelFuture.channel().closeFuture().sync();
       }finally {
           eventExecutors.shutdownGracefully();
       }

    }

    public static class ClientHandler extends ChannelInboundHandlerAdapter {
        /**
         * 当前通道已就绪
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server", CharsetUtil.UTF_8));
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            ByteBuf byteBuf  = (ByteBuf) msg;
            System.out.println("服务器消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
