package com.cn.ycy.httpserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.*;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/25
 * 类说明：自己的业务处理
 */
@ChannelHandler.Sharable
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

  /*** 服务端读取到网络数据后的处理*/
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg)
          throws Exception {
/*
    FullHttpResponse response = new DefaultFullHttpResponse(
            HTTP_1_1,
            OK,
            Unpooled.wrappedBuffer("欢迎来到猿天地".getBytes("utf-8")));
    response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
    response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
    response.headers().set(RtspHeaders.Names.CONNECTION, RtspHeaders.Values.KEEP_ALIVE);
*/
/*
    ByteBuf content = msg.content();
    byte[] bts = new byte[content.readableBytes()];
    content.readBytes(bts);
    String result = null;
    if(msg.getMethod() == HttpMethod.GET) {
      String url = msg.getUri().toString();
    }*/

    Gson gson = new GsonBuilder().create();

    if (msg instanceof HttpRequest) {
      DefaultHttpRequest request = (DefaultHttpRequest) msg;
      String uri = request.uri();
      if ("/like".equals(uri)) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, OK,
                Unpooled.wrappedBuffer(gson.toJson(new Entity()).getBytes("utf-8"))
        );
        response.headers().set(CONTENT_TYPE, "application/json;charset=UTF-8");
        response.headers().set(CONTENT_LENGTH,response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
      } else {
        return;
      }
    } else {
      return;
    }
  }

  /*** 服务端读取完成网络数据后的处理*/
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx)
          throws Exception {
    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
            .addListener(ChannelFutureListener.CLOSE);
  }

  /*** 发生异常后的处理*/
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
          throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}