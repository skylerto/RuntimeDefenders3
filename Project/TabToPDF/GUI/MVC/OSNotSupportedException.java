package MVC;

/**
 * Error throw a new error for a non supported operating system.
 * 
 * @author cse23170
 * 
 */
public class OSNotSupportedException extends Exception {

	public OSNotSupportedException(String message) {
		super(message);
	}

}
