package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.logic.actors.opponents.Opponents;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.cells.*;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class MapLoader {
    private static ArrayList<Actor> actualOpponents;

    public static GameMap loadMap(String mapName, Player player) {
        InputStream is = MapLoader.class.getResourceAsStream("/" + mapName + ".txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        actualOpponents = new ArrayList<>();


        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            Cell skeletonCell = new SkeletonCell(map, x, y, CellType.FLOOR, mapName);
                            map.setCell(x, y, skeletonCell);
                            actualOpponents.add(cell.getActor());
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            if (player != null) {
                                map.setPlayer(player);
                                player.setCell(cell);
                            } else {
                                map.setPlayer(new Player(cell));
                            }
                            break;
                        case 'p':
                            Cell phantomCell = new PhantomCell(map, x, y, CellType.FLOOR, mapName);
                            map.setCell(x, y, phantomCell);
                            actualOpponents.add(phantomCell.getActor());
                            break;
                        case 'w':
                            Cell warriorCell = new WarriorCell(map, x, y, CellType.FLOOR, mapName);
                            map.setCell(x, y, warriorCell);
                            actualOpponents.add(warriorCell.getActor());
                            break;
                        case 'k':
                            Cell keyCell = new KeyCell(map, x, y, CellType.KEY, mapName);
                            map.setCell(x, y, keyCell);
                            break;
                        case 'b':
                            Cell bladeCell = new BladeCell(map, x, y, CellType.BLADE, mapName);
                            map.setCell(x, y, bladeCell);
                            break;
                        case 'h':
                            Cell heartCell = new HeartCell(map, x, y, CellType.HEART, mapName);
                            map.setCell(x, y, heartCell);
                            break;
                        case 'o':
                            cell.setType(CellType.OPEN_DOORS);
                            break;
                        case 'c':
                            cell.setType(CellType.CLOSED_DOORS);
                            break;
                        case 'l':
                            Cell leverCell = new LeverCell(map, x, y, CellType.LEVER);
                            map.setCell(x, y, leverCell);
                            break;
                        case 'd':
                            Cell diamondCell = new DiamondCell(map, x, y, CellType.DIAMOND, mapName);
                            map.setCell(x, y, diamondCell);
                            break;
                        case 'a':
                            Cell armorCell = new ArmorCell(map, x, y, CellType.ARMOR, mapName);
                            map.setCell(x, y, armorCell);
                            break;
                        case 'f':
                            Cell fireCell = new FireCell(map, x, y, CellType.FIRE);
                            map.setCell(x, y, fireCell);
                            break;
                        case 'g':
                            cell.setType(CellType.GATE);
                            break;
                        case 'n':
                            Cell nextLevelCell = new NextLevelCell(map, x, y, CellType.NEXT_LEVEL);
                            map.setCell(x, y, nextLevelCell);
                            break;
                        case 'm':
                            Cell previousLevelCell = new PreviousLevelCell(map, x, y, CellType.PREVIOUS_LEVEL);
                            map.setCell(x, y, previousLevelCell);
                            break;
                        case 'S':
                            cell.setType(CellType.STONES);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        setCorrectItemsForMap(map, player);
        setCorrectOpponentsForMap(map, player);
        return map;
    }

    public static void updatePlayerOpponents(Player player) {
        player.setOpponents(new Opponents(actualOpponents));
    }

    public static void setCorrectItemsForMap(GameMap map, Player player) {
        if (player != null) {
            ArrayList<Item> items = player.getInventory().getItems();
            if (items.size() > 0) {
                for (Item item : items) {
                    if (item.getMapName().equals(player.getMapNumber())) {
                        if (item.getTileName().equals("openDoors")) {
                            map.getCell(item.getX(), item.getY()).setType(CellType.OPEN_DOORS);
                        } else {
                            map.getCell(item.getX(), item.getY()).setItem(null);
                            map.getCell(item.getX(), item.getY()).setType(CellType.FLOOR);

                        }
                    }
                }
            }
        }
    }


    public static void setCorrectOpponentsForMap(GameMap map, Player player) {
        if (player != null) {
            ArrayList<Actor> diedOpponents = player.getDiedOpponents();
            int mapWidth = map.getWidth();
            int mapHeight = map.getHeight();

            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {
                    Actor actorToCheck = map.getCell(x, y).getActor();
                    if (actorToCheck != null) {
                        for (Actor died : diedOpponents) {
                            if (Objects.equals(actorToCheck.getMapNumber(), died.getMapNumber()))
                                if (actorToCheck.getStartX() == died.getStartX() && actorToCheck.getStartY() == died.getStartY()) {
                                    actorToCheck.getCell().setActor(null);
                                    actualOpponents.remove(actorToCheck);
                                }
                        }
                    }
                }
            }
        }
    }



}
