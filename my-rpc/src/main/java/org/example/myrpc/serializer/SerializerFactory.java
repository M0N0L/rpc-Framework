package org.example.myrpc.serializer;

import org.example.myrpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化器工厂 - 用于获取序列化对象
 */
public class SerializerFactory {
    /**
     * 序列化映射，硬编码实现
     */
//    private static final Map<String, Serializer> KEY_SERIALIZER_MAP = new HashMap<String, Serializer>() {{
//        put(SerializerKeys.JDK, new JdkSerializer());
//        put(SerializerKeys.JSON, new JsonSerializer());
//        put(SerializerKeys.KRYO, new KryoSerializer());
//        put(SerializerKeys.HESSIAN, new HessianSerializer());
//    }};

    /**
     * 获取实例（硬编码实现）
     *
     * @param key
     * @return
     */
//    public static Serializer getInstance(String key) {
//        return KEY_SERIALIZER_MAP.get(key);
//    }

    /**
     * 加载所需要的类
     */
    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
