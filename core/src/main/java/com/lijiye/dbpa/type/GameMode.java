package com.lijiye.dbpa.type;


/**
 * Created by lijiye on 17-7-30.
 */
public enum GameMode {
    ALL_PICK(1),
    CAPTAIN_MODE(2),
    RANDOM_DRAFT(3);

    private int id;

    GameMode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GameMode fromId(int id) {
        switch (id) {
            case 1:
                return ALL_PICK;
            case 2:
                return CAPTAIN_MODE;
            case 3:
                return RANDOM_DRAFT;
            default:
                return null;
        }
    }
}
