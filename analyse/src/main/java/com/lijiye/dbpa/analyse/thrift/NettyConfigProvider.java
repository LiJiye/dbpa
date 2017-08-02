package com.lijiye.dbpa.analyse.thrift;

import com.facebook.nifty.core.NettyServerConfig;
import com.facebook.nifty.core.NettyServerConfigBuilder;
import com.lijiye.dbpa.analyse.Analyse;
import com.lijiye.dbpa.analyse.Config;

import javax.inject.Provider;

/**
 * Created by lijiye on 17-8-2.
 */
public class NettyConfigProvider implements Provider<NettyServerConfig> {
    @Override
    public NettyServerConfig get() {
        NettyServerConfigBuilder nettyServerConfigBuilder = new NettyServerConfigBuilder();
        nettyServerConfigBuilder.getSocketChannelConfig().setTcpNoDelay(true);
        int timeout = Analyse.getAnalyse().getConfiguration().getInt(Config.TIMEOUT, Config.DEFAULT_TIMEOUT);
        nettyServerConfigBuilder.getSocketChannelConfig().setConnectTimeoutMillis(timeout);
        return nettyServerConfigBuilder.build();
    }
}
