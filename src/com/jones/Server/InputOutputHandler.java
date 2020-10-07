package com.jones.Server;

import com.jones.ProblemDomain.ClientConnection;
import com.jones.ProblemDomain.Message;
import com.jones.ProblemDomain.Ship;

import java.io.IOException;

public class InputOutputHandler implements Runnable {
    private final ClientConnection input;
    private final ClientConnection output;

    public InputOutputHandler(ClientConnection input, ClientConnection output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while (!this.input.getSocket().isClosed() && !this.output.getSocket().isClosed()) {
            try {
                Object object = this.input.getObjectInput().readObject();
                if (object instanceof Message) {
                    Message message = (Message) object;
                    ServerGUI.addServerMessage(message);
                    this.output.getObjectOutput().writeObject(message);
                } else if (object instanceof Ship) {
                    System.out.println("ship here");
					Ship ship = (Ship) object;
					ServerGUI.addServerMessage(new Message("test", "Received ship" + ship.getShipType()));
					this.output.getObjectOutput().writeObject(ship);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("sent something, not message");
                e.printStackTrace();
            }
        }
    }

}
