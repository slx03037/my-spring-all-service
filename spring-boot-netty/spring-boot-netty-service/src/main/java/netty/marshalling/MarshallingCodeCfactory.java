package netty.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 14:10
 */
public class MarshallingCodeCfactory {
    /**
     * 创建MarshallingDecoder解码器
     * @return
     */
//    public static MarshallingDecoder buildMarshallingDecoder(){
//        final MarshallerFactory marshallerFactory= Marshalling.getProvidedMarshallerFactory("serial");
//        final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
//        marshallingConfiguration.setVersion(5);
//        DefaultUnmarshallerProvider provider = new
//                DefaultUnmarshallerProvider(marshallerFactory, marshallingConfiguration);
//        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
//        return decoder;
//    }
    public static MarshallingDecoder buildMarshallingDecoder() {
        //首先通过Marshalling 工具类的静态方法获取Marshalling实例对象，参数serial标识创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        //创建 MarshallingConfiguration对象，配置了版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        //根据MarshallerFactory和MarshallingConfiguration 创建 provider
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        //构建Netty的MarshallingDecoder对象，两个参数分别是provider和单个消息序列化后的最大长度
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024 * 1024);

        return decoder;
    }


    /**
     * 创建MarshallingEncoder编码器
     * @return
     */
//    public static MarshallingEncoder buildMarshallingEncoder(){
//        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
//        final MarshallingConfiguration configuration = new MarshallingConfiguration();
//        configuration.setVersion(5);
//        DefaultMarshallerProvider provider = new
//                DefaultMarshallerProvider(marshallerFactory, configuration);
//        MarshallingEncoder encoder = new MarshallingEncoder(provider);
//        return encoder;
//    }

    public static MarshallingEncoder buildMarshallingEncoder() {
        //首先通过Marshalling 工具类的静态方法获取Marshalling实例对象，参数serial标识创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        //创建 MarshallingConfiguration对象，配置了版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        //根据MarshallerFactory和MarshallingConfiguration 创建 provider
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        //构建Netty的MarshallingEncoder对象，MarshallingEncoder用于实现序列化接口的POJO对象序列化为二进制数组
        MarshallingEncoder encoder = new MarshallingEncoder(provider);

        return encoder;
    }
}
