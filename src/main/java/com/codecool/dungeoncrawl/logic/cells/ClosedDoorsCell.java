package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class ClosedDoorsCell extends Cell {

    public ClosedDoorsCell(GameMap gameMap, int x, int y, CellType type) {
        super(gameMap, x, y, type);
    }


}
