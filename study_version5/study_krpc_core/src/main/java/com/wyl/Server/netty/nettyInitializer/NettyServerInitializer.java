package com.wyl.Server.netty.nettyInitializer;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import lombok.AllArgsConstructor;
import com.wyl.Server.netty.handle.NettyServerHandle;
import com.wyl.Server.provider.ServiceProvider;
import com.wyl.serializer.myCode.MyDecoder;
import com.wyl.serializer.myCode.MyEncoder;
import com.wyl.serializer.mySerializer.JsonSerializer;

/**
 * @author WYL
 * @date 2025/2/10 11:20
 * @description: TODO
 */
@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer {

    private ServiceProvider serviceProvider;

    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new MyDecoder());
        pipeline.addLast(new MyEncoder(new JsonSerializer()));
        pipeline.addLast(new NettyServerHandle(serviceProvider));

    }
}
