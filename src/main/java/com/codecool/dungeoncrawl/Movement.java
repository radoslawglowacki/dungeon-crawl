package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.cells.CellType;


public class Movement {

    public boolean movementValidator(Cell nextCell, Cell actualCell) {
        if (outOfMapChecker(actualCell)) {
            if (cannotMoveIntoWall(nextCell, actualCell)) {
                if (cannotMoveIntoAnotherActor(nextCell)) {
                    if (cannotMoveIntoClosedDoors(nextCell, actualCell) && cannotMoveIntoGate(nextCell)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean cannotMoveIntoAnotherActor(Cell nextCell) {
        Actor actorOfCell = nextCell.getActor();
        if (actorOfCell == null) {
            if (!nextCell.getTileName().equals("warrior")) { return true; }
        }
        return false;
    }

    private boolean outOfMapChecker(Cell actualCell) {
        int height = actualCell.getGameMap().getHeight();
        int width = actualCell.getGameMap().getWidth();
        int nextCellX = actualCell.getX();
        int nextCellY = actualCell.getY();

        if (nextCellX > 0 && nextCellX < width - 1 && nextCellY > 0 && nextCellY < height - 1) { return true; }

        return false;
    }

    private boolean cannotMoveIntoWall(Cell nextCell, Cell actualCell) {
        try {
            if (actualCell.getGameMap().getPlayer().getPlayerName().equalsIgnoreCase("radek")) { return true; }
        }catch (Exception e){ }

        if (!nextCell.getType().getTileName().equals("wall")) { return true; }
        return false;
    }

    private boolean cannotMoveIntoClosedDoors(Cell nextCell, Cell actualCell) {
        if (!nextCell.getType().getTileName().equals("closedDoors")) { return true; }
        else {
            Inventory inventory = actualCell.getGameMap().getPlayer().getInventory();
            if (inventory.getKeys() > 0) {
                nextCell.setType(CellType.OPEN_DOORS);
                inventory.setKeys();
                return true;
            }
        }
        return false;
    }

    private boolean cannotMoveIntoGate(Cell nextCell) {
        if (!nextCell.getType().getTileName().equals("gate")) { return true; }
        return false;
    }

}
