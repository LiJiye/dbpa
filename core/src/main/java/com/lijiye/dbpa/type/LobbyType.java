package com.lijiye.dbpa.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lijiye on 17-7-30.
 */
public enum LobbyType {
    PUBLIC_MATCHMAKING(0);
    private int id;

    LobbyType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LobbyType fromId(int id) {
        switch (id) {
            case 0:
                return PUBLIC_MATCHMAKING;
            default:
                return null;
        }
    }
}
