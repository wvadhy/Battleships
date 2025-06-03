package org.example.view;

import org.example.util.Coords;
import org.example.controller.CLIController;
import org.example.model.Model;
import org.example.model.Player;
import org.example.util.JSONHandler;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Scanner;

public class CLI {

    /*
    CLI loop for basic interactions to Controller
    and input of player details.
     */

    public CLI(){
        Scanner reader = new Scanner(System.in);
        System.out.print("Would you like to swap to GUI? (y/n): ");
        if (reader.next().equals("y")) { new EntryView(); return; }
        System.out.print("Please enter player one's name: ");
        Player one = new Player(reader.next());
        System.out.print("Please enter player two's name: ");
        Player two = new Player(reader.next());
        System.out.print("Would you like to use a custom board? (y/n): ");
        if (reader.next().equals("y")) {
            JSONHandler jsonHandler = new JSONHandler();
            JSONObject jsonObject = jsonHandler.load();
            if (!jsonObject.isEmpty()){
                try {
                    System.out.println("Attempting to load board...");
                    one.createJsonBoard(jsonObject);
                } catch (Exception ex) {
                    System.out.println("Error loading file");
                    throw new RuntimeException(ex);
                }
            } else
                one.createBoard();
        }
        loop(new CLIController(new Model(one, two)));
    }

    public void loop(CLIController client) {
        Scanner reader = new Scanner(System.in);
        client.getDefender().displayHit();
        System.out.print(client.getAttacker().getName() + " please enter a co-ordinate: ");
        String[] toCoords = reader.next().split("");
        client.attack(new Coords(Integer.parseInt(toCoords[1]), Character.toLowerCase(toCoords[0].charAt(0))));
        loop(client);
    }

}