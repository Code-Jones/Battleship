package com.jones.Server;

import com.jones.ProblemDoimain.ClientConnection;
import com.jones.ProblemDoimain.Message;
import com.jones.ProblemDoimain.Ship;

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
				Message message = (Message) this.input.getObjectInput().readObject();
				System.out.println("Received message: " + message.toString());
				ServerGUI.addServerMessage(message);
				this.output.getObjectOutput().writeObject(message);
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("sent something, not message");
				e.printStackTrace();
			}
			try {
				Ship ship = (Ship) this.input.getObjectInput().readObject();
				System.out.println("Received ship: " + ship.toString());
				ServerGUI.addServerMessage(new Message(ship.getShipType().toString(), "Received"));
				this.output.getObjectOutput().writeObject(ship);
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("ship went wrong");
				e.printStackTrace();
			}

		}
	}

}
