package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.OpponentsModel;

public interface OpponentsDao {
    void add(OpponentsModel opponents);
    void delete(int id);
    void update(OpponentsModel opponentsModel);
    OpponentsModel getAllDiedOpponents(int id, Player player);
}
