/*
 * 深圳市灵智数科有限公司版权所有.
 */
package org.tinygame.herostory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务器入口类
 * <p>
 *
 * @author minwei
 * @version 1.0.0
 * @date 2020/7/12
 */
public class ServerMain {

    /**
     * 日志对象
     */
     private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    /**
     * 应用函数入口
     * @param args
     */
    public static void main(String[] args) {

        // 拉客的, 也就是故事中的美女
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 干活的, 也就是故事中的服务生
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup);
            // 服务器信道的处理方式
            b.channel(NioServerSocketChannel.class);
            //客户端处理
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new HttpServerCodec(),
                            new HttpObjectAggregator(65535),
                            new WebSocketServerProtocolHandler("/websocket"),
                            //自定义消息处理器
                            new GameMsgHandler()
                    );
                }
            });

            b.option(ChannelOption.SO_BACKLOG,128);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(12345).sync();
            if (f.isSuccess()) {
               LOGGER.info("服务器启动成功!");
            }
            // 等待服务器信道关闭,
            // 也就是不要立即退出应用程序, 让应用程序可以一直提供服务
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }finally {
              workerGroup.shutdownGracefully();
              bossGroup.shutdownGracefully();
        }

    }
}
