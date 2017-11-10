package com.yu.test.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yu.test.sdk.Users;
import com.yu.test.sdk.model.WeiboException;
import com.yu.test.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.yu.test.util.HandleDataHelper.handleData2Mongo;
import static com.yu.test.util.HttpUtils.CONTENT_TYPE_TEXT_XML;

/**
 * Created by koreyoshi on 2017/11/9.
 */
@Slf4j
public class Statuses {



    public boolean getWeiboListByUserId(String uid, String access_token) {
        Users um = new Users(access_token);
        List sendMongoData = new ArrayList();
        try {
            String response = um.getUserWeiboListById(uid);
            log.info("user is:[ " + response + "]");
            if (StringUtils.isEmpty(response)) {
                log.error("the error info: the uid [ " + uid + " ] data is null");
                throw new RuntimeException("the data is null");
            }
            JSONObject data = JSONObject.parseObject(response);
            JSONArray datas = (JSONArray) data.get("statuses");

            Iterator iterator = datas.iterator();
            while (iterator.hasNext()) {
                JSONObject oneData = (JSONObject) iterator.next();
                Map<String, Object> sendData = handleData2Mongo(oneData);
                sendMongoData.add(sendData);
            }


            Map<String, Object> sendDataMap = new HashMap<String, Object>(5);
            sendDataMap.put("currentHost", "192.168.0.165");
            sendDataMap.put("currentPort", "8081");
            sendDataMap.put("page_url", "xxx");
            sendDataMap.put("dictPlan", "[[]]");
            sendDataMap.put("datas", sendMongoData);

            String sendDataString = JSON.toJSONString(sendDataMap);
            String charset = "utf8";
            String content_type = CONTENT_TYPE_TEXT_XML;
            String requstUrl = "http://192.168.0.241:7080/otaapi/DataClean/cleandoc?type=insert&cacheServerName=cache01&isWeibo=true";
            HttpUtils.executePost(sendDataString, charset, requstUrl, 3000 * 1000, content_type);

        } catch (WeiboException e) {
            e.printStackTrace();
        }
        return true;
    }




}
