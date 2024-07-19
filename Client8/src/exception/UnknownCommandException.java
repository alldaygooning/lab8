package exception;

public class UnknownCommandException extends RuntimeException{
	public UnknownCommandException(String commandName) {
		super(commandName);
	}
}
