package command;

import java.io.Serializable;
import java.util.List;

import item.User;
import module.DatabaseModule;
import wrapper.Response;

public class SignUpCommand extends Command implements Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Response execute(String executingUser, List<String> args) {
		String login = args.get(0);
		if(User.users.stream().filter(user -> user.getName().equals(login)).findAny().orElse(null) != null) {
			return new Response(false, 50);
		}
		User newUser = new User(login, "", "user");
		User.users.add(newUser);
		DatabaseModule.insertUser(newUser);
		
		return new Response(true);
	}
	
	@Override
	public boolean requiresAuthorization() {
		return false;
	}
}
