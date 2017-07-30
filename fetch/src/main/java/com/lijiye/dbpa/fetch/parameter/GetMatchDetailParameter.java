package com.lijiye.dbpa.fetch.parameter;

import lombok.Data;

import java.util.List;

/**
 * Created by lijiye on 17-7-29.
 */

@Data
public class GetMatchDetailParameter extends Parameter {
    private static final String MATCH_ID = "match_id=";
    private long matchId;

    public GetMatchDetailParameter(long matchId) {
        super();
        this.matchId = matchId;
    }

    @Override
    public List<String> getList() {
        List<String> ret = super.getList();
        ret.add(MATCH_ID + matchId);
        return ret;
    }
}
