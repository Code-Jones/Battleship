package com.battleship.server;

import com.battleship.problemdomain.ClientConnection;
import com.battleship.problemdomain.Message;

import javax.swing.*;
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
				Message message = (Message) this.input.getObjectInputStream().readObject();
				
				System.out.println("Received message: " + message.toString());

				Message send = new Message("Server", "Okay!");
				this.output.getObjectOutputStream().writeObject(send);
				this.output.getObjectOutputStream().writeObject(message);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

}
