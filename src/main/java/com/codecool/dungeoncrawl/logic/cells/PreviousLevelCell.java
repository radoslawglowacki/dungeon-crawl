package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class PreviousLevelCell extends Cell{
    public PreviousLevelCell(GameMap gameMap, int x, int y, CellType type) {
        super(gameMap, x, y, type);
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);
        if(actor instanceof Player){
            ((Player) actor).setMapNumber("1");
        }
    }
}
