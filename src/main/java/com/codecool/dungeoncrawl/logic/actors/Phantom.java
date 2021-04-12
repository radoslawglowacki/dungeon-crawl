package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Phantom extends Actor {
    private final int startX;
    private final int startY;

    public Phantom(Cell cell, int startX, int startY) {
        super(cell,30, 10, 30);
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public String getTileName() {
        return "phantom";
    }


    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }
}
