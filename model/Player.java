package org.example.model;

import org.example.util.Color;
import org.example.util.Coords;
import org.example.util.Ship;
import org.example.util.WeightedRandom;
import org.json.JSONObject;

import java.util.*;


/**
 * @file Player.java
 * @author William Viet Anh Douglas Halliday
 * @date 2025
 */
public class Player {

    /*
    Sub-brain of Battleships, is modular and primarily functions as an additive to Model.
     */

    private char[][] board = new char[10][10];
    private String name;
    private List<Ship> ships = new ArrayList<>(Arrays.asList(new Ship(0), new Ship(5), new Ship(4),
            new Ship(3), new Ship(2), new Ship(2)));
    private final Map<Character, Integer> charToCoord = Map.of('a', 1, 'b', 2,
            'c', 3, 'd', 4,
            'e', 5, 'f', 6,
            'g', 7, 'h', 8,
            'i', 9,'j', 10);
    private int shipsHit = 0;
    private int shipsMissed = 0;
    private List<Integer> shipsSunk = new ArrayList<>();
    public Player(String name){

        /*
        args: String name -> The name of the Player.
        rtype: Player -> An instance of Player.
        precon: None

        Instantiates a new Player object which will store persistent individual data local to the Player,
        this includes their current board, how many of their ships have sunk, how many ships they've hit / missed,
        etc.
        */

        this.name = name;
    }

    public void createBoard(){

        /*
        args: None
        rtype: void
        precon: None

        Creates a board proprietary to this object and assigns it to this.board, this board consists of
        pseudo-randomly placed ships located on both horizontal and vertical planes whose locations
        are determined by a weighted index picker which can be denoted as -> w = (x + y)**2.
         */

        int[] weights = new int[] {100, 10, 10, 10, 10, 10};
        List<Integer> indexes = new ArrayList<>();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                WeightedRandom weightedRandom = new WeightedRandom(weights);
                Ship selectedShip = ships.get(weightedRandom.nextIndex());
                if (selectedShip.getLength() > 1 && !indexes.contains(weightedRandom.currentIndex())) {
                    if (9 - y > selectedShip.getLength()) {
                        if (board[x][y+selectedShip.getLength()] != 'O' && board[x][y] != 'O'){
                            if (checkSurroundings(x, y)) {
                                for (int i = y; i < selectedShip.getLength() + y; i++) {
                                    board[x][i] = 'O';
                                    selectedShip.addPosition(x+1, (char) (97 + i));
                                }
                                indexes.add(weightedRandom.currentIndex());
                            }
                        }
                    } else if (9 - x > selectedShip.getLength()) {
                        if (board[x+selectedShip.getLength()][y] != 'O' && board[x][y] != 'O') {
                            if (checkSurroundings(x, y)) {
                                for (int i = x; i < selectedShip.getLength() + x; i++) {
                                    board[i][y] = 'O';
                                    selectedShip.addPosition(i+1, (char) (97 + y));
                                }
                                indexes.add(weightedRandom.currentIndex());
                            }
                        }
                    }
                } if (board[x][y] != 'O'){
                    board[x][y] = '-';
                }
            }
        }
        System.out.println("Loaded board successfully!");
    }

    public void createJsonBoard(JSONObject obj) throws Exception {

        /*
        args: JSOBObject obj -> JSON structured data which can be queried.
        rtype: void
        precon: obj::isValid() -> JSON object must adhere to structural
                                  and functional requirements of the specification.

        Creates a custom board proprietary to this object and assigns it to this.board, this board is created
        from attributes presented in a JSON, these attributes are deserialized and validates before
        creation of the board.
         */

        Map<String, Integer> namesToSize = Map.of("fiveShip", 5, "fourShip", 4, "threeShip", 3,
                "twoShip", 2, "twoShipTwo", 2);
        String[] names = namesToSize.keySet().toArray(new String[5]);

        for (String value : names) {
            JSONObject temp = obj.getJSONObject(value);
            int originX = temp.getJSONObject("origin").getInt("x");
            int destX = temp.getJSONObject("destination").getInt("x");
            int originY = charToCoord.get(temp.getJSONObject("origin").getString("y").charAt(0));
            int destY = charToCoord.get(temp.getJSONObject("destination").getString("y").charAt(0));

            if (originX != destX) {
                assert (destX - originX) == namesToSize.get(value) - 1;
                assert (originY == destY);
            } else if (originY != destY) {
                assert (destY - originY) == namesToSize.get(value) - 1;
            } else {
                throw new Exception("Origin and destination are equal");
            }

            for (Ship s : ships) {
                if (s.getLength() == namesToSize.get(value)) {
                    if (originX == destX) {
                        for (int j = originY - 1; j < originY + (destY - originY); j++) {

                            if (board[originX][j] == 'O')
                                throw new Exception("Ships are intersected.");

                            s.addPosition(originX, (char) (j + 97));
                            board[originX][j] = 'O';
                        }
                    } else {
                        for (int j = originX - 1; j < originX + (destX - originX); j++) {

                            if (board[j][originY - 1] == 'O')
                                throw new Exception("Ships are intersected.");

                            s.addPosition(j + 1, (char) (originY + 97 - 1));
                            board[j][originY - 1] = 'O';
                        }
                    }
                    break;
                }
            }
        }

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                if (board[x][y] != 'O') {
                    board[x][y] = '-';
                }
            }
        }
        System.out.println("Loaded custom board successfully!");
    }

    private boolean checkSurroundings(int x, int y) {

        /*
        args: int x, int y -> Used to check adjacent co-ordinates.
        rtype: boolean -> Whether all flags were passed.
        precon: None

        Check co-ordinates directly adjacent to provided labels in order to prevent
        intersections of ships as outline in FR5. A boolean is returned whose value is
        based upon the state of flagOne and flagTwo.
         */

        boolean flagOne = false;
        boolean flagTwo = false;
        if (x == 0){
            flagOne = true;
        } else if (board[x-1][y] != '0'){
            flagOne = true;
        }
        if (y == 0) {
            flagTwo = true;
        } else if (board[x][y-1] != '0'){
            flagTwo = true;
        }
        return flagOne && flagTwo;
    }

    public String getName() { return name; }
    public int getShipsHit() { return shipsHit; }
    public int getShipsAttacked() { return shipsHit + shipsMissed; }
    public char[][] getBoard() { return board; }
    public List<Integer> getShipsSunk() { return shipsSunk; }
    public List<Ship> getShips() { return ships; }

    public int attackShip(Coords ship, char[][] board, List<Ship> vessels){

        /*
        args: Coords ship, char[][] board, List<Ship> vessels -> Used to attack defending player.
        rtype: int -> Return code for attack success or if failure, what failure.
        precon: ship.getX() must be a number 1-10, ship.getY() must be a letter within a-j.

        Attacks the defending players board, upon successful attack mark one of their ships as damaged,
        if fully damaged the ship will be marked as sunk.
         */

        if (!charToCoord.containsKey(ship.getY()))
            return 2;

        char target = board[ship.getX()-1][charToCoord.get(ship.getY())-1];

        if (target != 'x' && target != '+') {
            if (target == 'O') {
                System.out.println("Hit a ship!");
                checkShipHit(new Coords(ship.getX(), ship.getY()), vessels);
                board[ship.getX()-1][charToCoord.get(ship.getY())-1] = 'x';
                shipsHit++;
            }
            else {
                System.out.println("Missed...");
                board[ship.getX()-1][charToCoord.get(ship.getY())-1] = '+';
                shipsMissed++;
            }
            return 0;
        } else {
            return 1;
        }
    }

    private void checkShipHit(Coords coords, List<Ship> vessels) {
        for (Ship ship: vessels) {
            for (Coords c: ship.getPositions()) {
                if (c.getX() == coords.getX() && c.getY() == coords.getY()) {
                    ship.destroyPart();
                    if (ship.isSunk())
                        shipsSunk.add(ship.getLength());
                    break;
                }
            }
        }
    }

    public void displayBoard(){

        /*
        args: None
        rtype: void.
        precon: None

        Outputs the board to console, used newline characters to emulate a
        matrix like appearance. Will display location of all ships.
         */

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + ", ");
            }
            System.out.println();
        }
    }

    public void displayHit(){

        /*
        args: None
        rtype: void.
        precon: None

        Outputs the board to console, used newline characters to emulate a
        matrix like appearance. Will display location of successful and un-successful
        attacks only.
         */

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 'x')
                    System.out.print(String.format("%sx", Color.RED.getHex()) + String.format("%s, ", Color.WHITE.getHex()));
                else
                    System.out.print((board[i][j] == '+' ? String.format("%sx", Color.YELLOW.getHex()) : '-') + String.format("%s, ", Color.WHITE.getHex()));
            }
            System.out.println();
        }
    }

}
