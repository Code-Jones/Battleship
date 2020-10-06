package com.jones.Board;

import com.jones.ProblemDoimain.Tile;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private final int gameBoardSize;

    public GameBoard(int gameBoardSize) {
        this.gameBoardSize = gameBoardSize;
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50), null));
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        GridBagConstraints constraints = new GridBagConstraints();

        for (int i = 0; i < gameBoardSize; i++) {
            constraints.gridx = i;
            for (int j = 0; j < gameBoardSize; j++) {
                constraints.gridy = j;
                this.add(new Tile(i + 1, j + 1), constraints);
            }
        }
    }


}





