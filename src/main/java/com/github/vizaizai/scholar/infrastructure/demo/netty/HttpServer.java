package com.github.vizaizai.scholar.infrastructure.demo.netty;

import akka.stream.impl.io.TcpConnectionStage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.util.CharsetUtil;

/**
 * @author liaochongwei
 * @date 2022/2/11 14:42
 */
public class HttpServer {

    public static void main(String[] args) throws InterruptedException {
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
                            ChannelPipeline pipeline = sc.pipeline();

                            //ChannelOutboundHandler
                            // HttpServerCodec,Netty提供的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpServerHandler());


                        }
                    }); // 给workerGroup的EventLoop对应的管道设置处理器
            // 绑定端口并同步
            ChannelFuture cf = bootstrap.bind(8080).sync();
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


    public static class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

        @Override
        protected void channelRead0(ChannelHandlerContext cxf, HttpObject msg) throws Exception {
            if (msg instanceof  HttpRequest) {
                HttpRequest request = (HttpRequest) msg;

                ByteBuf byteBuf = Unpooled.copiedBuffer("Hello,我是Netty", CharsetUtil.UTF_8);
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

                response.headers().add(HttpHeaderNames.CONTENT_TYPE,"text/plain");
                response.headers().add(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
                cxf.writeAndFlush(response);

            }
        }
    }
}
