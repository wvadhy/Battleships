package org.example.controller;

import org.example.util.Coords;
import org.example.model.Model;
import org.example.model.Player;

import java.util.Observable;
import java.util.Observer;

public class CLIController implements Observer {

    /*
    Validates requests to and from the CLI and Model, it will then update
    the view accordingly.
    */

    private final Model model;

    public CLIController(Model model){
        this.model = model;
        model.addObserver(this);
        model.changeTurn();
    }

    public void attack(Coords coords){ model.attack(coords);}
    public Player getAttacker(){return model.getAttacker();}
    public Player getDefender() {return model.getDefender();}

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String){
            System.out.println(arg);
        } else {
            String[] winner = (String[]) arg;
            System.out.println(winner[0] + winner[1]);
        }
    }
}
