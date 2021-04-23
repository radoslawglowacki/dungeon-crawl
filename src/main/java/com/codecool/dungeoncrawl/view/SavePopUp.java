package com.codecool.dungeoncrawl.view;

import com.codecool.dungeoncrawl.dao.jdbc.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameStateModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class SavePopUp extends BorderPane {
    private Player player;
    private GameDatabaseManager dbManager;
    private int unreachableNumber = -1;
    private Scene scene;
    private Stage stage;
    private GridPane gridPane;
    private final int width = 600;
    private final int height = 600;
    private Button save;
    private Button load;
    private Button cancel;
    private TextField saveName;
    private Label wrong;
    private Button overwrite;
    private Button goBack;
    private int id = unreachableNumber;
    private GameStateModel gameStateModeltoOverwrite;
    private int idOfPlayerToLoad = unreachableNumber;

    public SavePopUp(Player player, GameDatabaseManager dbManager) {
        super();
        this.player = player;
        this.dbManager = dbManager;
    }

    public void show(){
        this.scene = new Scene(this, width, height);
        this.stage = new Stage();
        this.wrong = new Label();
        this.saveName = new TextField("Enter name of saving game state");
        this.save = new Button("Save");
        this.load = new Button("Load");
        this.cancel = new Button("Cancel");
        this.overwrite = new Button("Overwrite");
        this.goBack = new Button("Go back");

        this.gridPane = new GridPane();
        gridPane.setPrefHeight(width);
        gridPane.setPrefWidth(height);
        menuButtons();
        this.setCenter(gridPane);
        stage.setScene(scene);
        stage.setTitle("Save/load game");
        stage.showAndWait();
    }


    private void menuButtons() {
        GridPane.setHgrow(saveName, Priority.ALWAYS);
        gridPane.add(new Label("Game state name: "), 0, 0);
        gridPane.add(saveName, 1, 0);
        gridPane.add(save, 0, 1);
        gridPane.add(load, 1, 1);
        gridPane.add(cancel, 2, 1);

        save.setOnAction(this::handleClick);
        load.setOnAction(this::handleClick);
        cancel.setOnAction(this::handleClick);
    }

    private void saveMenu() {
        String nameOfSave = saveName.getText();
        List<GameStateModel> allPlayers = dbManager.getAllGameStates();
        boolean flag = false;

        if (!saveName.getText().equals("Enter name of saving game state")) {
            gridPane.getChildren().clear();
            for (GameStateModel player : allPlayers) {
                if (player.getSaveName().equals(nameOfSave)) {
                    flag = true;
                    this.id = player.getId();
                    this.gameStateModeltoOverwrite = player;
                }
            }
            if (flag) {
                gridPane.getChildren().clear();
                gridPane.add(new Label("Game state with that name exists in data base, choose action: "), 0, 0);
                gridPane.add(overwrite, 0, 1);
                gridPane.add(goBack, 1, 1);
            } else {
                dbManager.saveState(player, nameOfSave);
                stage.close();
            }
        } else {
            wrong = new Label("Invalid name");
            wrong.setTextFill(Color.color(1, 0, 0));
            gridPane.add(wrong, 2, 0);
        }

        overwrite.setOnAction(this::handleClick);
        goBack.setOnAction(this::handleClick);
    }

    private void overwriteGameState() {
        dbManager.updateState(player, saveName.getText(), id);
        stage.close();
    }


    private void loadGameState() {
        gridPane.getChildren().clear();
        List<GameStateModel> allPlayers = dbManager.getAllGameStates();

        for (int i = 0; i < allPlayers.size(); i++) {
            GameStateModel playerToDisplay = allPlayers.get(i);
            Button load_this_game_state = new Button("Load this game state");
            load_this_game_state.setId(i + 1 + "");
            gridPane.add(new Label((i + 1 + ". ") + playerToDisplay.getSaveName()), 0, i);
            gridPane.add(new Label(" Player name: " + playerToDisplay.getPlayerName()), 1, i);
            gridPane.add(load_this_game_state, 2, i);
            load_this_game_state.setOnAction(this::handleLoadClick);
        }
        gridPane.add(goBack,0,allPlayers.size());
        goBack.setOnAction(this::handleClick);
    }

    private void handleLoadClick(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        this.idOfPlayerToLoad= Integer.parseInt(source.getId());
        stage.close();
    }

    private void handleClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(save)) {
            saveMenu();
        } else if (actionEvent.getSource().equals(load)) {
            loadGameState();
        } else if (actionEvent.getSource().equals(cancel)) {
            stage.close();
        } else if (actionEvent.getSource().equals(overwrite)) {
            overwriteGameState();
        } else if (actionEvent.getSource().equals(goBack)) {
            gridPane.getChildren().clear();
            menuButtons();
        }
    }

    public int getIdOfPlayerToLoad() { return idOfPlayerToLoad; }
}
