package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import java.util.ArrayList;


public class OpponentsModel extends BaseModel {
    private ArrayList<Actor> diedOpponents;
    private int playerId;

    public OpponentsModel(ArrayList<Actor> diedOpponents, int id) {
        this.diedOpponents = diedOpponents;
        this.playerId = id;
    }


    public ArrayList<Actor> getDiedOpponents() { return diedOpponents; }

    public int getPlayerId() { return playerId; }

}
