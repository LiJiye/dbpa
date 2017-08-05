package com.lijiye.dbpa.fetch.builder;

import com.lijiye.dbpa.fetch.Fetch;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.lijiye.dbpa.fetch.Config.THREAD_NUMBER;

/**
 * 数据采集线程的生产者
 *
 * Created by lijiye on 17-7-27.
 */
public abstract class AbstractRunnableBuilder implements Runnable {
    private enum Phase {
        FIRST, SECOND
    }

    private static int size = Fetch.getFetch().getConfiguration().getInt(THREAD_NUMBER) * 2;

    private int number;
    private Phase phase;
    private Collecter collecter;
    private boolean isSuspend;


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
            while (isSuspend) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                }
            }
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

    public void suspend() {
        isSuspend = true;
    }

    public void proceed() {
        isSuspend = false;
    }

}
