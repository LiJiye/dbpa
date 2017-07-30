package com.lijiye.dbpa.pojo;

import com.lijiye.dbpa.type.GameMode;
import com.lijiye.dbpa.type.LeaverStatus;
import com.lijiye.dbpa.type.LobbyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by lijiye on 17-7-30.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDetail {
    private List<Integer> winers;
    private List<Integer> losers;
}
