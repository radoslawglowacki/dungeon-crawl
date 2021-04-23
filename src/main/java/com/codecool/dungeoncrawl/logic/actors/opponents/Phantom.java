package com.codecool.dungeoncrawl.logic.actors.opponents;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Phantom extends Actor {


    public Phantom(Cell cell, int startX, int startY, String mapNumber) {
        super(cell,30, 10, 30, mapNumber);
        this.setStartX(startX);
        this.setStartY(startY);
    }

    @Override
    public String getTileName() {
        return "phantom";
    }

}