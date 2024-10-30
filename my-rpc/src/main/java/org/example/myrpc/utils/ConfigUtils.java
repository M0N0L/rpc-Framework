package org.example.myrpc.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;
import org.example.myrpc.config.RpcConfig;
import org.springframework.context.ApplicationContext;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * 配置工具类
 */
public class ConfigUtils {

    /**
     * 加载配置对象
     *
     * @param tClass
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        T loadConfig;
        ClassLoader classLoader = ConfigUtils.class.getClassLoader();
        // 判断yaml 文件是否存在
        InputStream yamlStream = classLoader.getResourceAsStream("application.yaml");
        InputStream ymlStream = classLoader.getResourceAsStream("application.yml");

        if(yamlStream != null || ymlStream != null) {
            loadConfig = loadYamlConfig(tClass, "");
        } else {
            loadConfig = loadPropConfig(tClass, prefix, "");
        }
        return loadConfig;
    }

    /**
     * 加载配置对象（支持区分环境）
     *
     * @param tClass
     * @param prefix
     * @param environment
     * @param <T>
     * @return
     */
    public static <T> T loadPropConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        return props.toBean(tClass, prefix);
    }

    /**
     * 加载Yaml配置文件
     * @param tClass
     * @param environment
     * @return
     * @param <T>
     */
    public static <T> T loadYamlConfig(Class<T> tClass, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".yaml");

        return YamlUtil.loadByPath(configFileBuilder.toString(), tClass);
    }
}
