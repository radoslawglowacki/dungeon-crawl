package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Phantom;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

public class PhantomCell extends Cell {

    public PhantomCell(GameMap gameMap, int x, int y, CellType type) {
        super(gameMap, x, y, type);
        new Phantom(this,x,y);
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);


    }

}
