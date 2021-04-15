package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Diamond;
import com.codecool.dungeoncrawl.logic.items.Heart;
import com.codecool.dungeoncrawl.logic.items.Item;

public class HeartCell extends Cell {

    public HeartCell(GameMap gameMap, int x, int y, CellType type, String mapNumber) {
        super(gameMap, x, y, type);
        new Heart(this, mapNumber);
    }

    @Override
    public Item getItem() {
        return super.getItem();
    }

    @Override
    public void setItem(Item item) {
        super.setItem(item);
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);
        if(actor instanceof Player){
            ((Player) actor).getInventory().addItem(this.getItem());
            this.setItem(null);
        }
    }

}
