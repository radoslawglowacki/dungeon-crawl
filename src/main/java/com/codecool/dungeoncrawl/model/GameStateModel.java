package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class GameStateModel extends BaseModel {
    private String playerName;
    private int hp;
    private int armor;
    private int strength;
    private int score;
    private String mapName;
    private int x;
    private int y;
    private String saveName;


    public GameStateModel(String playerName, int hp, int armor, int strength, int score, String mapName, int x, int y, String saveName) {
        this.playerName = playerName;
        this.hp = hp;
        this.armor = armor;
        this.strength = strength;
        this.score = score;
        this.mapName = mapName;
        this.x = x;
        this.y = y;
        this.saveName = saveName;
    }

    public GameStateModel(Player player, String saveName) {
        this.playerName = player.getPlayerName();
        this.hp = player.getHealth();
        this.armor = player.getArmor();
        this.strength = player.getStrength();
        this.score = player.getScore();
        this.mapName = player.getMapNumber();
        this.x = player.getX();
        this.y = player.getY();
        this.saveName =saveName;


    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHp() {
        return hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getArmor() { return armor; }

    public int getStrength() { return strength; }

    public int getScore() { return score; }

    public String getMapName() { return mapName; }

    public String getSaveName() { return saveName; }
}
