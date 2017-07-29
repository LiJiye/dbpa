package com.lijiye.dbpa.fetch.request;

import java.io.IOException;

/**
 * Created by lijiye on 17-7-29.
 */
public interface Request {
    void handle(String ret) throws IOException;
}
