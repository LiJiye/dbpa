package com.lijiye.dbpa.analyse.thrift;

import com.lijiye.dbpa.thrift.DbpaService;
import com.lijiye.dbpa.thrift.MatchDetail;
import org.apache.thrift.TException;

import java.util.List;

/**
 * Created by lijiye on 17-8-2.
 */
public class DbpaIface implements DbpaService.Iface {
    @Override
    public short add(List<MatchDetail> matchDdetails) throws TException {
        return 0;
    }
}
