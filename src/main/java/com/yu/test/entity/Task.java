package com.yu.test.entity;

import lombok.Data;

/**
 * Created by koreyoshi on 2017/11/8.
 */
@Data
public class Task {
    private  int tasktype;
    private  int task;
    private  int tasklevel;
    private  int timeout;
    private  int activatetime;
    private  int conflictdelay;
    private  int starttime;
    private  int endtime;
    private  int taskstatus;
    private  String taskparams;
    private  String remarks;
    private  String machine;
}
