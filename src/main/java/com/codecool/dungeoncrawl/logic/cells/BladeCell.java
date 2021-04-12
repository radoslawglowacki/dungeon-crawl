package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Blade;

public class BladeCell extends Cell {

    public BladeCell(GameMap gameMap, int x, int y, CellType type) {
        super(gameMap, x, y, type);
        new Blade(this);
    }


}
