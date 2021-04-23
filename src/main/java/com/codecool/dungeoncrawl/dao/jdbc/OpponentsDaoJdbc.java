package com.codecool.dungeoncrawl.dao.jdbc;

import com.codecool.dungeoncrawl.dao.OpponentsDao;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.actors.opponents.Phantom;
import com.codecool.dungeoncrawl.logic.actors.opponents.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.opponents.Warrior;
import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.model.OpponentsModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class OpponentsDaoJdbc implements OpponentsDao {
    private DataSource dataSource;

    public OpponentsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(OpponentsModel opponents) {
        ArrayList<Actor> diedOpponents = opponents.getDiedOpponents();
        int playerId = opponents.getPlayerId();

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO opponents (player_id, start_x, start_y, map_name, actor_name, x, y) VALUES (?, ?, ?, ?, ?, ?, ?)";
            for (Actor died : diedOpponents) {
                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setInt(1, playerId);
                statement.setInt(2, died.getStartX());
                statement.setInt(3, died.getStartY());
                statement.setString(4, died.getMapNumber());
                statement.setString(5, died.getTileName());
                statement.setInt(6, died.getX());
                statement.setInt(7, died.getY());

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

            String sql = "DELETE FROM opponents WHERE player_id=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(OpponentsModel opponentsModel) {
        delete(opponentsModel.getPlayerId());
        add(opponentsModel);
    }

    @Override
    public OpponentsModel getAllDiedOpponents(int id, Player player) {
        ArrayList<Actor> allDiedActors = new ArrayList<>();
        String sql = "SELECT start_x, start_y, map_name, actor_name, x, y FROM opponents WHERE player_id = ?";
        GameMap gameMap = player.getCell().getGameMap();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int startX = resultSet.getInt(1);
                int startY = resultSet.getInt(2);
                String mapName = resultSet.getString(3);
                String actorName = resultSet.getString(4);
                int x = resultSet.getInt(5);
                int y = resultSet.getInt(6);

                switch (actorName) {
                    case "skeleton":
                        allDiedActors.add(new Skeleton(new Cell(gameMap, x, y, CellType.FLOOR), startX, startY, mapName));
                        break;
                    case "warrior":
                        allDiedActors.add(new Warrior(new Cell(gameMap, x, y, CellType.FLOOR), startX, startY, mapName));
                        break;
                    case "phantom":
                        allDiedActors.add(new Phantom(new Cell(gameMap, x, y, CellType.FLOOR), startX, startY, mapName));
                        break;
                }
            }
            return new OpponentsModel(allDiedActors, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
