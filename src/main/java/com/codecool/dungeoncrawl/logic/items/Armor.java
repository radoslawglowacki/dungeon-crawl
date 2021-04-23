package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Armor extends Item{
    public Armor(Cell cell, String mapName) {

        super(cell,10,0,30, mapName);
    }

    @Override
    public String getTileName() {
        return "armor";
    }
}
