package command;

import java.util.List;

import exception.ConnectionException;

public interface SilentExecute {
	public void silentExecute(List<String> args) throws ConnectionException;
}
