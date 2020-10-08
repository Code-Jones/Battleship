package Server;
import ProblemDomain.ClientConnection;

/**
 * @author Matt Jones
 * @version 2
 *
 * Freshly stolen from Nick Hamnett because i didn't want to rewrite it
 * Handles two client connection's input and output object streams
 * and puts them into a single thread
 */

public class ClientHandler implements Runnable {
	private final ClientConnection connection1;
	private final ClientConnection connection2;
	
	public ClientHandler(ClientConnection connection1, ClientConnection connection2) {
		this.connection1 = connection1;
		this.connection2 = connection2;
	}
	
	@Override
	public void run() {
		System.out.println("Waiting for messages...");

		InputOutputHandler ioHandler1 = new InputOutputHandler(this.connection1, this.connection2);
		Thread thread1 = new Thread(ioHandler1);
		
		thread1.start();
		
		InputOutputHandler ioHandler2 = new InputOutputHandler(this.connection2, this.connection1);
		Thread thread2 = new Thread(ioHandler2);
		
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
