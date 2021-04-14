package com.codecool.dungeoncrawl.view;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RightMenu extends GridPane {
    private final Player player;
    private Label healthLabel;
    private Label keyLabel;
    private Label bladesLabel;
    private Label armour;
    private Label score;
    private Button pickUp;
    private TextField name;
    private Button save;


    public RightMenu(Player player){
        super();
        this.player = player;

        setPrefWidth(250);
        setPadding(new Insets(10));

        healthStatus();
        armourStatus();
        scoreStatus();
        keyStatus();
        bladesStatus();
        pickUpButton();
        playerName();
    }

    private void healthStatus(){
        this.healthLabel = new Label();

        add(new Label("Health: "), 0, 1);
        add(healthLabel, 1, 1);
    }

    private void armourStatus(){
        this.armour = new Label();

        add(new Label("Armour"), 0, 2);
        add(armour, 1, 2);
    }

    private void scoreStatus(){
        this.score = new Label();

        add(new Label("Score: "), 0, 3);
        add(score, 1, 3);
    }

    private void keyStatus(){
        this.keyLabel =  new Label();

        add(new Label("Keys: "), 0, 4);
        add(keyLabel, 1, 4);
    }

    private void bladesStatus(){
        this.bladesLabel =  new Label();

        add(new Label("Blades: "), 0, 5);
        add(bladesLabel, 1, 5);
    }

    private void pickUpButton(){
        this.pickUp = new Button("Pick Up");

        add(pickUp, 0, 6);
        pickUp.setFocusTraversable(false);
        pickUp.setOnAction(this::handlePickUp);
    }

    private void playerName(){
        this.name = new TextField("Enter player name");
        this.save = new Button("Save");

        if(player.getPlayerName()==null) {
            add(name, 0, 0);
            add(save, 1, 0);
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    player.setPlayerName(name.getText());
                    getChildren().remove(name);
                    getChildren().remove(save);
                    add(new Label("Player name: "),0,0);
                    add(new Label(player.getPlayerName()),1,0);
                }
            };
            save.setOnAction(event);
        }else{
            add(new Label("Player name: "),0,0);
            add(new Label(player.getPlayerName()),1,0);
        }
    }

    private void handlePickUp(ActionEvent actionEvent) {
        Item pickedItem = player.getCell().getItem();
        player.getInventory().addItem(pickedItem);
        player.getCell().setItem(null);
        pickedItem.getCell().setType(CellType.FLOOR);
        updatePlayerStats();
    }

    public void updatePlayerStats(){
        healthLabel.setText("" + player.getHealth());
        keyLabel.setText("" + player.getInventory().getKeys());
        bladesLabel.setText("" + player.getInventory().getBlades());
        score.setText("" + player.getScore());
        armour.setText("" + player.getArmor());
    }

}
