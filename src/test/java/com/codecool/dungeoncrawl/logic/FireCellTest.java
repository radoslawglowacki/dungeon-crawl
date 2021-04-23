package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.logic.actors.opponents.Phantom;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.logic.cells.FireCell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FireCellTest {


    @Test
    void setActorAsPlayer(){
        GameMap map = new GameMap(3,3, CellType.FLOOR);
        Cell cell = new Cell(map, 2,2, CellType.FLOOR);
        Player player = new Player(cell);
        int defaultPlayerHealth = player.getHealth();
        FireCell fireCell = new FireCell(map, 2,2,CellType.FIRE);

        fireCell.setActor(player);
        assertEquals(defaultPlayerHealth-1, player.getHealth());
    }

    @Test
    void setActorAsNotPlayer(){
        GameMap map = new GameMap(3,3, CellType.FLOOR);
        Cell cell = new Cell(map, 2,2, CellType.FLOOR);
        Phantom phantom = new Phantom(cell,2,2,"1");
        int defaultPhantomHealth = phantom.getHealth();
        FireCell fireCell = new FireCell(map, 2,2,CellType.FIRE);

        fireCell.setActor(phantom);
        assertEquals(defaultPhantomHealth, phantom.getHealth());
    }
}
