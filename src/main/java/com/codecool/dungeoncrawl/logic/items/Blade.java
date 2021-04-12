package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Blade extends Item{
    public Blade(Cell cell) {
        super(cell,50,0,0);
    }

    @Override
    public String getTileName() {
        return "blade";
    }
}
