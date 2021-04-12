package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private GameMap gameMap;
    private int x, y;
    private Item item;

    public Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() { return type; }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() { return actor; }

    public Item getItem() { return item; }

    public void setItem(Item item) { this.item = item; }

    public Cell getNeighbor(int dx, int dy) {
        try {
            return gameMap.getCell(x + dx, y + dy);
        }catch (Exception e){
            return null;
        }
    }

    public Cell getDirectCell(int x, int y){
        return gameMap.getCell(x,y);
    }

    public GameMap getGameMap() { return gameMap; }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() { return x; }

    public int getY() {
        return y;
    }
}
