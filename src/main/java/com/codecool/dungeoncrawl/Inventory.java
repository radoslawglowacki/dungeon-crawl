package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;


public class Inventory {


private ArrayList<Item> items;
private final Player player;

    public Inventory(Player player) {
        this.items  = new ArrayList<>();
        this.player = player;
    }


private void updateInventory(Player player, Item item){
        player.updateScore(item.getScore());
        player.updateHealth(item.getHealth());
        player.updateArmor(item.getArmor());
}

public void addItem(Item item){
    if(item != null) {
        this.items.add(item);
        updateInventory(player, item);
        item.getCell().setType(CellType.FLOOR);
    }
}

    public int getKeys() {
        int count = 0;
        for (Item item:items) {
            if(item.getTileName().equals("key")){
                count++;
            }
        }
        return count;
    }

    public int getBlades() {
        int count = 0;
        for (Item item:items) {
            if(item.getTileName().equals("blade")){
                count++;
            }
        }
        return count;
    }

    public void setKeys() {
        for (Item item: items) {
            if(items.size()>0) {
                if (item.getTileName().equals("key")) {
                    items.remove(item);
                }
            }
        }
    }
}