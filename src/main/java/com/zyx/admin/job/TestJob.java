package com.zyx.admin.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by zhangyuanxin on 2016/5/19.
 */
@Component
public class TestJob implements Job{

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

       LOGGER.info("job executed at " + new Date());
    }
}
