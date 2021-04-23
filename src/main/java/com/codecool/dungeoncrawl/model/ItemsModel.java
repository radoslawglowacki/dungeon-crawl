package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;


public class ItemsModel extends BaseModel {
    private ArrayList<Item> items;
    private int playerId;


    public ItemsModel(ArrayList<Item> items, int id) {
        this. items = items;
        this.playerId = id;
    }

    public ArrayList<Item> getItems() { return items; }

    public int getPlayerId() { return playerId; }

}
