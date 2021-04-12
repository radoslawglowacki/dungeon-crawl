package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Armor extends Item{


    public Armor(Cell cell) {
        super(cell,10,0,30);
    }

    @Override
    public String getTileName() {
        return "armor";
    }

}
