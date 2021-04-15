package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Skeleton extends Actor {

    public Skeleton(Cell cell, int startX, int startY, String mapNumber) {
        super(cell,9, 2, 10, mapNumber);
        this.setStartX(startX);
        this.setStartY(startY);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

}