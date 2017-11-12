package com.yu.test.service;

import com.yu.test.config.MongoServiceConfig;
import com.yu.test.dao.TaskDao;
import com.yu.test.job.Statuses;

import java.util.Map;

/**
 * Created by koreyoshi on 2017/11/10.
 */
public interface CrawWbDataService {

    public boolean execture();

    public Map getWbTask(TaskDao taskMapper);
}
