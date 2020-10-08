package Client;

import Exception.ObjectNotRecognized;
import ProblemDomain.Coordinate;
import ProblemDomain.Message;
import ProblemDomain.Ship;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Matt Jones
 * @version 2
 * <p>
 * Freshly stolen from Nick Hamnett because i didn't want to rewrite it and modified
 * Connection class that handles a client connection with the server.
 * Handles the input object streams and receives objects.
 * Puts into generic object class and checks instance of to determine actions needed.
 */

public class ServerHandler implements Runnable {
    private final ClientGUI gui;
    private final Socket server;
    private final ObjectInputStream inputStream;

    public ServerHandler(ClientGUI gui, Socket server, ObjectInputStream inputStream) {
        this.gui = gui;
        this.server = server;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while (!this.server.isClosed()) {
            try {
                Object object = this.inputStream.readObject();
                // message
                if (object instanceof Message) {
                    Message receive = (Message) object;
                    if (receive.getUsername().equals("Admin"))
                        if (receive.getMessage().equals("true")) {
                            gui.gameController.player.isTurn = true;
                            gui.gameController.opponent.isTurn = false;
                            System.out.println("is my turn");
                        } else {
                            gui.gameController.player.isTurn = false;
                            gui.gameController.opponent.isTurn = true;
                            System.out.println("not my turn");
                        }
                    this.gui.addClientMessage(receive);
                  // ship
                } else if (object instanceof Ship) {
                    Ship ship = (Ship) object;
                    this.gui.gameController.opponent.addToFleet(ship);
                  // coordinates
                } else if (object instanceof Coordinate) {
                    Coordinate cord = (Coordinate) object;
                    for (Coordinate coordinate : this.gui.gameController.player.globalGrid) {
                        if (coordinate.getY() == cord.getY() && coordinate.getX() == cord.getX()) {
                            coordinate.setHit(true);
                            // determined this is the right coordinates
                            // take coordinates and turn it into a panels from players board
                            JPanel pp = (JPanel) this.gui.playerBoard.getComponentAt(coordinate.getX(), coordinate.getY());
                            Component[] temp = pp.getComponents();
                            ArrayList<JPanel> panList = new ArrayList<>();
                            for (Component component : temp) {
                                panList.add((JPanel) component);
                            }
                            // finally compare all panels to coordinates
                            for (JPanel panel : panList) {
                                if ((panel.getX() / 25 + 1) == coordinate.getX() && (panel.getY() / 25 + 1) == coordinate.getY()) {
                                    // get the selected tile on gui
                                    if (coordinate.isPartOfShip()) {
                                        panel.setBackground(Color.red);
                                        // check for win
                                        boolean win = true;
                                        for (Coordinate c : this.gui.gameController.player.globalGrid) {
                                            if (c.isPartOfShip() & !c.isHit()) {
                                                win = false;
                                            }
                                        }
                                        if (win) {
                                            int reply = JOptionPane.showConfirmDialog(null, "My god, you lost. Play again? ", "Game Over", JOptionPane.YES_NO_OPTION);
                                            if (reply == JOptionPane.YES_OPTION) {
                                                JOptionPane.showMessageDialog(null, "Well guess what. i haven't done this yet");
                                                System.exit(0);
                                            } else {
                                                JOptionPane.showMessageDialog(null, "GOODBYE");
                                                System.exit(0);
                                            }
                                        }
                                    } else {
                                        panel.setBackground(Color.GRAY);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("Received missile : " + cord.toString());
//					this.gui.gameController.player.sendMissile(cord);
//					System.out.println(this.gui.gameController.opponent.fleet.size());
                } else {
                    throw new ObjectNotRecognized("Received Object not recognized");
                }
            } catch (ClassNotFoundException | IOException | ObjectNotRecognized e) {
                e.printStackTrace();
            }
        }
    }

}
