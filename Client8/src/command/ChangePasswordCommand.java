package command;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import application.*;
import exception.ConnectionException;
import module.UserInputModule;
import wrapper.*;

public class ChangePasswordCommand extends Command implements Serializable, SilentExecute{
	private static final long serialVersionUID = 1L;

	public ChangePasswordCommand() {
		name = "changepassword";
		displayName = "changepassword";
		description = "Сменить пароль текущего профиля.";
	}

	@Override
	public void execute(List<String> args) throws ConnectionException {
		String newPassword = UserInputModule.getPasswordInput();
		sendPassword(User.getLogin(), newPassword);
		System.out.println("Пароль успешно изменен!");
	}

	@Override
	public void silentExecute(List<String> args) throws ConnectionException {
		String newPassword = args.get(0);
		sendPassword(User.getLogin(), newPassword);
	}
	
	private Response sendPassword(String login, String unencryptedNewPassword) { //Отвечает за обновление пароля и его шифрование, если нужно
		String encryptedNewPassword;
		if(unencryptedNewPassword.equals("")) {
			encryptedNewPassword = "";
		}
		else {
			encryptedNewPassword = UserInputModule.encrypt(unencryptedNewPassword);
		}
		Application.send(new Request(new ChangePasswordCommand(), Arrays.asList(User.getLogin(), encryptedNewPassword)));
		Response response = Application.receive();
		User.setPassword(encryptedNewPassword);
		return response;
	}
}
