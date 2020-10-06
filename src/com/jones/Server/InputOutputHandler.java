package com.jones.Server;

import com.jones.ProblemDoimain.ClientConnection;
import com.jones.ProblemDoimain.Message;

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
				e.printStackTrace();
			}

		}
	}

}
