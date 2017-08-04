package com.lijiye.dbpa.fetch.threads;

import com.lijiye.dbpa.fetch.Fetch;
import com.lijiye.dbpa.thrift.MatchDetail;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lijiye on 17-8-4.
 */
public class Sender implements Runnable {
    private LinkedBlockingQueue<MatchDetail> matchDetails;
    private long warnThreshold;
    private long errorThreshold;

    public Sender() {
    }

    @Override
    public void run() {

    }
}
