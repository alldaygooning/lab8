package command;
import java.util.*;

import application.*;
import attribute.TextColor;
import module.ConnectionModule;



public class HelpCommand extends Command{
	public HelpCommand() {
		this.name = "help";
		this.displayName = "help [команда]";
		this.description = "Вывести справку по доступным командам.";
		this.connectionRequired = false;
		this.authorizationRequired = false;
	}
	
	public void execute(List<String> input) { 
		if(input.size() == 0 || !Invoker.getCommands().containsKey(input.get(0))) {
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			Invoker.getCommands().values().stream().forEach(v -> {
				if ((v.connectionRequired && !ConnectionModule.isConnected()) | (!User.isAuthorized() && v.authorizationRequired)) {
					System.out.println(TextColor.faint(v.toString()));
				}
				else {
					System.out.println(v.toString());
				}
			});
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		}
		else {
			Invoker.getCommands().get(input.get(0)).getFullDescription();
		}
	}
}
