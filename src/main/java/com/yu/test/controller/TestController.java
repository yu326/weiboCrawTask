package com.yu.test.controller;

import com.yu.test.dao.TaskDao;
import com.yu.test.job.TaskAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by koreyoshi on 2017/8/1.
 */
@Controller
@RequestMapping("test")
public class TestController {
    @Autowired
    private TaskDao taskMapper;


    @RequestMapping("index")
    public String test() {
        Map param = new HashMap();
        Map data = taskMapper.findById(param);
        System.out.println(data);
        return "index";
    }

    @RequestMapping("index1")
    @ResponseBody
    public String test1() {
        TaskAdapter.exceptionTask(1, taskMapper);

        return "index";
    }

}