package com.lijiye.dbpa.fetch.collect;

import com.lijiye.dbpa.fetch.Fetch;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * Created by lijiye on 17-7-28.
 */
@Data
public class BaseParameter {
    private static final String DOTA2_API_KEY = "dota2.api.key";
    private static final String DOTA2_API_LANGUAGE = "dota2.api.language";
    private static final String DOTA2_API_FORMAT = "dota2.api.format";

    private String key;
    private String language;
    private String format;

    public BaseParameter() {
        key = Fetch.getFetch().getConfiguration().getString(DOTA2_API_KEY);
        language = Fetch.getFetch().getConfiguration().getString(DOTA2_API_LANGUAGE);
        format = Fetch.getFetch().getConfiguration().getString(DOTA2_API_FORMAT);
    }
}
