package thread;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.List;

import command.*;
import item.User;
import module.CommandModule;
import module.ResponseModule;
import wrapper.*;


public class CommandExecutingTask implements Runnable{
	Request request;
	SocketAddress clientAddress;
	DatagramChannel clientChannel;
	public CommandExecutingTask(Request request, SocketAddress clientAddress, DatagramChannel clientChannel) {
		this.request = request;
		this.clientAddress = clientAddress;
		this.clientChannel = clientChannel;
	}
	
	
	@Override
	public void run() {
		Command command = request.command;
		List<String> args = request.args;
		Response response;
		if (!command.requiresAuthorization()) {
			response = CommandModule.execute("nologin", command, args);
		}
		else {
			String login = request.login;
			String password = request.password;
			User user = User.users.stream().filter(u -> u.getName().equals(login)).findAny().orElse(null);
			if(user == null || !user.getPassword().equals(password)) {
				response = new Response(false, 57);
			}
			else {
				response = CommandModule.execute(login, command, args);
			}
		}
		ResponseModule.responses.add(new ResponsePackage(response, clientAddress, clientChannel));
	}
}
