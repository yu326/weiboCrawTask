package com.yu.test.dao;

import java.util.Map;

/**
 * Created by koreyoshi on 2017/11/8.
 */

public interface TaskDao {
    Map findById(Map taskMapper);

    Map updateExceptionTaskStatus(int taskId);

    Map updateNormalTaskStatus(int taskId);
}
