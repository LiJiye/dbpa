package com.lijiye.dbpa.util;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lijiye on 17-7-24.
 */
public class HttpUtils {

    public static List<NameValuePair> toNameValyePairs(Map<String, Object> parameters) {
        List<NameValuePair> ret = new ArrayList<>();
        parameters.forEach((key, value) -> ret.add(new BasicNameValuePair(key, value.toString())));
        return ret;
    }

    private static HttpResponse post(@NotNull String url, Map<String, Object> parameters) throws IOException {
        return Request.Post(url).bodyForm(toNameValyePairs(parameters)).execute().returnResponse();
    }

    private static String joinUrl(@NotNull String url, Map<String, Object> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return new String(url);
        }
        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.endsWith("?")) {
            stringBuffer.append("?");
        }
        parameters.forEach((key, value) -> {
            if (value != null) {
                stringBuffer.append("&" + key + "=" + value);
            } else {
                stringBuffer.append("&" + key);
            }
        });
        return stringBuffer.toString();
    }

    public static HttpResponse get(@NotNull String url, Map<String, Object> parameters) throws IOException {
        return Request.Get(joinUrl(url, parameters)).execute().returnResponse();
    }
}
