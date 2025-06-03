package org.example.view;

import org.example.model.Model;
import org.example.model.Player;
import org.example.util.CJButton;
import org.example.util.Coords;

import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

public abstract class View extends JFrame implements Observer {

    /*
    Main view where primary gameplay takes place.
    */

    private final JLabel player = new JLabel();
    private final JLabel error = new JLabel();
    private final JButton submit = new JButton("Attack!");
    private final JPanel battle = new JPanel();
    private final JPanel hit = new JPanel();
    private final JLabel round = new JLabel("1");
    private CJButton selected = new CJButton();
    private List<JButton> hits = new ArrayList<>();
    protected final Model model;

    public View(Model model){

        this.model = model;

        error.setForeground(new Color(238, 238, 238));
        error.setFont(new Font("MV Boli", Font.PLAIN, 13));
        error.setHorizontalTextPosition(JLabel.CENTER);
        error.setBounds(135, 375, 300, 50);

        player.setForeground(Color.WHITE);
        player.setFont(new Font("MV Boli", Font.BOLD, 14));
        player.setBounds(110, 290, 300, 50);

        round.setForeground(Color.WHITE);
        round.setFont(new Font("Arial", Font.BOLD, 35));
        round.setBounds(20, 118, 50, 100);

        submit.setBounds(112, 340, 195, 50);
        submit.setContentAreaFilled(false);
        submit.setBackground(new Color(0, 173, 181));
        submit.setFont(new java.awt.Font("Futura", 1, 15));
        submit.setForeground(new Color(238, 238, 238));
        submit.setOpaque(true);
        submit.setBorder(new LineBorder(Color.black, 1, true));

        hit.setBounds(350, 40, 60, 240);
        hit.setLayout(new GridLayout(5, 1, 0, 0));


        for (int i = 0; i < 5; i++) {
            JButton btn = new JButton((i == 0) ? String.valueOf(2) : String.valueOf(i+1));
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            btn.setOpaque(true);
            btn.setBackground(Color.DARK_GRAY);
            hit.add(btn);
            hits.add(btn);
        }

        this.setLayout(null);
        this.setTitle("Battleships");
        this.setIconImage(new ImageIcon("logo.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(420, 460);
        this.getContentPane().setBackground(new Color(57, 62, 70));

        initialBoard();

        this.add(player);
        this.add(error);
        this.add(battle);
        this.add(submit);
        this.add(round);
        this.add(hit);

        for (int i = 1; i < 10+1; i++) {
            JLabel y = new JLabel(Integer.toString(i));
            y.setForeground(new Color(0, 173, 181));
            y.setFont(new Font("MV Boli", Font.BOLD, 10));
            y.setBounds(70, 27 + ((i-1) * 25), 300, 50);

            JLabel x = new JLabel(Character.toString((char) ((i + 97)-1)).toUpperCase());
            x.setForeground(new Color(0, 173, 181));
            x.setFont(new Font("MV Boli", Font.BOLD, 10));
            x.setBounds(95 + ((i-1) * 25), 5, 300, 50);

            this.add(x);
            this.add(y);
        }

        model.addObserver(this);
        model.changeTurn();

        this.setVisible(true);
    }

    public void addSubmitListener(ActionListener a){ submit.addActionListener(a);}
    public void setLabel(String text) { player.setText(text + " please select a square"); }
    public void setRound(int turn){ round.setText(String.valueOf(turn));}
    public void setError(String text){ error.setText(text);}
    public void goToEnd(String[] winner) {
        this.dispose();
        new EndView(winner);
    }
    public Coords getTarget(){
        assert selected.getCoords().getX() > 0;
        return selected.getCoords();
    }

    public void actionPerformed(ActionEvent e) {
        selected = (CJButton) e.getSource();
        submit.setText("Attack " + selected.getCoords().toString() + "!");
    }
    public void setHit(List<Integer> ships){
        System.out.println("Settings hits for: " + ships);
        for (JButton j: hits) {
            j.setBackground(Color.gray);
            for (int i: ships)  {
                if (Objects.equals(j.getText(), String.valueOf(i))){
                    j.setBackground(Color.green);
                }
            }
        }
    }

    private void initialBoard(){

        battle.setBounds(85, 40, 250, 250);
        battle.setLayout(new GridLayout(10, 10, 0, 0));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton btn = new JButton("");
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                battle.add(btn);
            }
        }
    }
    public void setupBoard(Player defender){

        char[][] board = defender.getBoard();

        battle.removeAll();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                CJButton btn = new CJButton();
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                btn.setCoords(new Coords(i+1, (char) ((j + 97))));
                btn.addActionListener(this::actionPerformed);

                if (board[i][j] == 'x'){
                    btn.setOpaque(true);
                    btn.setBackground(Color.RED);
                } else if (board[i][j] == '+'){
                    btn.setOpaque(true);
                    btn.setBackground(Color.YELLOW);
                }

                battle.add(btn);
                battle.revalidate();
            }
        }
    }
}