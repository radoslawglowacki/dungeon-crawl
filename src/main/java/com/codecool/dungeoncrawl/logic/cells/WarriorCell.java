package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.Warrior;

public class WarriorCell extends Cell {

    public WarriorCell(GameMap gameMap, int x, int y, CellType type) {
        super(gameMap, x, y, type);
        new Warrior(this);
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);


    }

}
