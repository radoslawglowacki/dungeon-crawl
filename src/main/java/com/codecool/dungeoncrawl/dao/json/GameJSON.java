package com.codecool.dungeoncrawl.dao.json;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.opponents.Phantom;
import com.codecool.dungeoncrawl.logic.actors.opponents.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.opponents.Warrior;
import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.ItemsModel;
import com.codecool.dungeoncrawl.model.OpponentsModel;

import java.util.ArrayList;

public class GameJSON {

    private GameStateModel gameStateModel;
    private ArrayList<ArrayList<String>> listItems;
    private ArrayList<ArrayList<String>> listOpponents;


    public GameJSON(GameStateModel gameStateModel, ItemsModel itemsModel, OpponentsModel opponentsModel) {
        this.gameStateModel = gameStateModel;
        this.listItems = itemsToList(itemsModel);
        this.listOpponents = opponentsToList(opponentsModel);
    }


    private ArrayList<ArrayList<String>> itemsToList(ItemsModel itemsModel){
        ArrayList<ArrayList<String>> allItems = new ArrayList<ArrayList<String>>();
        for (Item item: itemsModel.getItems()) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(item.getTileName());
            temp.add(item.getMapName());
            temp.add("" + item.getX());
            temp.add("" + item.getY());
            if(temp != null) {
                allItems.add(temp);
            }
        }
        return allItems;
    }


    private ArrayList<ArrayList<String>> opponentsToList(OpponentsModel opponentsModel){
        ArrayList<ArrayList<String>> allOpponents = new ArrayList<ArrayList<String>>();
        for (Actor opponent: opponentsModel.getDiedOpponents()) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add("" + opponent.getStartX());
            temp.add("" + opponent.getStartY());
            temp.add(opponent.getMapNumber());
            temp.add(opponent.getTileName());
            temp.add("" + opponent.getX());
            temp.add("" + opponent.getY());
            if(temp != null) {
                allOpponents.add(temp);
            }
        }
        return allOpponents;
    }


    public ArrayList<Item> getPlayerItems(Player player) {
        ArrayList<Item> playerItems = new ArrayList<>();
        GameMap gameMap = player.getCell().getGameMap();
        for (ArrayList element : listItems) {
            String itemName = element.get(0).toString();
            String mapName = element.get(1).toString();
            int x = Integer.parseInt(element.get(2).toString());
            int y = Integer.parseInt(element.get(3).toString());

            switch (itemName) {
                case "armor":
                    playerItems.add(new Armor(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                    break;
                case "blade":
                    playerItems.add(new Blade(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                    break;
                case "diamond":
                    playerItems.add(new Diamond(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                    break;
                case "heart":
                    playerItems.add(new Heart(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                    break;
                case "key":
                    playerItems.add(new Key(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                    break;
                case "openDoors":
                    playerItems.add(new OpenedDoors(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                    break;
            }
        }
        return playerItems;
    }


    public ArrayList<Actor> getdiedOpponents(Player player) {
        ArrayList<Actor> playerDiedOpponents = new ArrayList<>();
        GameMap gameMap = player.getCell().getGameMap();
        for (ArrayList element : listOpponents) {

            int startX = Integer.parseInt(element.get(0).toString());
            int startY = Integer.parseInt(element.get(1).toString());
            String mapName = element.get(2).toString();
            String actorName = element.get(3).toString();
            int x = Integer.parseInt(element.get(4).toString());
            int y = Integer.parseInt(element.get(5).toString());

            switch (actorName) {
                case "skeleton":
                    playerDiedOpponents.add(new Skeleton(new Cell(gameMap, x, y, CellType.FLOOR), startX, startY, mapName));
                    break;
                case "warrior":
                    playerDiedOpponents.add(new Warrior(new Cell(gameMap, x, y, CellType.FLOOR), startX, startY, mapName));
                    break;
                case "phantom":
                    playerDiedOpponents.add(new Phantom(new Cell(gameMap, x, y, CellType.FLOOR), startX, startY, mapName));
                    break;
            }
        }

        return playerDiedOpponents;
    }

    public GameStateModel getGameStateModel() { return gameStateModel; }
}
