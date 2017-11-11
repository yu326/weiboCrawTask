package com.yu.test.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.*;


/**
 * Created by koreyoshi on 2017/11/9.
 */
public class MongoServiceConfig {

    private static Resource resource = new ClassPathResource("/mongoService.properties");

    private static MongoServiceConfig config;

    //redisConfigCacheMap
    private static Map<String, String> MongoServiceConfig = new HashMap<String, String>(5);

    private static String OTA_URL;
    private static String OTA_PORT;
    private static String OTA_INTERCECE_NAME;
    private static String OTA_INTERFACE_PARAM;
    private static String OTA_CACHENAME;


    public static void main(String[] args) {
        config.getConfig();
    }

    /**
     * 获取配置文件中redis的信息
     *
     * @return
     */
    public static MongoServiceConfig getConfig() {
        synchronized (MongoServiceConfig.class) {
            if (config != null) {
                return config;
            }
        }
        try {
            config = new MongoServiceConfig();
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            Iterator it = props.keySet().iterator();
            String key;
            Object data;
            while (it.hasNext()) {
                key = (String) it.next();
                if (key.contains("url")) {
                    OTA_URL = (String) props.get(key);
                } else if (key.contains("port")) {
                    OTA_PORT = (String) props.get(key);
                }else if (key.contains("interceceName")) {
                    OTA_INTERCECE_NAME = (String) props.get(key);
                }else if (key.contains("interfaceParam")) {
                    OTA_INTERFACE_PARAM = (String) props.get(key);
                }else if (key.contains("cacheName")) {
                    OTA_CACHENAME = (String) props.get(key);
                }
            }
            System.out.println("here is end~~");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }

    public String getOtaUrl(){
        return OTA_URL;
    }
    public String getOtaPort(){
        return OTA_PORT;
    }
    public String getOtaInterceceName(){
        return OTA_INTERCECE_NAME;
    }
    public String getOtaInterfaceParam(){
        return OTA_INTERFACE_PARAM;
    }
    public String getOtaCachename(){
        return OTA_CACHENAME;
    }
}
