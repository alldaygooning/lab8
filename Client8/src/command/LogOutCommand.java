package command;

import java.io.Serializable;
import java.util.List;

import application.User;
import exception.ConnectionException;

public class LogOutCommand extends Command implements Serializable{

	private static final long serialVersionUID = 1L;
	public LogOutCommand() {
		this.name = "logout";
		this.displayName = "logout";
		this.description = "Выйти из профиля.";
		this.connectionRequired = false;
	}
	@Override
	public void execute(List<String> args) throws ConnectionException {
		User.logout();
		System.out.println("Успешный выход из профиля.");
	}
}
