package com.codecool.dungeoncrawl.logic.cells;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class LeverCell extends Cell {

    public LeverCell(GameMap gameMap, int x, int y, CellType type) {
        super(gameMap, x, y, type);
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);

        if(actor instanceof Player){
            Cell[][] mapCells = actor.getCell().getGameMap().getCells();
            int mapWidth = actor.getCell().getGameMap().getWidth();
            int mapHeight = actor.getCell().getGameMap().getHeight();

            for(int x=0; x< mapWidth; x++){
                for(int y=0; y< mapHeight; y++){
                    if(mapCells[x][y].getType() == CellType.GATE){
                        mapCells[x][y].setType(CellType.OPEN_DOORS);
                    }
                }
            }
        }
    }
}
