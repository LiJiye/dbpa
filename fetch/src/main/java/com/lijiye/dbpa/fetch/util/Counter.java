package com.lijiye.dbpa.fetch.util;

/**
 * Created by lijiye on 17-7-29.
 */
public class Counter {
    private volatile Long count;

    public Counter(Long start) {
        this.count = start;
    }

    public Counter() {
        this.count = 0L;
    }

    public synchronized Long getNext(){
        count++;
        return count;
    }

    public synchronized long getCurrent() {
        return count;
    }
}
