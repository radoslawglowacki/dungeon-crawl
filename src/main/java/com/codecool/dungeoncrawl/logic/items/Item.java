package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable{
    private Cell cell;
    private int score;
    private int health;
    private int armor;


    public Item(Cell cell, int score, int health, int armor){
        this.cell = cell;
        this.cell.setItem(this);
        this.score = score;
        this.health = health;
        this.armor = armor;
    }


    public Cell getCell() { return cell; }

    public void setCell(Cell cell) { this.cell = cell; }

    public int getX() { return cell.getX(); }

    public int getY() { return cell.getY(); }

    @Override
    public String getTileName() { return null; }

    public int getScore() { return score; }

    public int getHealth() { return health; }

    public int getArmor() { return armor; }
}
