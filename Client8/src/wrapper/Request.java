package wrapper;

import java.io.Serializable;
import java.util.List;

import command.Command;

public class Request implements Serializable{
	private static final long serialVersionUID = 1L;
	public String login;
	public String password;
	
	public Command command;
	public List<String> args;
	
	public Request(Command command, List<String> args) {
		this.command = command;
		this.args = args;
	}
}
