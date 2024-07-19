package command;

import java.io.Serializable;
import java.util.*;

import application.*;
import attribute.TextColor;
import exception.ConnectionException;
import module.UserInputModule;
import wrapper.*;

public class LogInCommand extends Command implements Serializable, SilentExecute{
	private static final long serialVersionUID = 1L;



	public LogInCommand() {
		this.name ="login";
		this.displayName = "login [профиль] [пароль]";
		this.description = "Авторизоваться в системе.";
		this.authorizationRequired = false;
	}

	@Override
	public void execute(List<String> args) throws ConnectionException {
		silentExecute(args);
		System.out.println("Вы зашли под профилем " + TextColor.bold(User.getLogin()) + "!");
	}

	@Override
	public void silentExecute(List<String> args) {
		String login;
		String password;
		
		if(args.size() == 2) {
			login = args.get(0);
			password = args.get(1);
			if (!password.equals("")) {
				password = UserInputModule.encrypt(password);
			}
		}
		
		else {
			login = UserInputModule.getLoginInput();
			password = UserInputModule.getPasswordAuthInput();
			if(password == null) {
				password = "";
			}
			else {
				password = UserInputModule.encrypt(password);
			}
		}
		
		Application.send(new Request(new LogInCommand(), Arrays.asList(login, password)));
		Application.receive();
		User.login(login, password);
	}
}
