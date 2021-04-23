package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.jdbc.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.json.SerializationMenager;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.Tiles;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.opponents.Opponents;
import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.view.RightMenu;
import com.codecool.dungeoncrawl.view.SavePopUp;
import javafx.application.Application;
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
import java.util.ArrayList;


public class Main extends Application {
    private GameMap map;
    private Canvas canvas;
    private GraphicsContext context;
    private Player player;
    private Stage stage;
    private GameDatabaseManager dbManager;
    private SerializationMenager serializationMenager;
    private RightMenu rightMenu;
    private int sizeOfGameWindow = 10;
    private int offset = sizeOfGameWindow / 2;
    static String defaultMap = "1";
    static String mapName = defaultMap;
    static String previousMapName = defaultMap;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        this.stage = primaryStage;
        setMap(mapName, player);
        MapLoader.updatePlayerOpponents(player);
        this.serializationMenager = new SerializationMenager(player);
        this.rightMenu = new RightMenu(player, serializationMenager, this);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(rightMenu);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }


    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        KeyCombination saveCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
        if(saveCombination.match(keyEvent)){
            SavePopUp savePopUp = new SavePopUp(player, dbManager);
            savePopUp.show();
            int idOfPlayerToLoad = savePopUp.getIdOfPlayerToLoad();
            if(idOfPlayerToLoad != -1){
                preparingGameStateFromDBToLoad(idOfPlayerToLoad);
            }
        }

    }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case S:
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case A:
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case D:
            case RIGHT:
                map.getPlayer().move(1, 0);
                refresh();
                break;
        }
    }


    public void refresh() {
        mapChanger();
        Opponents.opponentsMove();
        playerHealthChecker();
        showMap();
        rightMenu.updatePlayerStats();
    }

    private void playerHealthChecker(){
        if (player.getHealth() < 1) {
            this.exit();
        }
    }


    private void showMap(){
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int playerX = player.getX();
        int playerY = player.getY();

        int xStart = getStartPosition(playerX);
        int xEnd = getEndPosition(playerX, map.getWidth());

        int yStart = getStartPosition(playerY);
        int yEnd = getEndPosition(playerY, map.getHeight());

        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x - xStart, y - yStart);
                } else {
                    Tiles.drawTile(context, cell, x - xStart, y - yStart);
                }
            }
        }
    }


    private int getStartPosition(int playerPosition) {
        if (playerPosition - offset < 0) {
            return 0;
        } else {
            return playerPosition - offset;
        }
    }

    private int getEndPosition(int playerPosition, int mapSize) {
        if (playerPosition + offset > mapSize) {
            return mapSize;
        } else {
            return playerPosition + offset;
        }
    }

    public void setMap(String mapName, Player player) {
        this.map = MapLoader.loadMap(mapName, player);
        this.player = map.getPlayer();
        this.canvas = new Canvas(
                sizeOfGameWindow * Tiles.TILE_WIDTH,
                sizeOfGameWindow * Tiles.TILE_WIDTH);
        this.context = canvas.getGraphicsContext2D();
    }


    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();

        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }


    private void mapChanger(){
        if (!player.getMapNumber().equals(previousMapName)) {
            mapName = player.getMapNumber();
            previousMapName = mapName;

            try {
                start(this.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void preparingGameStateFromDBToLoad(int id){
        dbManager.loadState(player, id);

        ArrayList<Item> items = dbManager.getItemsModel().getItems();
        ArrayList<Actor> opponents = dbManager.getOpponentsModel().getDiedOpponents();
        GameStateModel gameStateModel = dbManager.getPlayerModel();
        loadSavedGame(items, opponents, gameStateModel);
    }


    public void loadSavedGame(ArrayList<Item> items, ArrayList<Actor> opponents, GameStateModel gameStateModel ){
        player.setPlayerName(gameStateModel.getPlayerName());
        player.setHealth(gameStateModel.getHp());
        player.setArmor(gameStateModel.getArmor());
        player.setStrength(gameStateModel.getStrength());
        player.setScore(gameStateModel.getScore());
        player.setMapNumber(gameStateModel.getMapName());
        player.getInventory().setItems(items);
        Opponents.setDiedOpponents(opponents);

        setMap(player.getMapNumber(), player);

        previousMapName = "XD";
        mapChanger();
        player.setPosition(gameStateModel.getX(), gameStateModel.getY());
        refresh();
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