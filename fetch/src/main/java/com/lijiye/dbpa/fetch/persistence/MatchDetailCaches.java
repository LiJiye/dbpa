package com.lijiye.dbpa.fetch.persistence;

import com.lijiye.dbpa.fetch.Fetch;

import java.util.ArrayList;
import java.util.List;

import static com.lijiye.dbpa.fetch.util.Config.CACHE_SIZE;
import static com.lijiye.dbpa.fetch.util.Config.DOTA2_HERO_NUMBER;

public class MatchDetailCaches {
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
        caches.get(hashValue).add(win, lost);
    }

    public void count() {
        int heroNumber = Fetch.getFetch().getConfiguration().getInt(DOTA2_HERO_NUMBER) + 1;
        long[][] ret = new long[heroNumber][heroNumber];
        for (MatchDetailCache cache : caches) {
            cache.count(ret);
        }
    }

}
