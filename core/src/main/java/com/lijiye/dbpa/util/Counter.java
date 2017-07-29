package com.lijiye.dbpa.util;

/**
 * Created by lijiye on 17-7-29.
 */
public class Counter {
    private volatile Long count;

    public Counter(Long start) {
        this.count = start;
    }

    public synchronized Long getNext(){
        count++;
        return count;
    }
}
