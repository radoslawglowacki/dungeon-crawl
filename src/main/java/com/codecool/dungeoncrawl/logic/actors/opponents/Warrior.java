package com.codecool.dungeoncrawl.logic.actors.opponents;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Warrior extends Actor {
    private int direction;

    public Warrior(Cell cell, int startX, int startY, String mapNumber) {
        super(cell,15, 5, 20, mapNumber);
        this.direction = 1;
        this.setStartX(startX);
        this.setStartY(startY);
    }

    @Override
    public String getTileName() {
        return "warrior";
    }

    public int getDirection() {
        return direction;
    }

    public void changeDirection() {
        this.direction *= -1;
    }

}