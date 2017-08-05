package com.lijiye.dbpa.fetch.threads;

import com.lijiye.dbpa.fetch.Fetch;
import com.lijiye.dbpa.thrift.DbpaService;
import com.lijiye.dbpa.thrift.MatchDetail;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static com.lijiye.dbpa.fetch.Config.*;

/**
 * Created by lijiye on 17-8-4.
 */
public class Sender implements Runnable {
    private LinkedBlockingQueue<MatchDetail> matchDetails;
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private volatile long queueSize;
    private long threshold;
    private long size;
    private TTransport transport;
    private TProtocol protocol;

    public Sender() throws TTransportException {
        matchDetails = new LinkedBlockingQueue<>();
        threshold = Fetch.getFetch().getConfiguration().getLong(SENDER_THRESHOLD);
        size = Fetch.getFetch().getConfiguration().getLong(SENDER_SIZE);
        queueSize = 0;
        String host = Fetch.getFetch().getConfiguration().getString(ANALYSE_HOST);
        int port = Fetch.getFetch().getConfiguration().getInt(ANALYSE_PORT);
        transport = new TSocket(host, port);
        transport.open();
        protocol = new TBinaryProtocol(transport);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (Sender.class) {
                if (threshold <= queueSize ) {
                    Fetch.getFetch().getBuilder().suspend();
                } else {
                    Fetch.getFetch().getBuilder().proceed();
                }
            }
            DbpaService.Client client = new DbpaService.Client(protocol);
            List<MatchDetail> details = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                try {
                    details.add(matchDetails.take());
                    synchronized (Sender.class) {
                        queueSize--;
                    }
                } catch (InterruptedException e) {
                    logger.warn(e.toString());
                }
            }
            try {
                client.send_add(details);
            } catch (TException e) {
                logger.error(e.toString());
            }
        }

    }

    public boolean add(MatchDetail matchDetail) {
        synchronized (Sender.class) {
            queueSize++;
        }
        return matchDetails.offer(matchDetail);
    }
}
