package com.lijiye.dbpa.analyse.thrift;

import com.facebook.nifty.core.ThriftServerDefBuilder;
import com.facebook.nifty.guice.NiftyModule;
import com.lijiye.dbpa.analyse.Analyse;
import com.lijiye.dbpa.analyse.Config;
import com.lijiye.dbpa.thrift.DbpaService;

/**
 * Created by lijiye on 17-8-2.
 */
public class DbpaNiftyModule extends NiftyModule {


    @Override
    protected void configureNifty() {
        ThriftServerDefBuilder builder = new ThriftServerDefBuilder();
        int port = Analyse.getAnalyse().getConfiguration().getInt(Config.PORT, Config.DEFAULT_PORT);
        bind().toInstance(builder
                .listen(port)
                .withProcessor(new DbpaService.Processor<>(new DbpaIface())).build());
        withNettyServerConfig(NettyConfigProvider.class);
    }
}
