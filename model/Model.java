package org.example.model;

import org.example.util.Coords;

import java.util.Observable;

/**
 * @file Model.java
 * @author William Viet Anh Douglas Halliday
 * @date 2025
 */
public class Model extends Observable {

    /*
    Main brain of the Battleships.
     */

    private final Player one;
    private final Player two;
    private Player attacker;
    private Player defender;

    private int turn = 0;

    public Model(Player one, Player two){

        /*
        args: Player one, Player two -> The two Player sub-models which will be updated accordingly by this Model.
        rtype: Model -> An instance of Model.
        precon: None

        Instantiates a new Model which will act as the core system keeping track of attacks turns and overall
        game state.
        */

        this.one = one;
        this.two = two;
    }

    public void attack(Coords toParse){

        /*
        args: String[] toParse -> An array of two strings used to instantiate Coords provided they are valid.
        rtype: void
        precon: toParse.length > 2 -> Array of strings must equal 2, the number of arguments required for Coords.

        Parses the String array and instantiates a new Coords object, this will then be passed to the Player
        method .attackShip(...) -> int, the controller will then be notified whether the Coords produced were
        erroneous, if the target had already been attacked or if the current attacker had sunk all of their
        opponents ships as a result of the integer returned by the aforementioned function, if none of these
        hold true changeTurn() -> void will be called and play will continue.
         */

        assert (toParse.getX() <= 10 && toParse.getX() >= 1) && (toParse.getY() <= 106 && toParse.getY() >= 97);

        switch (attacker.attackShip(toParse, defender.getBoard(), defender.getShips())) {
            case 0 -> {
                if (attacker.getShipsHit() == 16){
                    setChanged();
                    notifyObservers(new String[] {attacker.getName(), String.valueOf(attacker.getShipsAttacked())});
                } else
                    changeTurn();
            }
            case 1 -> {
                setChanged();
                notifyObservers("Target already attacked!");
            }
            case 2 -> {
                setChanged();
                notifyObservers("Alphabet not supported.");
            }
        }
    }

    public void changeTurn(){

        /*
        args: None
        rtype: void
        precon: None

        Changes the current attacker and defender based upon the current turn,
        these changes are then passed to the Controller which then validates
        the Player array and appropriately modifies the view.
         */

        attacker = turn % 2 == 0 ? two: one;
        defender = turn % 2 == 0 ? one: two;

        setChanged();
        notifyObservers("");
        this.turn++;
    }

    public int getTurn() {return turn;}
    public Player getAttacker() {return attacker;}
    public Player getDefender() {return defender;}
}
