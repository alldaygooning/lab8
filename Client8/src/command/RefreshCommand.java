package command;

import java.util.List;

import exception.ConnectionException;
import module.ConnectionModule;

public class RefreshCommand extends Command{
	public RefreshCommand() {
		this.name = "refresh";
		this.displayName = "refresh";
		this.description = "Обновить данные о коллекции на локальном устройстве.";
	}
	@Override
	public void execute(List<String> args) throws ConnectionException {
		ConnectionModule.restartJsonRefresh();
	}
}
