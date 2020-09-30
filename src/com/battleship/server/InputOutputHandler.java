package com.battleship.server;

import com.battleship.problemdomain.ClientConnection;
import com.battleship.problemdomain.Message;
import java.io.IOException;

public class InputOutputHandler implements Runnable {
	private final ServerGUI gui;
	private final ClientConnection input;
	private final ClientConnection output;
	
	public InputOutputHandler(ServerGUI gui, ClientConnection input, ClientConnection output) {
		this.gui = gui;
		this.input = input;
		this.output = output;
	}
	
	@Override
	public void run() {
		while (!this.input.getSocket().isClosed() && !this.output.getSocket().isClosed()) {
			try {
				Message message = (Message) this.input.getObjectInputStream().readObject();
				
				System.out.println("Received message: " + message.toString());
				gui.addMessage(message);

				Message send = new Message("Server", "Okay!");
				this.output.getObjectOutputStream().writeObject(send);
				this.output.getObjectOutputStream().writeObject(message);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

}
