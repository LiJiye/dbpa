package com.lijiye.dbpa.fetch.parameter;

import com.google.common.base.Joiner;
import com.lijiye.dbpa.fetch.Fetch;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijiye on 17-7-28.
 */
@Data
public class Parameter {
    private static final String DOTA2_API_KEY = "dota2.api.key";
    private static final String DOTA2_API_LANGUAGE = "dota2.api.language";
    private static final String DOTA2_API_FORMAT = "dota2.api.format";
    private static final String KEY = "key=";
    private static final String LANGUAGE = "language=";
    private static final String FORMAT = "format=";
    protected static final String INTERVAL = "&";

    private String key;
    private String language;
    private String format;

    public Parameter() {
        key = Fetch.getFetch().getConfiguration().getString(DOTA2_API_KEY);
        language = Fetch.getFetch().getConfiguration().getString(DOTA2_API_LANGUAGE);
        format = Fetch.getFetch().getConfiguration().getString(DOTA2_API_FORMAT);
    }

    public List<String> getList() {
        List<String> ret = new ArrayList<>();
        ret.add(KEY + key);
        ret.add(LANGUAGE + language);
        ret.add(FORMAT + format);
        return ret;
    }

    public String getString() {
        return Joiner.on(INTERVAL).skipNulls().join(getList()).toString();
    }
}
