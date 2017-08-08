package com.lijiye.dbpa.fetch.persistence;

import com.lijiye.dbpa.fetch.Fetch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.lijiye.dbpa.fetch.util.Config.CACHE_SIZE;
import static com.lijiye.dbpa.fetch.util.Config.DOTA2_HERO_NUMBER;

public class MatchDetailCaches implements  Runnable{

    private static final Logger logger = LoggerFactory.getLogger(MatchDetailCache.class);

    private int size;
    private List<MatchDetailCache> caches;
    private long lastId;

    public MatchDetailCaches() {
        size = Fetch.getFetch().getConfiguration().getInt(CACHE_SIZE);
        caches = new ArrayList<>(size);
        lastId = 0;
    }

    private int hash(long key) {
        return (int) (key % size);
    }

    public void add(Short win, Short lost, Long matchId) {
        lastId = lastId > matchId ? lastId : matchId;
        int hashValue = hash(matchId);
        caches.get(hashValue).add(win, lost, matchId);
    }


    @Override
    public void run() {
        while (true) {
            for (MatchDetailCache cache: caches) {
                cache.persistence();
            }
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                logger.error(e.toString());
            }
        }
    }
}
