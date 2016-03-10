package version13;

public class CannotReadFileException extends Exception{
	
	public CannotReadFileException(){}
	
	public CannotReadFileException(String message) {
		super(message);
	}
}
