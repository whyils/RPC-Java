package part1.Client.netty.nettyInitializer;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import part1.Client.netty.handle.NettyClientHandle;
import part1.common.serializer.myCode.MyDecoder;
import part1.common.serializer.myCode.MyEncoder;
import part1.common.serializer.mySerializer.JsonSerializer;

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
