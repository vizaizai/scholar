package com.github.vizaizai.scholar.infrastructure.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.nio.ByteBuffer;

/**
 * @author liaochongwei
 * @date 2022/2/10 14:06
 */
public class Server1 {
    public static void main(String[] args) throws Exception{
        // 创建BossGroup和workerGroup

        // 只处理连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 服务端的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup,workerGroup)// 设置线程组
                    .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel作为通道实现
                    .option(ChannelOption.SO_BACKLOG,128) // 设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)// 设置保持连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象
                        // 给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(new ServerHandler());

                        }
                    }); // 给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("服务器准备好了...");
            // 绑定端口并同步
            ChannelFuture cf = bootstrap.bind(8888).sync();
            // 注册监听器
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()) {
                        System.out.println("绑定端口成功");
                    }
                }
            });

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


    public static class ServerHandler extends ChannelInboundHandlerAdapter {



        /** 读取事件
         * @param ctx 上下文对象
         * @param msg 客户端发送的数据
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            /**
             * 如果是耗时操作可以提交到任务队列（TaskQueue）异步处理
             */


            System.out.println("server ctx= " + ctx);
            // ByteBuf非Nio的ByteBuffer
            ByteBuf buffer = (ByteBuf) msg;
            System.out.println("客户端消息：" + buffer.toString(CharsetUtil.UTF_8));
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());
        }

        /**
         * 数据读取完毕
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端",CharsetUtil.UTF_8));
        }

        /**
         * 发生异常
         * @param ctx
         * @param cause
         * @throws Exception
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
