package command;

import java.util.*;

import wrapper.Response;


public class Invoker{

	public static Response execute(String user, Command command, List<String> args) {
		return command.execute(user, args);
	}
}