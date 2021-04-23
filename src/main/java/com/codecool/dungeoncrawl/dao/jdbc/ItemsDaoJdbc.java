package com.codecool.dungeoncrawl.dao.jdbc;

import com.codecool.dungeoncrawl.dao.ItemsDao;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.model.ItemsModel;
import com.codecool.dungeoncrawl.model.OpponentsModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsDaoJdbc implements ItemsDao {
    private DataSource dataSource;

    public ItemsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemsModel itemsModel) {
        ArrayList<Item> items = itemsModel.getItems();
        int playerId = itemsModel.getPlayerId();

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO items (player_id, map_name, item_name, x, y) VALUES (?, ?, ?, ?, ?)";
            for (Item item : items) {
                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setInt(1, playerId);
                statement.setString(2, item.getMapName());
                statement.setString(3, item.getTileName());
                statement.setInt(4, item.getX());
                statement.setInt(5, item.getY());

                statement.executeUpdate();

                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "DELETE FROM items WHERE player_id=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ItemsModel itemsModel) {
        delete(itemsModel.getPlayerId());
        add(itemsModel);
    }

    @Override
    public ItemsModel getAllItems(int id, Player player) {
        ArrayList<Item> allItems = new ArrayList<>();
        String sql = "SELECT map_name, item_name, x, y FROM items WHERE player_id = ?";
        GameMap gameMap = player.getCell().getGameMap();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String mapName = resultSet.getString(1);
                String itemName = resultSet.getString(2);
                int x = resultSet.getInt(3);
                int y = resultSet.getInt(4);

                switch (itemName) {
                    case "armor":
                        allItems.add(new Armor(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                        break;
                    case "blade":
                        allItems.add(new Blade(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                        break;
                    case "diamond":
                        allItems.add(new Diamond(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                        break;
                    case "heart":
                        allItems.add(new Heart(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                        break;
                    case "key":
                        allItems.add(new Key(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                        break;
                    case "openDoors":
                        allItems.add(new OpenedDoors(new Cell(gameMap, x, y, CellType.FLOOR), mapName));
                        break;
                }
            }
            return new ItemsModel(allItems, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
