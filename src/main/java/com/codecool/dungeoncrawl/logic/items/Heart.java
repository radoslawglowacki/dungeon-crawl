package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Heart extends Item{

    public Heart(Cell cell) {
        super(cell,20,30,0);
    }

    @Override
    public String getTileName() { return "heart"; }

}
