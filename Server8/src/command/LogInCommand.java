package command;

import java.io.Serializable;
import java.util.List;

import item.User;
import wrapper.Response;

public class LogInCommand extends Command implements Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Response execute(String executingUser, List<String> args) {
		String login = args.get(0);
		String password = args.get(1);
		User user = User.users.stream().filter(u -> u.getName().equals(login)).findAny().orElse(null);
		if (user == null) {
			return new Response(false, 55);
		}
		if(!user.getPassword().equals(password)) {
			return new Response(false, 56);
		}
		return new Response(true);
	}

	@Override
	public boolean requiresAuthorization() {
		return false;
	}
}
