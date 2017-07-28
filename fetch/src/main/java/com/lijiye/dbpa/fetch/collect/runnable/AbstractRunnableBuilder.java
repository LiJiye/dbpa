package com.lijiye.dbpa.fetch.collect.runnable;

import com.lijiye.dbpa.fetch.Fetch;
import com.lijiye.dbpa.fetch.collect.Collecter;

import java.util.List;

/**
 * 数据采集线程的生产者
 *
 * Created by lijiye on 17-7-27.
 */
public abstract class AbstractRunnableBuilder implements Runnable{
    private enum Phase {
        FIRST, SECOND
    }

    private static final String THREAD_NUMBER = "thread.number";

    private int number;
    private int size;
    private Phase phase;
    private Collecter collecter;

    public AbstractRunnableBuilder() {
        number = 1;
        phase = Phase.FIRST;
        size = Fetch.getFetch().getConfiguration().getInt(THREAD_NUMBER) * 2;
        collecter = new Collecter(size);
    }

    public void run() {
        new Thread(collecter).start();
        while (true) {
            List<Runnable> runnables = build(number);
            int ret = collecter.put(runnables);
            if (ret < size) {
                increase();
            } else {
                if (phase == Phase.FIRST) {
                    phase = Phase.SECOND;
                }
                decrease();
            }
        }
    }

    private void decrease() {
        number = number / 2;
    }

    private void increase() {
        if (phase == Phase.FIRST) {
            number = number * 2;
        } else {
            number = number + 1;
        }
    }

    protected abstract List<Runnable> build(int number);
}
