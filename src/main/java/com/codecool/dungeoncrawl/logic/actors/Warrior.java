package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.cells.Cell;

public class Warrior extends Actor {

    private int dx;

    public Warrior(Cell cell) {
        super(cell,15, 5, 20);
        this.dx = 1;
    }

    @Override
    public String getTileName() {
        return "warrior";
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx *= -dx;
    }
}
