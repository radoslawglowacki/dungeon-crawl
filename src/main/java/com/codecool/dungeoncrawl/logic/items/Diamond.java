package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Diamond extends Item{

    public Diamond(Cell cell) {
        super(cell, 50,0,0);
    }

    @Override
    public String getTileName() {
        return "diamond";
    }

}
