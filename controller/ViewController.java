package org.example.controller;

import org.example.model.Model;
import org.example.view.View;

import java.util.Observable;

/**
 * @file Controller.java
 * @author William Viet Anh Douglas Halliday
 * @date 2025
 */
public class ViewController extends View {

    /*
    Validates requests to and from the View and Model, it will then update
    the view accordingly.
    */

    public ViewController(Model model){
        super(model);
        addSubmitListener( e -> model.attack(getTarget()));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String){
            setupBoard(model.getDefender());
            setLabel(model.getAttacker().getName());
            setHit(model.getAttacker().getShipsSunk());
            setRound(model.getTurn());
            setError((String) arg);
        } else {
            String[] winner = (String[]) arg;
            goToEnd(winner);
            System.out.println(winner[0] + winner[1]);
        }
    }
}
