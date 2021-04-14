package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.view.RightMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.SQLException;


public class Main extends Application {
    private GameMap map;
    private Canvas canvas;
    private GraphicsContext context;
    private Player player;
    private Stage stage;
    private GameDatabaseManager dbManager;
    private RightMenu rightMenu;
    private int sizeOfGameWindow = 10;
    private int offset = sizeOfGameWindow/2;
    static String mapName = "1";
    static String previousMapName = "1";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        this.stage = primaryStage;
        setMap(mapName, player);
        MapLoader.updatePlayerOpponents(player);
        rightMenu = new RightMenu(player);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(rightMenu);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }


//tu dorobić control s do zapisywania
    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }


//    private void onKeyPressed(KeyEvent keyEvent) {
//        switch (keyEvent.getCode()) {
//            case UP:
//            case W:
//                map.getPlayer().move(0, -1);
//                refresh();
//                break;
//            case DOWN:
//            case S:
//                map.getPlayer().move(0, 1);
//                refresh();
//                break;
//            case LEFT:
//            case A:
//                map.getPlayer().move(-1, 0);
//                refresh();
//                break;
//            case RIGHT:
//            case D:
//                map.getPlayer().move(1, 0);
//                refresh();
//                break;
//        }
//    }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                refresh();
                break;
            case S:
                Player player = map.getPlayer();
                dbManager.savePlayer(player);
                break;
        }
    }


    public void refresh() {
        if(!player.getMapNumber().equals(previousMapName)) {
            previousMapName = mapName;
            mapName = player.getMapNumber();

            try {
                start(this.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        prymitywny koniec gry gdy życie gracza bedzie wynosiło 0
        if(player.getHealth() < 1) {
            Platform.exit();
        }

        Opponents.warriorMove();
        Opponents.phantomMove();

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int playerX = player.getX();
        int playerY = player.getY();

        int xStart = getStartPosition(playerX);
        int xEnd = getEndPosition(playerX, map.getWidth());

        int yStart = getStartPosition(playerY);
        int yEnd =  getEndPosition(playerY, map.getHeight());

        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x-xStart, y-yStart);
                } else {
                    Tiles.drawTile(context, cell, x-xStart, y-yStart);
                }
            }
        }
            rightMenu.updatePlayerStats();
    }

    private int getStartPosition(int playerPosition){
        if(playerPosition - offset < 0){
            return 0;
        }else {
            return playerPosition - offset;
        }
    }

    private int getEndPosition(int playerPosition, int mapSize){
        if(playerPosition + offset > mapSize){
            return mapSize;
        }else {
            return playerPosition + offset;
        }
    }

    public void setMap(String mapName, Player player){
        this.map = MapLoader.loadMap(mapName, player);
        this.canvas = new Canvas(
                sizeOfGameWindow * Tiles.TILE_WIDTH,
                sizeOfGameWindow * Tiles.TILE_WIDTH);
        this.context = canvas.getGraphicsContext2D();
        this.player = map.getPlayer();
    }


    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();

        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }


}
