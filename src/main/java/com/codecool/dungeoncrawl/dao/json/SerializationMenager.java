package com.codecool.dungeoncrawl.dao.json;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.ItemsModel;
import com.codecool.dungeoncrawl.model.OpponentsModel;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;

public class SerializationMenager {
    private Player player;
    private JFileChooser fileChooser;
    private File file;
    private GameStateModel gameStateModel;
    private OpponentsModel opponentsModel;
    private ItemsModel itemsModel;


    public SerializationMenager(Player player) {
        this.player = player;
        this.fileChooser = new JFileChooser();
        fileFilter();
    }


    private void fileFilter(){
        this.fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game state file", "json");
        this.fileChooser.addChoosableFileFilter(filter);
    }

    public void exportGameState() {
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.file = fileChooser.getSelectedFile();
            if(!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")){
                this.file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".json");
            }

            this.gameStateModel = new GameStateModel(player, "1");
            this.opponentsModel = new OpponentsModel(player.getDiedOpponents(), 1);
            this.itemsModel = new ItemsModel(player.getInventory().getItems(), 1);

            GameJSON gameJSON = new GameJSON(gameStateModel, itemsModel, opponentsModel);
            Gson gson = new Gson();

            try (PrintWriter out = new PrintWriter(file.getAbsolutePath())) {
                out.println(gson.toJson(gameJSON));
            } catch (Exception e) { }
        }
    }


    public void importGameState(Main main) {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.file = fileChooser.getSelectedFile();
            Gson gson = new Gson();
            try (Reader reader = new FileReader(file.getAbsolutePath())) {
                GameJSON gameJSON = gson.fromJson(reader, GameJSON.class);
                GameStateModel gameStateModel = gameJSON.getGameStateModel();
                ArrayList<Actor> playerOpponents = gameJSON.getdiedOpponents(player);
                ArrayList<Item> playerItems = gameJSON.getPlayerItems(player);

                main.loadSavedGame(playerItems,playerOpponents,gameStateModel);

            } catch (Exception e) {

            }
        }
    }

}
