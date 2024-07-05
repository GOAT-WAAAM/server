package com.goat.server.notification.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public class FcmJobListener implements JobListener {

    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        log.info("[-] Job이 실행되기전 수행");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        log.info("[-] Job이 실행 취소된 시점 수행");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        log.info("[+] Job이 실행 완료된 시점 수행");
    }
}
