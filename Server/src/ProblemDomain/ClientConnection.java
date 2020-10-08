package ProblemDomain;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Matt Jones
 * @version 2
 *
 * Freshly stolen from Nick Hamnett because i didn't want to rewrite it
 * Connection class that handles a client connection with their own input
 * and output object streams
 */

public class ClientConnection {
	private final Socket socket;
	private final ObjectInputStream objectInput;
	private final ObjectOutputStream objectOutput;
	
	public ClientConnection(Socket socket, ObjectInputStream objectInput, ObjectOutputStream objectOutput) {
		this.socket = socket;
		this.objectInput = objectInput;
		this.objectOutput = objectOutput;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectInputStream getObjectInput() {
		return objectInput;
	}

	public ObjectOutputStream getObjectOutput() {
		return objectOutput;
	}
}
