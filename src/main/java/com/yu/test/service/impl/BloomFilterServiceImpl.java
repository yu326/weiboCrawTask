package com.yu.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import com.yu.test.config.RedisServiceConfig;
import com.yu.test.job.FilterAdapter;
import com.yu.test.service.BloomFilterService;
import com.yu.test.service.RepositoryFactory;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * Created by koreyoshi on 2017/10/31.
 */
//implements BloomFilterService
@Slf4j
public class BloomFilterServiceImpl implements BloomFilterService {

    public String getFilterParam(JSONObject doc) {
        String filterData = null;
        if (doc.containsKey("text") && !Util.isEmptyString(doc.getString("text"))) {
            filterData = doc.getString("text");
        } else {
            log.error("the error info is:[ the filter data is null ].");
            throw new RuntimeException("the filter data is null");
        }
        return filterData;
    }

    public boolean filterByData(RedisServiceConfig redisServiceConfig, FilterAdapter filterAdapter, String filterData) {
        //获取redis的配置
        String redisUrl = redisServiceConfig.getServiceHost();
        String redisPort = redisServiceConfig.getServicePort();
        String redisAuth = redisServiceConfig.getAuth();
        String redisAuthPassword = redisServiceConfig.getAuthPassword();

        Jedis jedis = RepositoryFactory.getRedisClient(redisUrl, redisPort, redisAuth, redisAuthPassword);
        boolean isExist = filterAdapter.contains(filterData, jedis);
        if (isExist) {
            return true;
        } else {
            filterAdapter.addValue(filterData, jedis);
            return false;
        }
    }
}
