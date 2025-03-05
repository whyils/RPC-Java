package com.wyl.Client.netty.nettyInitializer;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import com.wyl.Client.netty.handle.NettyClientHandle;
import com.wyl.serializer.myCode.MyDecoder;
import com.wyl.serializer.myCode.MyEncoder;
import com.wyl.serializer.mySerializer.JsonSerializer;

/**
 * @author WYL
 * @date 2025/2/8 17:11
 * @description: TODO
 */
public class NettyClientInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new MyDecoder());
        pipeline.addLast(new MyEncoder(new JsonSerializer()));
        pipeline.addLast(new NettyClientHandle());

    }
}
