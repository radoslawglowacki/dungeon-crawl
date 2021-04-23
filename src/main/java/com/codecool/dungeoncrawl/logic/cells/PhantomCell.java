package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.opponents.Phantom;

public class PhantomCell extends Cell {

    public PhantomCell(GameMap gameMap, int x, int y, CellType type, String mapNumber) {
        super(gameMap, x, y, type);
        new Phantom(this,x,y, mapNumber);
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);


    }

}
