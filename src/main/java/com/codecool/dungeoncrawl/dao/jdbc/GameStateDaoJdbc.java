package com.codecool.dungeoncrawl.dao.jdbc;

import com.codecool.dungeoncrawl.dao.GameStateDao;
import com.codecool.dungeoncrawl.model.GameStateModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameStateModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (player_name, hp, armor, strength, score, map_name, x, y, save_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getArmor());
            statement.setInt(4, player.getStrength());
            statement.setInt(5, player.getScore());
            statement.setString(6, player.getMapName());
            statement.setInt(7, player.getX());
            statement.setInt(8, player.getY());
            statement.setString(9, player.getSaveName());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameStateModel player, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_state SET player_name = ?, hp = ?, armor = ?, strength = ?, score = ?, map_name = ?, x = ?, y = ?, save_name = ? WHERE id = "+id;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, player.getPlayerName());
            preparedStatement.setInt(2, player.getHp());
            preparedStatement.setInt(3, player.getArmor());
            preparedStatement.setInt(4, player.getStrength());
            preparedStatement.setInt(5, player.getScore());
            preparedStatement.setString(6, player.getMapName());
            preparedStatement.setInt(7, player.getX());
            preparedStatement.setInt(8, player.getY());
            preparedStatement.setString(9, player.getSaveName());
//            preparedStatement.setInt(10, id);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameStateModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "SELECT player_name, hp, armor, strength, score, map_name, x, y, save_name FROM game_state WHERE id= ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                GameStateModel player = new GameStateModel(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8),
                        resultSet.getString(9));
                player.setId(id);
                return player;
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameStateModel> getAll() {

        try (Connection conn = dataSource.getConnection()) {
            List<GameStateModel> listOfAllPlayers = new ArrayList<>();

            String sql = "SELECT id, player_name, hp, armor, strength, score, map_name, x, y, save_name FROM game_state";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                GameStateModel player = new GameStateModel(
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getString(10));
                player.setId(resultSet.getInt(1));
                listOfAllPlayers.add(player);
            }
            return listOfAllPlayers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
