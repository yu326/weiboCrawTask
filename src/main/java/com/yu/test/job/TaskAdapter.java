package com.yu.test.job;

import com.yu.test.dao.TaskDao;

import java.util.Map;

/**
 * Created by korey on 2017/11/12.
 */
public class TaskAdapter {

    /**
     * 修改异常任务状态
     *
     * @param taskId
     * @param taskMapper
     */
    public static void exceptionTask(int taskId, TaskDao taskMapper) {
        taskMapper.updateExceptionTaskStatus(taskId);

    }

    /**
     * 修改正常任务状态
     *
     * @param taskId
     * @param taskMapper
     */
    public static void normalTask(int taskId, TaskDao taskMapper) {
        taskMapper.updateNormalTaskStatus(taskId);
    }
}
