package org.example.util;

import javax.swing.*;

public class CJButton extends JButton {

    /*
    Wrapper class for JButton which enables easier submission of
    co-ordinates to the controller.
    */

    private Coords coords = new Coords(0, 'a');

    public void setCoords(Coords c){ coords = c; }

    public Coords getCoords(){ return coords; }

}
