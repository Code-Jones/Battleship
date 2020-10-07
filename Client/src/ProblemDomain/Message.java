package ProblemDomain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Matt Jones
 * @version 2
 *
 * Freshly stolen from Nick Hamnett because i didn't want to rewrite it with modifications
 * Standard Message class that is used to send messages between users
 * holds date, username, and message
 */

public class Message implements Serializable {
	private final Date date;
	private final String username;
	private final String message;
	
	public Message(String username, String message) {
		this.date = new Date();
		this.username = username;
		this.message = message;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		return String.format("[%s] %s sent: %s", format.format(this.date), this.username, this.message);
	}

}
