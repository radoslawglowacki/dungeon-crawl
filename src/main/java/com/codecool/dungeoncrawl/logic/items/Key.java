package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Key extends Item {

    public Key(Cell cell, String mapName) {
        super(cell,50,0,0, mapName);

    }

    @Override
    public String getTileName() {
        return "key";
    }


}
