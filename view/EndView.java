package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class EndView extends JFrame {

    /*
    End view showing the winning player and the amount of
    attacks taken to win.
    */

    private final JLabel name = new JLabel();

    private final JLabel subHeading = new JLabel();

    public EndView(String[] winner){

        name.setText(winner[0]);
        name.setBounds(105, 60, 400, 50);
        name.setFont(new Font("Monospaced", Font.BOLD, 22));
        name.setForeground(Color.white);
        name.setAlignmentX(CENTER_ALIGNMENT);

        subHeading.setText("has won in " + winner[1] + " attacks!");
        subHeading.setBounds(50, 90, 300, 50);
        subHeading.setFont(new Font("Monospaced", Font.BOLD, 16));
        subHeading.setForeground(Color.lightGray);

        this.add(name);
        this.add(subHeading);

        this.setLayout(null);
        this.setTitle("Finished");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(300, 300);
        this.getContentPane().setBackground(new Color(57, 62, 70));
        this.setVisible(true);
    }

}
