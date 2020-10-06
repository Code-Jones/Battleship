package com.jones.ProblemDoimain;

import com.jones.Board.Coordinate;

import javax.swing.*;
import java.awt.*;


public class Tile extends JPanel {
    public String id;
    public Coordinate coordinate;

    public Tile(int cord_x, int cord_y) {
        this.coordinate = new Coordinate(cord_x, cord_y);
        this.id = (cord_x + " " + cord_y);
        this.setBackground(Color.BLUE);
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        this.setPreferredSize(new Dimension(25, 25));
        JLabel label = new JLabel(cord_x + " " + cord_y);
        this.add(label);
    }

    @Override
    public String toString() {
        return id;
    }


}