package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Inventory;
import com.codecool.dungeoncrawl.Opponents;
import com.codecool.dungeoncrawl.logic.cells.Cell;

import java.util.ArrayList;

public class Player extends Actor {
    private Inventory inventory;
    private Opponents opponents;
    private int score;
    private int armor;
    private String playerName;

    public Player(Cell cell) {
        super(cell, 10, 5, 0, "1");
        this.inventory = new Inventory(this);
        this.score = 0;
        this.armor = 0;

    }

    @Override
    public void move(int dx, int dy) {
        try{
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if(nextCell.getActor()!=null) {
            Opponents.fightWithOpponent(nextCell, this.getCell());
        }else{
            super.move(dx, dy);
        }
        }catch (NullPointerException e){
            super.move(dx, dy);
        }
    }

    public String getTileName() {
        return "player";
    }

    public int getArmor() { return armor; }

    public void updateArmor(int armor) { this.armor += armor; }

    public int getScore() { return score; }

    public void updateScore(int score) { this.score += score; }

    public Inventory getInventory() { return inventory; }

    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public void setOpponents(Opponents opponents) { this.opponents = opponents; }

    public Opponents getOpponents() { return opponents; }

    public ArrayList<Actor> getDiedOpponents() { return opponents.getDiedOpponents(); }

    public ArrayList<Actor> getActualOpponents() { return opponents.getActualOpponents(); }


}
