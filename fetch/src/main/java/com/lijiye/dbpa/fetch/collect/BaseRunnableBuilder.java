package com.lijiye.dbpa.fetch.collect;

import java.util.List;

/**
 * Created by lijiye on 17-7-27.
 */
public abstract class BaseRunnableBuilder implements Runnable{
    private enum Phase {
        FIRST, SECOND
    }
    private int number;
    private int size;
    private Phase phase;
    private Collect collect;

    public BaseRunnableBuilder(int size) {
        number = 1;
        phase = Phase.FIRST;
        this.size = size * 2;
        collect = new Collect(size);
    }

    public void run() {
        new Thread(collect).start();
        while (true) {
            List<Runnable> runnables = build(number);
            int ret = collect.put(runnables);
            if (ret < size) {
                increase();
            } else {
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
