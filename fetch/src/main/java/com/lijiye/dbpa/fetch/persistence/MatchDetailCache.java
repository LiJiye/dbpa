package com.lijiye.dbpa.fetch.persistence;

import com.lijiye.dbpa.fetch.Fetch;
import com.lijiye.dbpa.fetch.bean.Match;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;


import java.util.HashMap;
import java.util.Map;

import static com.lijiye.dbpa.fetch.util.Config.DOTA2_HERO_NUMBER;

public class MatchDetailCache {
    private int heroNumber;
    private Map<Pair<Short, Short>, Match> matches;
    private long currentMatchId;

    public MatchDetailCache() {
    }

    private void init() {
        heroNumber = Fetch.getFetch().getConfiguration().getInt(DOTA2_HERO_NUMBER) + 1;
        matches = new HashMap<>();
    }

    public synchronized void add(Short win, Short lost, long matchId) {
        currentMatchId = matchId;
        Pair<Short, Short> pair = new ImmutablePair<>(win, lost);
        Match match = new Match();
        match.setCount(0);
        match.setLost(lost);
        match.setWin(win);
        match = matches.getOrDefault(pair, match);
        match.setCount(match.getCount() + 1);
    }

    public synchronized void persistence() {

    }
}
