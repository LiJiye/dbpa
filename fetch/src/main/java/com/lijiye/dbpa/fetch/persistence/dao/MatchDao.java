package com.lijiye.dbpa.fetch.persistence.dao;

import com.lijiye.dbpa.fetch.bean.Match;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by hzlijiye on 2017/8/8.
 */
public interface MatchDao {
    void update(List<Match> matches);
}
