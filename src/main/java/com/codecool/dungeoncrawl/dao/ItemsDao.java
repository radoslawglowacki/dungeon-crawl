package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.ItemsModel;

public interface ItemsDao {
    void add(ItemsModel itemsModel);
    void delete(int id);
    void update(ItemsModel itemsModel);
    ItemsModel getAllItems(int id, Player player);
}
