package command;

import java.util.*;

import wrapper.Response;

public abstract class Command implements Authorization{
	String fullDescription;
	String description;
	public String name;
	String displayName;
	boolean connectionRequired = true;
	boolean authorizationRequired = true;

	
	public abstract Response execute(String user, List<String> args);
	
	
	@Override
	public String toString() {
		return (this.displayName + " - " + this.description);
	}
}
