package version11;

/**
 * Is thrown when two pulls or hammers are seen one after another in a staff.
 * 
 * Example:
 * 
 * |---3h---||---p3---|
 * |--------||--------| 
 * |--------||--------|
 * |--------||--------|
 * |--------||--------|
 * |--------||--------|
 * 
 * This will throw the exception.
 * 
 * @author Ron
 *
 */
public class InvalidSymbolPosition extends Exception{
	
	public InvalidSymbolPosition(){}
	
	public InvalidSymbolPosition(String message) {
		super(message);
	}
}
