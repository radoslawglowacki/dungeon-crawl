package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.opponents.Warrior;
import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.logic.cells.LeverCell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeverCellTest {


    @Test
    void setActorOnLeverAsPlayerGateOpen(){
        int width = 3;
        int height = 3;

        GameMap map = new GameMap(width,height, CellType.FLOOR);

        Cell cellForActor = new Cell(map, 1,1, CellType.FLOOR);
        Cell gate = new Cell(map,2,2,CellType.GATE);
        LeverCell leverCell = new LeverCell(map,0,0,CellType.LEVER);

        map.setCell(2,2,gate);
        map.setCell(0,0,leverCell);

        Player player = new Player(cellForActor);

        leverCell.setActor(player);

        assertEquals(CellType.OPEN_DOORS, gate.getType());
    }


    @Test
    void setActorOnLeverAsNotPlayerGateClose(){
        int width = 3;
        int height = 3;

        GameMap map = new GameMap(3,3, CellType.FLOOR);

        Cell cellForActor = new Cell(map, 1,1, CellType.FLOOR);
        Cell gate = new Cell(map,2,2,CellType.GATE);
        LeverCell leverCell = new LeverCell(map,0,0,CellType.LEVER);
        map.setCell(2,2,gate);
        map.setCell(0,0,leverCell);

        Warrior warrior = new Warrior(cellForActor,1,1,"1");

        leverCell.setActor(warrior);

        assertEquals(CellType.GATE, gate.getType());
    }


}
