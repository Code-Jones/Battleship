package com.jones.Client;

import com.jones.Board.Coordinate;
import com.jones.ProblemDoimain.Player;

import java.util.ArrayList;

public class GameController {
    Player playerOne;
    Player playerTwo;
    public ArrayList<Coordinate> globalGridPlayer;
    public ArrayList<Coordinate> globalGridOpponent;

    public GameController() {
        this.globalGridPlayer = new ArrayList<>();
        this.globalGridOpponent = new ArrayList<>();
        this.playerOne = new Player(true, globalGridPlayer);
        this.playerTwo = new Player(false, globalGridOpponent);
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();
        ClientGUI gui = new ClientGUI("Battleship", gameController);

    }

    public Player getPlayer() {
        return playerOne;
    }

    public Player getPlayer2Data() {
        return playerTwo;
    }
}
