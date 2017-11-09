package com.yu.test.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBCollection;
import com.yu.test.sdk.Users;
import com.yu.test.sdk.model.WeiboException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by koreyoshi on 2017/11/9.
 */
@Slf4j
public class Statuses {

    //需要入库到mongo中的字段
    // TODO: 2017/11/9  标注 level字段，是处理得到的，我需要确认逻辑。user_city_code城市吗，还有省包括区码，都是计算得到的。这个工作暂时不做，只存其location字段，类似“广东 广州”。page_url 需要处理得到。

    private final String[] SEND_TOMONGO_TEXT_FIELDS = {"location", "text", "reposts_count", "comments_count", "attitudes_count", "verify", "verified_type", "verified_reason", "created_at", "favorited", "id", "father_id", "original_url", "followers_count"};

    private final String[] SEND_TOMONGO_TEXT_FIELDS_MAPPING = {"location", "text", "reposts_count", "comments_count", "praises_count", "verify", "verified_type", "verified_reason", "created_at", "isFavorite", "id", "father_id", "original_url", "followers_count"};

    private final String[] SEND_TOMONGO_USER_FIELDS = {"screen_name", "description", "gender"};
    private final String[] SEND_TOMONGO_USER_FIELDS_MAPPING = {"user_screen_name", "users_description", "sex"};


    public boolean showUser(String uid, String access_token, DBCollection collection) {
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
        } catch (WeiboException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Map handleData2Mongo(JSONObject data) {
        Map<String, Object> sendData = new HashMap<String, Object>();

        //处理文本中的字段
        for (int i = 0; i < SEND_TOMONGO_TEXT_FIELDS.length; i++) {
            if (data.containsKey(SEND_TOMONGO_TEXT_FIELDS[i]) && !StringUtils.isEmpty(data.get(SEND_TOMONGO_TEXT_FIELDS[i]))) {
                sendData.put(SEND_TOMONGO_TEXT_FIELDS_MAPPING[i], data.get(SEND_TOMONGO_TEXT_FIELDS[i]));
            }
        }
        if (data.containsKey("user")) {
            for (int i = 0; i < SEND_TOMONGO_USER_FIELDS.length; i++) {
                if (data.containsKey(SEND_TOMONGO_USER_FIELDS[i]) && !StringUtils.isEmpty(data.get(SEND_TOMONGO_USER_FIELDS[i]))) {
                    sendData.put(SEND_TOMONGO_USER_FIELDS_MAPPING[i], data.get(SEND_TOMONGO_USER_FIELDS[i]));
                }
            }
        }

        //处理用户对象中的字段

        return sendData;
    }
}
