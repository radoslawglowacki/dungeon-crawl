package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Movement;
import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int strength;
    private int points;
    private Movement movement;



    public Actor(Cell cell, int health, int strength, int points) {
        this.cell = cell;
        this.cell.setActor(this);
        this.movement = new Movement();
        this.health = health;
        this.strength = strength;
        this.points = points;

    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if(movement.movementValidator(nextCell, cell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

//    na razie brak potrzeby walidacji
    public void setPosition(int x, int y){
        Cell nextCell = cell.getDirectCell(x,y);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public int getStrength() { return strength; }

    public int getHealth() { return health; }

    public void updateHealth(int value){ health+= value; }

    public int getPoints() { return points; }

    public Cell getCell() { return cell; }

    public void setCell(Cell cell) { this.cell = cell; }

    public int getX() { return cell.getX(); }

    public int getY() { return cell.getY(); }
}
