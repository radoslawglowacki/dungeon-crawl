package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;


public class Inventory {


    private ArrayList<Item> items;
    private ArrayList<Item> keys;
    private final Player player;

    public Inventory(Player player) {
        this.items = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.player = player;
    }


    private void updateInventory(Player player, Item item) {
        player.updateScore(item.getScore());
        player.updateHealth(item.getHealth());
        player.updateArmor(item.getArmor());
    }

    public void addItem(Item item) {
        if (item != null) {
            this.items.add(item);
            if(item.getTileName().equals("key")){
                keys.add(item);
            } else if(item.getTileName().equals("blade")){
                player.setStrength(5);
            }
            System.out.println(item.getTileName());
            updateInventory(player, item);
            item.getCell().setType(CellType.FLOOR);
        }
    }

    public ArrayList<Item> getItems() { return items; }

    public int getItemsCount() {
        return items.size();
    }

    public int getKeys() { return keys.size(); }

    public void removeOneKey() { if(keys.size() > 0) { keys.remove(keys.get(0)); } }
}