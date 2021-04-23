package com.codecool.dungeoncrawl.logic.actors.opponents;

import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.logic.actors.*;

import java.util.ArrayList;
import java.util.Random;

public class Opponents {
    private static ArrayList<Actor> diedOpponents = new ArrayList<>();
    private static ArrayList actualOpponents;


    public Opponents(ArrayList arrayList){
        actualOpponents = arrayList;
    }


    public static void opponentsMove(){
        for (Object actor: actualOpponents) {
            if(actor instanceof Warrior){
                warriorMove((Warrior) actor);
            }else if(actor instanceof Phantom){
                phantomMove((Phantom) actor);
            }
        }
    }

    public static void warriorMove(Warrior warrior){
        if(warrior.getHealth()>0){
            int dx = warrior.getDirection();
            if (warrior.getCell().getNeighbor(dx,0).getType().equals(CellType.WALL)){ warrior.changeDirection(); }
            dx = warrior.getDirection();
            warrior.move(dx, 0);
        }
    }

    public static void phantomMove(Phantom phantom){
        Random random = new Random();
        int chance = random.nextInt(4);

        if(chance == 1) {
            int dx = -2 + random.nextInt(5);
            int dy = -2 + random.nextInt(5);
            int startX = phantom.getStartX();
            int startY = phantom.getStartY();
            if(phantom.getHealth()>0) {
                phantom.setPosition(startX, startY);
                phantom.move(dx, dy);
            }
        }
    }

    public static void fightWithOpponent(Cell opponnent, Cell player){
        Player playerActor = player.getGameMap().getPlayer();
        Actor opponentActor = opponnent.getActor();

        opponentActor.updateHealth(-playerActor.getStrength());

        if (opponentActor.getHealth() > 0) {
            if (playerActor.getHealth() > 1) {
                if (playerActor.getArmor() > 0) {
                    playerActor.updateArmor(-opponentActor.getStrength());
                } else {
                    playerActor.updateHealth(-opponentActor.getStrength());
                }
            }
        }

        if (playerActor.getArmor() < 0) {
            playerActor.updateArmor(-playerActor.getArmor());
            playerActor.updateHealth(playerActor.getArmor());
        }

        if (opponentActor.getHealth() <= 0) {
            opponnent.setActor(null);
            playerActor.updateScore(opponentActor.getPoints());
            diedOpponents.add(opponentActor);
        }
    }

    public static ArrayList<Actor> getDiedOpponents() { return diedOpponents; }

    public static ArrayList getActualOpponents() { return actualOpponents; }

    public void printDiedOpp(){
        int num= 0;
        for (Actor died: diedOpponents) {
            System.out.println(died.getTileName());
            num++;
        }
        System.out.println("Dieds");
        System.out.println(num);
    }

    public static void setDiedOpponents(ArrayList<Actor> diedOpponents) { Opponents.diedOpponents = diedOpponents; }
}
