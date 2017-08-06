package com.lijiye.dbpa.fetch.persistence;

import com.lijiye.dbpa.fetch.Fetch;


import static com.lijiye.dbpa.fetch.util.Config.DOTA2_HERO_NUMBER;

public class MatchDetailCache {
    private long[][] cache;
    private int heroNumber;
    private long currentMatchId;

    public MatchDetailCache() {
        heroNumber = Fetch.getFetch().getConfiguration().getInt(DOTA2_HERO_NUMBER) + 1;
    }

    private void init() {
        currentMatchId = 0;
        cache = new long[heroNumber][heroNumber];
    }

    public synchronized void add(Short win, Short lost, long matchId) {
        cache[win][lost]++;
        currentMatchId = matchId;
    }

    public synchronized void persistence() {

    }
}
