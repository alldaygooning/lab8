package command;
import java.io.Serializable;
import java.util.*;

import application.Application;
import exception.ConnectionException;
import wrapper.Request;
import wrapper.Response;


public class InfoCommand extends Command implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	public InfoCommand() {
		this.name = "info";
		this.displayName = "info";
		this.description = "Вывести в стандартный поток вывода информацию о коллекции.";
	}

	@Override
	public void execute(List<String> input) throws ConnectionException{
		Application.send(new Request(new InfoCommand(), input));
		Response response = Application.receive();
		String[] responseValue = response.text.split(";");
		System.out.println(String.format("Коллекция типа: %s. Число элементов: %s.", responseValue[0], responseValue[1]));
	}
}
