package com.lijiye.dbpa.fetch.type;

/**
 * Created by lijiye on 17-7-30.
 */
public enum LeaverStatus {
    NONE(0),
    DISCONNECTED(1);

    private int id;

    LeaverStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LeaverStatus fromId(int id) {
        switch (id) {
            case 0:
                return NONE;
            case 1:
                return DISCONNECTED;
            default:
                return null;
        }
    }
}
