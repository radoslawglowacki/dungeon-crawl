package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    GameMap map;
    Canvas canvas;
    GraphicsContext context;
    Player player;
    Stage stage;

    static String mapName = "1";
    static String previousMapName = "1";

    int sizeOfGameWindow = 10;
    int offset = sizeOfGameWindow/2;

    Label healthLabel = new Label();
    Label keyLabel = new Label();
    Label bladesLabel = new Label();
    Label armour = new Label();
    Label score = new Label();
    Button pickUp = new Button("Pick Up");



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        setMap(mapName, player);
        MapLoader.updatePlayerOpponents(player);

        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Armour"), 0, 1);
        ui.add(armour, 1, 1);
        ui.add(new Label("Score: "), 0, 2);
        ui.add(score, 1, 2);
        ui.add(new Label("Keys: "), 0, 3);
        ui.add(keyLabel, 1, 3);
        ui.add(new Label("Blades: "), 0, 4);
        ui.add(bladesLabel, 1, 4);
        pickUp.setFocusTraversable(false);
        ui.add(pickUp, 0, 5);


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        pickUp.setOnAction(this::handlePickUp);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void handlePickUp(ActionEvent actionEvent) {
        Item pickedItem = map.getPlayer().getCell().getItem();
        player.getInventory().addItem(pickedItem);
        map.getPlayer().getCell().setItem(null);
        refresh();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case W:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
            case S:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
            case A:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
            case D:
                map.getPlayer().move(1, 0);
                refresh();
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

        healthLabel.setText("" + map.getPlayer().getHealth());
        keyLabel.setText("" + player.getInventory().getKeys());
        bladesLabel.setText("" + player.getInventory().getBlades());
        score.setText("" + player.getScore());
        armour.setText("" + player.getArmor());

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

}
