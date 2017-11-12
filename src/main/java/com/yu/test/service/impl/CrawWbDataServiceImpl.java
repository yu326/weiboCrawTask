package com.yu.test.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yu.test.config.MongoServiceConfig;
import com.yu.test.dao.TaskDao;
import com.yu.test.job.Statuses;
import com.yu.test.job.TaskAdapter;
import com.yu.test.sdk.Users;
import com.yu.test.service.CrawWbDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import java.util.*;
import static com.yu.test.util.HandleDataHelper.handleData2Mongo;
import static com.yu.test.util.HandleDataHelper.sendWbdataToMongo;

/**
 * Created by koreyoshi on 2017/11/10.
 */
public class CrawWbDataServiceImpl implements CrawWbDataService {

    @Autowired
    private Statuses statuses;
    @Autowired
    private TaskDao taskMapper;
    private MongoServiceConfig mongoServiceConfig = MongoServiceConfig.getConfig();

    Logger log = LoggerFactory.getLogger(CrawWbDataServiceImpl.class);

    public boolean execture() {

        try {
            Map paramMap = getWbTask(taskMapper);
            if (paramMap == null) {
                log.error("error : In mysql result:[ the field [ select result ]  in null ]");
                throw new RuntimeException("error : In mysql result:[ the select result in null ]");
            }
            if (!paramMap.containsKey("task")) {
                log.error("error : In mysql result:[ the field [ task ]  in null ]");
                throw new RuntimeException("error : In mysql result:[ the field [ task ]  in null ]");
            }
            int taskType = Integer.parseInt(paramMap.get("task").toString());

            if (taskType == 18) {
                String access_token = null;
                if (paramMap.containsKey("access_token")) {
                    access_token = (String) paramMap.get("access_token");
                }
                Users um = new Users(access_token);
                Map taskParam = new HashMap();
                JSONArray uids = null;
                int count = 20;
                int cache_name = 0;
                if (paramMap.containsKey("taskparams")) {
                    taskParam = JSONObject.parseObject((String) paramMap.get("taskparams"));
                    if (taskParam.containsKey("inputtype")) {
                        if (taskParam.get("inputtype").equals("id")) {
                            if (taskParam.containsKey("users")) {
                                uids = (JSONArray) taskParam.get("users");
                            }
                        }
                    }
                    if (taskParam.containsKey("cache_name")) {
                        cache_name = Integer.parseInt(taskParam.get("cache_name").toString());
                    }
                }
                List sendMongoData = new ArrayList();
                for (Object uid : uids) {
                    String StringUid = (String) uid;
                    String response = statuses.getWeiboListByUserId(taskParam, um, StringUid);
                    if (StringUtils.isEmpty(response)) {
                        log.error("error : request weiboApi response is null. userID :[" + uid + "]");
                    }
                    JSONObject data = JSONObject.parseObject(response);
                    JSONArray datas = (JSONArray) data.get("statuses");
                    Iterator iterator = datas.iterator();
                    while (iterator.hasNext()) {
                        JSONObject oneData = (JSONObject) iterator.next();
                        Map<String, Object> sendData = handleData2Mongo(oneData, paramMap);
                        sendMongoData.add(sendData);
                    }
                    boolean res = sendWbdataToMongo(sendMongoData, mongoServiceConfig, cache_name);
                    if (!res) {
                        log.error("the uid [ " + StringUid + " ] send to otaApi is failed");
                    }
                    sendMongoData.clear();
                }
                TaskAdapter.normalTask(Integer.valueOf(paramMap.get("id").toString()), taskMapper);
            } else if (taskType == 17) {
                // TODO: 2017/11/11 抓取关键词
            } else {
                log.error("error : In mysql result:[ the field [ task ]  in null ]");
                throw new RuntimeException("error : In mysql result:[ the field [ task ]  in null ]");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            return true;
        }
    }

    public Map getWbTask(TaskDao taskMapper) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tasktype", 2);
        params.put("taskstatus", 0);
        Map resMap = taskMapper.findById(params);
        return resMap;
    }
}
