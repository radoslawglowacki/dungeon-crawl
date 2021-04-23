package com.codecool.dungeoncrawl.dao.jdbc;

import com.codecool.dungeoncrawl.dao.*;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.ItemsModel;
import com.codecool.dungeoncrawl.model.OpponentsModel;
import com.codecool.dungeoncrawl.model.GameStateModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class GameDatabaseManager {
    private GameStateDao gameStateDao;
    private OpponentsDao opponentsDao;
    private ItemsDao itemsDao;
    private com.codecool.dungeoncrawl.model.GameStateModel gameStateModel;
    private OpponentsModel opponentsModel;
    private ItemsModel itemsModel;
    private GameMap maptoLoad;
    private Player playerToLoad;
    private int playerX;
    private int playerY;


    public void setup() throws SQLException {
        DataSource dataSource = connect();
        gameStateDao = new GameStateDaoJdbc(dataSource);
        opponentsDao = new OpponentsDaoJdbc(dataSource);
        itemsDao = new ItemsDaoJdbc(dataSource);
    }

    public List<com.codecool.dungeoncrawl.model.GameStateModel> getAllGameStates(){
        return gameStateDao.getAll();
    }

    public void saveState(Player player, String saveName) {
        this.gameStateModel = new GameStateModel(player, saveName);
        gameStateDao.add(gameStateModel);
        this.opponentsModel = new OpponentsModel(player.getDiedOpponents(), gameStateModel.getId());
        opponentsDao.add(opponentsModel);
        this.itemsModel = new ItemsModel(player.getInventory().getItems(), gameStateModel.getId());
        itemsDao.add(itemsModel);
    }


    public void updateState(Player player, String saveName, int id){
        this.gameStateModel = new GameStateModel(player, saveName);
        gameStateDao.update(gameStateModel,id);
        this.opponentsModel = new OpponentsModel(player.getDiedOpponents(),id);
        opponentsDao.update(opponentsModel);
        this.itemsModel = new ItemsModel(player.getInventory().getItems(), id);
        itemsDao.update(itemsModel);
    }


    public void loadState(Player player, int id) {
        this.gameStateModel = gameStateDao.get(id);
        this.opponentsModel = opponentsDao.getAllDiedOpponents(id,player);
        this.itemsModel = itemsDao.getAllItems(id,player);

    }


    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("dbName");
        String user = System.getenv("user");
        String password = System.getenv("password");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public com.codecool.dungeoncrawl.model.GameStateModel getPlayerModel() { return gameStateModel; }

    public OpponentsModel getOpponentsModel() { return opponentsModel; }

    public ItemsModel getItemsModel() { return itemsModel; }

}
