package com.yu.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.yu.test.config.MongoServiceConfig;
import com.yu.test.dao.TaskDao;
import com.yu.test.job.Statuses;
import com.yu.test.service.CrawWbDataService;
import com.yu.test.service.impl.CrawWbDataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by koreyoshi on 2017/11/9.
 */
@Controller
@RequestMapping("Weibo-api")
public class WeiboController {



    @Autowired
    private CrawWbDataServiceImpl crawWbDataService;


    Logger log = LoggerFactory.getLogger(WeiboController.class);

    /**
     * 抓取数据
     *
     * @return 返回执行结果
     */

    @RequestMapping(produces = "text/xml;charset=utf8", value = "crawData", method = {RequestMethod.POST})
    public
    @ResponseBody
    String crawData() throws UnknownHostException, ParseException {
        Map<String, Boolean> resMap = new HashMap<String, Boolean>(1);
        boolean res = crawWbDataService.execture();
        resMap.put("result", res);
        return resMap.toString();
    }

}
