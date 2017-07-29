package com.lijiye.dbpa.fetch.request;

import com.lijiye.dbpa.fetch.Fetch;
import com.lijiye.dbpa.fetch.parameter.GetMatchDetailParameter;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by lijiye on 17-7-29.
 */
public class GetMatchDetailRequest implements Runnable, Request {
    private static final String INTERVAL = "?";
    private static final Logger logger = LoggerFactory.getLogger(GetMatchDetailParameter.class);
    private Long matchId;
    private String baseUrl;

    private static final String URL = "dota2.url.get_match_detail";

    public GetMatchDetailRequest(Long matchId) {
        this.matchId = matchId;
        this.baseUrl = Fetch.getFetch().getConfiguration().getString(URL);
    }

    @Override
    public void handle(String ret) throws IOException{
        if (false) {
            throw new IOException();
        }
        logger.debug(ret);

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

        try {
            handle(ret);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new UnsupportedOperationException("需要将错误比赛id进行持久化处理");
        }
    }
}
