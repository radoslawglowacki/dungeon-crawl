package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.cells.Cell;
import com.codecool.dungeoncrawl.logic.cells.CellType;
import com.codecool.dungeoncrawl.logic.actors.*;

import java.util.ArrayList;
import java.util.Random;

public class Opponents {

    private static ArrayList<Actor> diedOpponents;
    private ArrayList<Skeleton> skeletons;
    private static ArrayList<Warrior> warriors;
    private static ArrayList<Phantom> phantoms;



    public Opponents(ArrayList arrayList){
        this.skeletons = (ArrayList<Skeleton>) arrayList.get(0);
        this.warriors = (ArrayList<Warrior>) arrayList.get(1);
        this.phantoms = (ArrayList<Phantom>) arrayList.get(2);
        this.diedOpponents = new ArrayList<>();
    }



    public static void warriorMove(){
        for (Warrior warrior: warriors) {
            if(warrior.getHealth()>0){
                int dx = warrior.getDx();
                if (warrior.getCell().getNeighbor(dx,0).getType().equals(CellType.WALL)){ warrior.setDx(1); }
                dx = warrior.getDx();
                warrior.move(dx, 0);
            }
        }
    }

    public static void phantomMove(){

        Random random = new Random();
        for (Phantom phantom: phantoms) {
            int chance = random.nextInt(4); //random chance to move
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

    }

//jak sie zdecyduje zeby potwory same tez atakowaly to trzeba przerobic
    public static void fightWithOpponent(Cell opponnent, Cell player){
        Actor opponentActor = opponnent.getActor();
        Player playerActor = player.getGameMap().getPlayer();
        int opponentHealth = opponentActor.getHealth();
        int playerHealth = playerActor.getHealth();
        int opponentStrength = opponentActor.getStrength();
        int playerStrength = playerActor.getStrength();
        int pointsForWin = opponentActor.getPoints();
        int playerArmor = player.getGameMap().getPlayer().getArmor();
        Inventory inventory = player.getGameMap().getPlayer().getInventory();


        if (inventory.getBlades() != 0) {
            playerStrength += 5 * inventory.getBlades();
        }

        opponentActor.updateHealth(-playerStrength);
        opponentHealth = opponentActor.getHealth();

        if (opponentHealth > 0) {
            if (playerHealth > 1) {
                if (playerArmor > 0) {
                    playerActor.updateArmor(-opponentStrength);
                } else {
                    playerActor.updateHealth(-opponentStrength);
                }
            }
        }

        playerArmor = playerActor.getArmor();

        if (playerArmor < 0) {
            playerActor.updateArmor(-playerArmor);
            playerActor.updateHealth(playerArmor);
        }

        if (opponentHealth <= 0) {
            opponnent.setActor(null);
            playerActor.updateScore(pointsForWin);
            diedOpponents.add(opponentActor);
        }
    }


//    to tylko do testów

    public void printWarrior(){
        int num= 0;
        for (Warrior warrior:warriors) {
            num++;
        }
        System.out.println("warrior");
        System.out.println(num);
    }

    public void printSkeletons(){
        int num= 0;
        for (Skeleton skeleton: skeletons) {
            num++;
        }
        System.out.println("skeleton");
        System.out.println(num);
    }

    public void printPhantoms(){
        int num= 0;
        for (Phantom phantom: phantoms) {
            num++;
        }
        System.out.println("phantom");
        System.out.println(num);
    }

    public void printDiedOpp(){
        int num= 0;
        for (Actor died: diedOpponents) {
            System.out.println(died.getTileName());
            num++;
        }
        System.out.println("Dieds");
        System.out.println(num);
    }


}
