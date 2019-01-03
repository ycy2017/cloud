package com.cn.ycy.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

public class HttpServer {

  private final int port;

  public HttpServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws InterruptedException {
    int port = 9999;
    HttpServer echoServer = new HttpServer(port);
    System.out.println("服务器即将启动");
    echoServer.start();
    System.out.println("服务器关闭");
  }

  public void start() throws InterruptedException {
    final HttpServerHandler serverHandler = new HttpServerHandler();
    /*线程组*/
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      /*服务端启动必须*/
      ServerBootstrap b = new ServerBootstrap();
      b.group(group)
              .channel(NioServerSocketChannel.class)
              /*指定服务器监听端口*/
              .localAddress(new InetSocketAddress(port))
              /*服务端每接收到一个连接请求，就会新启一个socket通信，也就是channel，
              所以下面这段代码的作用就是为这个子channel增加handle*/
              .childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                  // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                  ch.pipeline().addLast(
                          new HttpResponseEncoder());
                  // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                  ch.pipeline().addLast(
                          new HttpRequestDecoder());

                  ch.pipeline().addLast(serverHandler);
                }
              });
      /*异步绑定到服务器，sync()会阻塞直到完成*/
      ChannelFuture f = b.bind().sync();
      f.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }

}
