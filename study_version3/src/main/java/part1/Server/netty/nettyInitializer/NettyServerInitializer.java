package part1.Server.netty.nettyInitializer;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import lombok.AllArgsConstructor;
import part1.Server.netty.handle.NettyServerHandle;
import part1.Server.provider.ServiceProvider;
import part1.common.serializer.myCode.MyDecoder;
import part1.common.serializer.myCode.MyEncoder;
import part1.common.serializer.mySerializer.JsonSerializer;

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
