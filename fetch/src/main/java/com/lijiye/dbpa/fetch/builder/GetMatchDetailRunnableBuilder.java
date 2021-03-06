package com.lijiye.dbpa.fetch.builder;

import com.lijiye.dbpa.fetch.Fetch;
import com.lijiye.dbpa.fetch.request.GetMatchDetailRequest;
import com.lijiye.dbpa.fetch.util.Counter;

import java.util.ArrayList;
import java.util.List;

import static com.lijiye.dbpa.fetch.util.Config.GET_MATCH_DETAIL_THREAD_NUMBER;

/**
 * 用于生产${dota2.url.get_match_detail}线程
 *
 * Created by lijiye on 17-7-28.
 */
public class GetMatchDetailRunnableBuilder extends AbstractRunnableBuilder {
    private static final int size = Fetch.getFetch().getConfiguration().getInt(GET_MATCH_DETAIL_THREAD_NUMBER, -1);

    private Counter counter;

    public GetMatchDetailRunnableBuilder(Counter counter) {
        super(size);
        this.counter = counter;
    }

    @Override
    protected List<Runnable> build(int number) {
        List<Runnable> ret = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            ret.add(new GetMatchDetailRequest(counter.getNext()));
        }
        return ret;
    }
}
