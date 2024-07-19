package command;

import java.io.Serializable;
import java.util.*;

import application.Application;
import exception.*;
import module.UserInputModule;
import wrapper.*;

public class SignUpCommand extends Command implements Serializable, SilentExecute{
	private static final long serialVersionUID = 1L;
	public SignUpCommand() {
		this.name = "signup";
		this.displayName ="signup";
		this.description = "Создать новый профиль в системе.";
		this.authorizationRequired = false;
	}
	@Override
	public void execute(List<String> args) throws ConnectionException {
		String login = UserInputModule.getLoginInput();
		sendLogin(login);
		System.out.println(String.format("Профиль %s успешно добавлен.", login));
		
		String password = UserInputModule.getPasswordInput();
		
		if(password.equals("")) {
			return;
		}
		sendPassword(login, password);
		System.out.println("Пароль успешно установлен.");
	}
	@Override
	public void silentExecute(List<String> args) throws ConnectionException {
		
		String login = args.get(0);
		sendLogin(login);
		
		String password = args.get(1);
		if(password.equals("")) {
			return;
		}
		sendPassword(login, password);
	}
	
	
	private Response sendLogin(String login) {
		Application.send(new Request(new SignUpCommand(), Arrays.asList(login)));
		Response response = Application.receive();
		return response;
	}
	
	private Response sendPassword(String login, String unencryptedPassword) {
		String encryptedPassword = UserInputModule.encrypt(unencryptedPassword);
		Application.send(new Request(new ChangePasswordCommand(), Arrays.asList(login, encryptedPassword)));
		Response response = Application.receive();
		return response;
	}
}
