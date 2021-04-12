package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

public class SkeletonCell extends Cell {

    public SkeletonCell(GameMap gameMap, int x, int y, CellType type) {
        super(gameMap, x, y, type);
        new Skeleton(this);
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);

    }

}
