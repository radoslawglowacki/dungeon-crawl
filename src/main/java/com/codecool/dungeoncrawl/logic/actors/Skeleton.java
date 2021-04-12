package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Skeleton extends Actor {

    public Skeleton(Cell cell) {

        super(cell,9, 2, 10);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
