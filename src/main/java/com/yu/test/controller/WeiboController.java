package com.yu.test.controller;

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

/**
 * Created by koreyoshi on 2017/11/9.
 */
@Controller
@RequestMapping("Weibo-api")
public class WeiboController {

    @Autowired
    private Statuses statuses;

    @Autowired
    private CrawWbDataServiceImpl crawWbDataService;

    @Autowired
    private TaskDao taskMapper;


    private MongoServiceConfig mongoServiceConfig = MongoServiceConfig.getConfig();

    Logger log = LoggerFactory.getLogger(WeiboController.class);

    /**
     * 抓取数据
     *
     * @param type        类型
     * @param projectName 项目名
     * @return 返回执行结果
     */

    @RequestMapping(produces = "text/xml;charset=utf8", value = "crawData", method = {RequestMethod.POST})
    public
    @ResponseBody
    String crawData(@RequestParam("type") int type, @RequestParam("projectName") String projectName) throws UnknownHostException, ParseException {


            if (type == 1) {
                log.info("here is test info");
                crawWbDataService.execture(taskMapper, statuses, mongoServiceConfig);


//            String uid = "2257089810";
//            String access_token = "2.00SdyOsBnp71ED6984ef87900ZfM2B";
//            boolean res = statuses.getWeiboListByUserId(uid,access_token);

            } else {
                //暂不处理
            }



        return "index";
    }

}
