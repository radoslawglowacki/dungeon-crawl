package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;

import java.util.List;

public interface GameStateDao {
    void add(GameStateModel player);
    void update(GameStateModel player, int id);
    GameStateModel get(int id);
    List<GameStateModel> getAll();
}
