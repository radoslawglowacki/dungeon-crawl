package com.codecool.dungeoncrawl.logic.cells;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    KEY("key"),
    BLADE("blade"),
    HEART("heart"),
    OPEN_DOORS("openDoors"),
    CLOSED_DOORS("closedDoors"),
    LEVER("lever"),
    DIAMOND("diamond"),
    ARMOR("armor"),
    FIRE("fire"),
    NEXT_LEVEL("nextLevel"),
    PREVIOUS_LEVEL("previousLevel"),
    STONES("stones"),
    GATE("gate");



    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
