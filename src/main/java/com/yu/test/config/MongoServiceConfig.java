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
    private static Map<String, String> MongoServiceConfig = new HashMap<String, String>(4);


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




                data = props.get(key);
                System.out.println(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }


    //获取配置文件中的“.”之后的符号，作为redisServiceConfigMap的key值
    private static String getKeyIn(String key) {
        int start = key.indexOf(".") + 1;
        int end = key.lastIndexOf(".");
        String rs = key.substring(start, end);
        return rs;
    }

    public static String getProjectName(String key) {
        int start = key.lastIndexOf(".") + 1;
        int end = key.length();
        String rs = key.substring(start, end);
        return rs;
    }


    public static class projectCache{
        private static List<String> projectCache = new ArrayList<String>(4);
    }

}
