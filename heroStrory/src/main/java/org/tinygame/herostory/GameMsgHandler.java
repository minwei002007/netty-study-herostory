/*
 * 深圳市灵智数科有限公司版权所有.
 */
package org.tinygame.herostory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明
 * <p>
 *
 * @author minwei
 * @version 1.0.0
 * @date 2020/9/24
 */
public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {


    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);

    /**
     * Is called for each message of type {@link }.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
              LOGGER.info("收到客户端，msg = {}", msg);
    }
}
