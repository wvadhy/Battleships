package org.example.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.Color;
import java.io.*;
import java.util.Objects;

import org.example.controller.ViewController;
import org.example.model.Model;
import org.example.model.Player;
import org.example.util.JSONHandler;
import org.example.util.RoundJTextField;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

public class EntryView extends JFrame {

    /*
    Initial view where players names are entered,
    board setup files can be uploaded and gameplay swapped
    to CLI version.
    */

    protected final JLabel logo = new JLabel("BATTLESHIPS");
    protected final JLabel extension = new JLabel(".gui");
    protected final JTextField nameOne = new RoundJTextField(15);
    protected final JTextField nameTwo = new RoundJTextField(15);
    protected final JButton enter = new JButton("Start game");
    protected final JButton board = new JButton("Upload Board");
    protected final JButton swap = new JButton("Swap to CLI");
    protected JSONObject jsonObject = new JSONObject();
    private final JSONHandler jsonHandler = new JSONHandler();

    public EntryView(){

        logo.setBounds(55, 60, 300, 50);
        extension.setBounds(196, 61, 100, 50);
        nameOne.setBounds(75, 120, 150, 20);
        nameTwo.setBounds(75, 150, 150, 20);
        enter.setBounds(75, 180, 150, 30);
        board.setBounds(75, 205, 150, 30);
        swap.setBounds(75, 230, 150, 30);

        enter.addActionListener(e -> {
            if (!Objects.equals(nameOne.getText(), "") && !Objects.equals(nameTwo.getText(), "")){
                this.dispose();

                Player one = new Player(nameOne.getText());
                Player two = new Player(nameTwo.getText());

                // Checks whether a JSON file was loaded, if so load custom board otherwise load RNG board.
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

                two.createBoard();
                one.displayBoard();
                new ViewController(new Model(one, two));
            }});

        board.addActionListener(e -> { jsonObject = jsonHandler.load(); });

        swap.addActionListener(e -> {
            this.dispose();
            new CLI();
        });

        nameOne.setHorizontalAlignment(SwingConstants.CENTER);
        nameTwo.setHorizontalAlignment(SwingConstants.CENTER);

        logo.setFont(new Font("Monospaced", Font.BOLD, 22));
        logo.setForeground(new Color(34, 40, 49));

        extension.setFont(new Font("Monospaced", Font.BOLD, 18));
        extension.setForeground(Color.white);

        setupBtn(enter);
        setupBtn(board);
        setupBtn(swap);

        this.setLayout(null);
        this.setTitle("Welcome");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(300, 300);
        this.getContentPane().setBackground(new Color(57, 62, 70));

        this.add(logo);
        this.add(nameOne);
        this.add(nameTwo);
        this.add(enter);
        this.add(board);
        this.add(swap);
        this.add(extension);

        this.setVisible(true);
    }

    private void setupBtn(JButton btn) {
        btn.setContentAreaFilled(false);
        btn.setBackground(new Color(0, 173, 181));
        btn.setFont(new java.awt.Font("Futura", 1, 12));
        btn.setForeground(new Color(238, 238, 238));
        btn.setOpaque(true);
        btn.setBorder(new LineBorder(Color.black, 1, true));
    }

    private ImageIcon createImage(String image) {
        try {
            Image img = ImageIO.read(Objects.requireNonNull(getClass().getResource(image)));
            return new ImageIcon(img);
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
}