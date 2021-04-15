package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Blade extends Item{
    public Blade(Cell cell, String mapName) {

        super(cell,50,0,0, mapName);
    }

    @Override
    public String getTileName() {
        return "blade";
    }
}
