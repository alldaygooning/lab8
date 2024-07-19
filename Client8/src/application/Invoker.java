package application;

import java.util.*;
import java.util.stream.Stream;

import command.*;

import exception.*;
import module.ConnectionModule;

public class Invoker {
	private final static HashMap<String, Command> commands = new HashMap<>();
	
	public static void execute(List<String> input) throws ConnectionException{
		String commandName = input.get(0).toLowerCase();
		input.remove(0);
		if(!commands.containsKey(commandName)) {
			throw new UnknownCommandException(commandName);
		}
		Command command = commands.get(commandName);
		if(command.requiresConnection() && !ConnectionModule.isConnected()) {
			throw new ConnectionException();
		}
		if(command.requiresAuthorization() && !User.isAuthorized()) {
			throw new IllegalAccessError();
		}
		command.execute(input);
	}
	
	public static void silentExecute(List<String> input) throws ConnectionException{
		String commandName = input.get(0).toLowerCase();
		input.remove(0);
		if(!commands.containsKey(commandName)) { //Скорее всего можно убрать эту проверку
			throw new UnknownCommandException(commandName);
		}
		Command command = commands.get(commandName);
		if(command.requiresConnection() && !ConnectionModule.isConnected()) {
			throw new ConnectionException();
		}
		SilentExecute silentCommand = (SilentExecute) command;
		silentCommand.silentExecute(input);
	}
	
	
	
	public static void introduce(Command ... args) {
		Stream.of(args).forEach(command -> commands.put(command.name, command));
	}
	
	public static HashMap<String, Command> getCommands() {
		return(commands);
	}
}
