package com.yu.test.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yu.test.controller.WeiboController;
import com.yu.test.sdk.Users;
import com.yu.test.sdk.model.WeiboException;
import com.yu.test.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.yu.test.util.HandleDataHelper.handleData2Mongo;
import static com.yu.test.util.HttpUtils.CONTENT_TYPE_TEXT_XML;

/**
 * Created by koreyoshi on 2017/11/9.
 */
public class Statuses {

    private static final Logger logger = LoggerFactory.getLogger(WeiboController.class);

    public String getWeiboListByUserId(Map paramMap,Users um,String uid) {
        int page = 1;
        int each_count = 50;
        if(paramMap.containsKey("page") &&!StringUtils.isEmpty(paramMap.get("page"))){
            page = Integer.valueOf(paramMap.get("page").toString());
        }
        if(paramMap.containsKey("each_count") &&!StringUtils.isEmpty(paramMap.get("each_count"))){
            each_count = Integer.valueOf(paramMap.get("each_count").toString());
        }
        String response = null;
        try {
            response = um.getUserWeiboListById(uid, each_count,page);
        } catch (WeiboException e) {
            e.printStackTrace();
        }


        return response;
    }


}
