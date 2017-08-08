package com.lijiye.dbpa.fetch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hzlijiye on 2017/8/8.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Match {
    private int id;
    private short win;
    private short lost;
    private long count;
}
