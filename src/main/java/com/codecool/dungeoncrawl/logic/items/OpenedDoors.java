package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class OpenedDoors extends Item{


    public OpenedDoors(Cell cell, String mapName) {
        super(cell,50,0,0, mapName);
    }

    @Override
    public String getTileName() {
        return "openDoors";
    }

}
