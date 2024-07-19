package command;

import java.io.Serializable;
import java.util.List;

import item.User;
import module.DatabaseModule;
import wrapper.Response;

public class ChangePasswordCommand extends Command implements Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Response execute(String executingUser, List<String> args) {
		String login = args.get(0);
		String password = args.get(1);
		User user = User.findUserByName(login);
		user.changePassword(password);
		DatabaseModule.updateUserPassword(user);
		
		return new Response(true);
	}
	
	@Override
	public boolean requiresAuthorization() {
		return false;
	}
}
