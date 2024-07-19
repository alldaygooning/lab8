package command;

import java.util.List;

import attribute.TextColor;
import exception.ConnectionException;
import exception.ExecutionCancelled;
import module.ConnectionModule;


public class ReconnectCommand extends Command implements SilentExecute{
	public ReconnectCommand() {
		name = "reconnect";
		displayName = "reconnect";
		description = "Осуществить попытку подключения к серверу.";
		this.connectionRequired = false;
		this.authorizationRequired = false;
	}
	@Override
	public void execute(List<String> args) {
		reconnect();
		
		if (!ConnectionModule.isConnected()) {
			System.out.println(TextColor.red("Ошибка! Не удалось подключиться к серверу. Некоторые функции будут недостпуны."));
		}
		else {
			System.out.println("Соединение установлено!");
		}
	}
	@Override
	public void silentExecute(List<String> args) throws ConnectionException {
		reconnect();
		
		if (!ConnectionModule.isConnected()) {
			throw new ExecutionCancelled(9);
		}
	}
	
	private void reconnect() {
		if(ConnectionModule.isConnected()) {
			throw new ExecutionCancelled(10);
		}
		ConnectionModule.reconnect();
	}
}
