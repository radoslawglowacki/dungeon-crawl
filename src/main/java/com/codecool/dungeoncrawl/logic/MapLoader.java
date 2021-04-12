package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.Opponents;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.cells.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {
    private static ArrayList<Warrior> warriors;
    private static ArrayList<Phantom> phantoms;
    private static ArrayList<Skeleton> skeletons;

    public static GameMap loadMap(String mapName, Player player) {
        InputStream is = MapLoader.class.getResourceAsStream("/" + mapName + ".txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        warriors = new ArrayList<>();
        skeletons = new ArrayList<>();
        phantoms = new ArrayList<>();

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
                            Cell skeletonCell = new SkeletonCell(map, x, y, CellType.FLOOR);
                            map.setCell(x,y, skeletonCell);
                            skeletons.add((Skeleton) cell.getActor());
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            if (player != null){
                                map.setPlayer(player);
                                player.setCell(cell);
                            }else {
                                map.setPlayer(new Player(cell));
                            }
                            break;
                        case 'p':
                            Cell phantomCell = new PhantomCell(map,x,y,CellType.FLOOR);
                            map.setCell(x,y, phantomCell);
                            phantoms.add((Phantom) phantomCell.getActor());
                            break;
                        case 'w':
                            Cell warriorCell = new WarriorCell(map,x,y,CellType.FLOOR);
                            map.setCell(x,y,warriorCell);
                            warriors.add((Warrior) warriorCell.getActor());
                            break;
                        case 'k':
                            Cell keyCell = new KeyCell(map, x, y, CellType.KEY);
                            map.setCell(x,y, keyCell);
                            break;
                        case 'b':
                            Cell bladeCell = new BladeCell(map, x, y, CellType.BLADE);
                            map.setCell(x,y, bladeCell);
                            break;
                        case 'h':
                            Cell heartCell = new HeartCell(map, x, y, CellType.HEART);
                            map.setCell(x,y, heartCell);
                            break;
                        case 'o':
                            cell.setType(CellType.OPEN_DOORS);
                            break;
                        case 'c':
                            cell.setType(CellType.CLOSED_DOORS);
                            break;
                        case 'l':
                            Cell leverCell = new LeverCell(map, x, y, CellType.LEVER);
                            map.setCell(x,y, leverCell);
                            break;
                        case 'd':
                            Cell diamondCell = new DiamondCell(map, x, y, CellType.DIAMOND);
                            map.setCell(x,y, diamondCell);
                            break;
                        case 'a':
                            Cell armorCell = new ArmorCell(map, x,y, CellType.ARMOR);
                            map.setCell(x,y,armorCell);
                            break;
                        case 'f':
                            Cell fireCell = new FireCell(map, x, y, CellType.FIRE);
                            map.setCell(x,y, fireCell);
                            break;
                        case 'g':
                            cell.setType(CellType.GATE);
                            break;
                        case 'n':
                            Cell nextLevelCell = new NextLevelCell(map, x, y, CellType.NEXT_LEVEL);
                            map.setCell(x,y, nextLevelCell);
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
        return map;
    }

    public static void updatePlayerOpponents(Player player){
        ArrayList<ArrayList> allOpponentsList = new ArrayList<>();
        allOpponentsList.add(skeletons);
        allOpponentsList.add(warriors);
        allOpponentsList.add(phantoms);
        player.setOpponents(new Opponents(allOpponentsList));
    }

}
