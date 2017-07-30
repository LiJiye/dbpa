package com.lijiye.dbpa.fetch.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lijiye.dbpa.fetch.Fetch;
import com.lijiye.dbpa.fetch.parameter.GetMatchDetailParameter;
import com.lijiye.dbpa.pojo.MatchDetail;
import com.lijiye.dbpa.type.GameMode;
import com.lijiye.dbpa.type.LeaverStatus;
import com.lijiye.dbpa.type.LobbyType;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lijiye on 17-7-29.
 */
public class GetMatchDetailRequest implements Runnable, Request {
    private static final String INTERVAL = "?";
    private static final String GAME_MODE = "game_mode";
    private static final String LOBBY_TYPE = "lobby_type";
    private static final String HUMAN_PLAYERS = "human_players";
    private static final String LEAVER_STATUS = "leaver_status";
    private static final String MATCH_ID = "match_id";
    private static final String HERO_ID = "hero_id";
    private static final String RESULT = "result";
    private static final String PLAYERS = "players";
    private static final String RADIANT_WIN = "radiant_win";

    private static final Logger logger = LoggerFactory.getLogger(GetMatchDetailParameter.class);
    private Long matchId;
    private String baseUrl;

    private static final String URL = "dota2.url.get_match_detail";

    public GetMatchDetailRequest(Long matchId) {
        this.matchId = matchId;
        this.baseUrl = Fetch.getFetch().getConfiguration().getString(URL);
    }

    @Override
    public void handle(String ret) {
        try {
            JSONObject json = JSON.parseObject(ret).getJSONObject(RESULT);

            long matchId = json.getLong(MATCH_ID);
            if (matchId != this.matchId) {
                logger.warn("Cannot parse the match {} to ${MatchDetail}.", this.matchId);
            }

            boolean radiantWin = json.getBoolean(RADIANT_WIN);

            int humanPlayers = json.getInteger(HUMAN_PLAYERS);
            if (humanPlayers != 10) {
                return;
            }

            LobbyType lobbyType = LobbyType.fromId(json.getInteger(LOBBY_TYPE));
            if (lobbyType == null) return;
            GameMode gameMode = GameMode.fromId(json.getInteger(GAME_MODE));
            if (gameMode == null) return;

            List<Integer> winners = new ArrayList<>();
            List<Integer> losers = new ArrayList<>();
            JSONArray playersTmp = json.getJSONArray(PLAYERS);
            if (radiantWin) {
                if (!setHeroId(winners, playersTmp, 0)) return;
                if (!setHeroId(losers, playersTmp, 5)) return;
            } else {
                if (!setHeroId(winners, playersTmp, 5)) return;
                if (!setHeroId(losers, playersTmp, 0)) return;
            }

            MatchDetail matchDetail = new MatchDetail(winners, losers);
            System.out.println();
        } catch (Exception e) {
            logger.warn(e.toString());
        }
    }

    private boolean setHeroId(List<Integer> dest, JSONArray jsonArray, int offset) {
        for (int i = offset; i < offset + 5; i++) {
            JSONObject player = jsonArray.getJSONObject(i);
            LeaverStatus leaverStatus = LeaverStatus.fromId(player.getInteger(LEAVER_STATUS));
            if (leaverStatus == null) return false;
            dest.add(player.getInteger(HERO_ID));
        }
        return true;
    }

    @Override
    public void run() {
        GetMatchDetailParameter getMatchDetailParameter = new GetMatchDetailParameter(matchId);
        String url = baseUrl + INTERVAL + getMatchDetailParameter.getString();
        org.apache.http.client.fluent.Request request = org.apache.http.client.fluent.Request.Get(url);
        logger.info("Get match detail: " + request.toString());
        HttpEntity entity;
        try {
            entity = request.execute().returnResponse().getEntity();
        } catch (IOException e) {
            logger.error(e.toString());
            throw new UnsupportedOperationException("需要将错误比赛id进行持久化处理");
        }
        String ret;
        try {
            ret = EntityUtils.toString(entity);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new UnsupportedOperationException("需要将错误比赛id进行持久化处理");
        }

        handle(ret);
    }
}
