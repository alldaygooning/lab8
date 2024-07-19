package command;

import java.util.*;

import attribute.*;
import exception.ConnectionException;


public abstract class Command {
	String fullDescription;
	String description;
	public String name;
	String displayName;
	boolean connectionRequired = true;
	boolean authorizationRequired = true;
	
	public abstract void execute(List<String> args) throws ConnectionException;
	
	
	@Override
	public String toString() {
		return (this.displayName + " - " + this.description);
	}
	
	public boolean requiresAuthorization() {
		return authorizationRequired;
	}
	
	public boolean requiresConnection() {
		return connectionRequired;
	}
	
	public void getFullDescription() {
		if (this.fullDescription != null) {
			System.out.println(fullDescription);
		}
		else {
			System.out.println(TextColor.bold(description));
		}
	}
}
