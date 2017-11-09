package com.yu.test.controller;

import com.mongodb.*;
import com.yu.test.job.Statuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.UnknownHostException;

/**
 * Created by koreyoshi on 2017/11/9.
 */
@Controller
@RequestMapping("Weibo-api")
public class WeiboController {
    // TODO: 2017/11/9  mongo配置文件写好注入
    // 包括assecc_token

    //// TODO: 2017/11/9 加入任务部分

    @Autowired
    private Statuses statuses;

    /**
     * 抓取数据
     *
     * @param type        类型
     * @param projectName 项目名
     * @return 返回执行结果
     */
    @RequestMapping(produces = "text/xml;charset=utf8", value = "crawData", method = {RequestMethod.POST})
    public String crawData(@RequestParam("type") int type, @RequestParam("projectName") String projectName) throws UnknownHostException {
        if (type == 1) {

            String dbName = "yu";
            String tableName = "test_yu";
            MongoClient mongoClient = null;
            ServerAddress ssAddress = new ServerAddress("127.0.0.1", 40000);
            MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).
                    threadsAllowedToBlockForConnectionMultiplier(50).build();

            mongoClient = new MongoClient(ssAddress, options);

            DB mongoDatabase = mongoClient.getDB(dbName);
            DBCollection collection = mongoDatabase.getCollection(tableName);


            String uid = "5035324520";
            String access_token = "2.00SdyOsBnp71ED6984ef87900ZfM2B";
//            Users um = new Users(access_token);
            boolean res = statuses.showUser(uid,access_token,collection);

        } else {
            //暂不处理
        }


        return "index";
    }

}
