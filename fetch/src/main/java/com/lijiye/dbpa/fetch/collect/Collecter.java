package com.lijiye.dbpa.fetch.collect;

import org.apache.log4j.Logger;

import javax.security.auth.login.Configuration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 用于数据采集，为一个线程池
 *
 * Created by lijiye on 17-7-26.
 */
public class Collecter implements Runnable {
    private int threadNumber = 20;
    private LinkedBlockingQueue<Runnable> queue;
    private final static Logger logger = Logger.getLogger(Collecter.class);

    public Collecter(int threadNumber) {
        queue = new LinkedBlockingQueue<>();
        this.threadNumber = threadNumber;
    }


    @Override
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        while (true) {
            try {
                Runnable runnable = queue.take();
                executorService.execute(runnable);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
    }

    public int put(List<Runnable> runnables) {
        runnables.forEach(runnable -> {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        });
        return queue.size();
    }
}
