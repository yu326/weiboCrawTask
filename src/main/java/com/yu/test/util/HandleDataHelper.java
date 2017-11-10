package com.yu.test.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.yu.test.util.TimeConvertUtils.chineseStandardTimeToLong;
import static com.yu.test.util.WeiboId2MidUtil.Mid2Uid;

/**
 * Created by koreyoshi on 2017/11/10.
 */
public class HandleDataHelper {


    //文章中需要的字段
    private static final String[] WEIBO_TEXT_FIELDS = {"text", "reposts_count", "comments_count", "attitudes_count", "favorited", "id"};
    private static final String[] WEIBO_TEXT_FIELDS_MAPPING = {"text", "reposts_count", "comments_count", "praises_count", "isFavorite", "id"};
    //用户中需要的字段
    private static final String[] WEIBO_USER_FIELDS = {"id", "screen_name", "description", "gender", "location", "verified", "verified_type", "verified_reason", "followers_count"};
    private static final String[] WEIBO_USER_FIELDS_MAPPING = {"userid", "user_screen_name", "description", "sex", "location", "verify", "verified_type", "verified_reason", "followers_count"};

    //转发中的原创微博需要的字段
    private static final String[] WEIBO_REPOST_FIELDS = {"id", "text"};
    private static final String[] WEIBO_REPOST_FIELDS_MAPPING = {"father_id", "original_text"};

    //微博page_url前缀
    private static final String PAGE_URL_PREFIX = "http://weibo.com";


    public static Map handleData2Mongo(JSONObject data) {
        Map<String, Object> sendData = new HashMap<String, Object>();

        //处理文本中的字段
        for (int i = 0; i < WEIBO_TEXT_FIELDS.length; i++) {
            if (data.containsKey(WEIBO_TEXT_FIELDS[i])) {
                sendData.put(WEIBO_TEXT_FIELDS_MAPPING[i], data.get(WEIBO_TEXT_FIELDS[i]));
            }
        }
        //处理用户字段
        if (data.containsKey("user")) {
            JSONObject user = data.getJSONObject("user");
            for (int i = 0; i < WEIBO_USER_FIELDS.length; i++) {
                if (user.containsKey(WEIBO_USER_FIELDS[i])) {
                    sendData.put(WEIBO_USER_FIELDS_MAPPING[i], user.get(WEIBO_USER_FIELDS[i]));
                }
            }
            //生成page_url
            if (data.containsKey("mid")) {
                String pageUrlSuffix = Mid2Uid(data.getString("mid"));
                sendData.put("page_url", PAGE_URL_PREFIX + "/" + user.get("id") + "/" + pageUrlSuffix);
            }
            //生成用户地址
            if (data.containsKey("id")) {
                sendData.put("user_page_url", PAGE_URL_PREFIX + "/u/" + user.get("id"));
            }
        }
        //处理原创信息  retweeted_status
        if (data.containsKey("retweeted_status")) {
            JSONObject original_data = data.getJSONObject("retweeted_status");
            for (int i = 0; i < WEIBO_REPOST_FIELDS.length; i++) {
                if (original_data.containsKey(WEIBO_REPOST_FIELDS[i])) {
                    sendData.put(WEIBO_REPOST_FIELDS_MAPPING[i], original_data.get(WEIBO_REPOST_FIELDS[i]));
                }
            }
            //生成转发中的原创url
            if (original_data.containsKey("mid")) {
                String pageUrlSuffix = Mid2Uid(original_data.getString("mid"));
                sendData.put("original_url", PAGE_URL_PREFIX + "/" + original_data.getJSONObject("user").get("id") + "/" + pageUrlSuffix);
            }
            sendData.put("content_type", 1);
        } else {
            sendData.put("content_type", 0);
        }
        //处理created_at字段。中国标准时间转化成long类型
        if (data.containsKey("created_at")) {
            long longTime = chineseStandardTimeToLong(data.getString("created_at"));
            sendData.put("created_at", longTime);
        }
        // TODO: 2017/11/10设置source
        if (data.containsKey("source")) {
            String repDocText = data.getString("source").replaceAll("<a.*?>|</a>", "");
            sendData.put("source", repDocText);
        }
        // TODO: 2017/11/10 column，column1配置化。
        //设置column和column1
        sendData.put("column", "微博");
        sendData.put("column1", "云计算_微博");

        //处理用户对象中的字段

        return sendData;
    }

}
