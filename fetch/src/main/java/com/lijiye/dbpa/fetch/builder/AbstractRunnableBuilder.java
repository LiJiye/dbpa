package com.lijiye.dbpa.fetch.builder;

import com.lijiye.dbpa.fetch.Fetch;

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
    private static int size = Fetch.getFetch().getConfiguration().getInt(THREAD_NUMBER) * 2;

    private int number;
    private Phase phase;
    private Collecter collecter;


    public AbstractRunnableBuilder(int size) {
        number = 1;
        phase = Phase.FIRST;
        if (size != -1) {
            this.size = size * 2;
        }
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    protected abstract List<Runnable> build(int number);
}
