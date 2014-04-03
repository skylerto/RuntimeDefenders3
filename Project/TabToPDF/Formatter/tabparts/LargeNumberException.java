package tabparts;

/**
 * Is thrown when 3 or more consecutive digits are found in a string.
 * @author Ron
 *
 */
public class LargeNumberException extends Exception{
	
	public LargeNumberException(){}
	
	public LargeNumberException(String message) {
		super(message);
	}
}
