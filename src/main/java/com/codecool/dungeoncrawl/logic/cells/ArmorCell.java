package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Armor;

public class ArmorCell extends Cell {

    public ArmorCell(GameMap gameMap, int x, int y, CellType type) {
        super(gameMap, x, y, type);
        new Armor(this);
    }
}
