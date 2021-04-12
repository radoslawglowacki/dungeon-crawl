package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Key;

public class KeyCell extends Cell {

    public KeyCell(GameMap gameMap, int x, int y, CellType type) {
        super(gameMap, x, y, type);
        new Key(this);
    }


}
