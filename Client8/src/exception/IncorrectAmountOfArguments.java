package exception;

public class IncorrectAmountOfArguments extends RuntimeException{
	public IncorrectAmountOfArguments(String commandName) {
		super(commandName);
	}
}
