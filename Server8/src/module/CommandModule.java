package module;

import java.util.*;
import java.util.concurrent.*;

import command.*;
import wrapper.Response;

public class CommandModule {
	
	private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()/4);
	
	public static Response execute(String user, Command command, List<String> args) {
		return Invoker.execute(user, command, args);
	}
	
	public static ExecutorService executor() {
		return executor;
	}
}

