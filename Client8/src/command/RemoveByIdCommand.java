package command;

import java.io.Serializable;
import java.util.*;

import application.Application;
import application.MusicBandHandler;
import attribute.TextColor;
import exception.*;
import module.*;
import wrapper.*;

public class RemoveByIdCommand extends Command implements Serializable, SilentExecute{
	private static final long serialVersionUID = 1L;

	MusicBandHandler musicBandHandler;

	public RemoveByIdCommand() {};
	
	public RemoveByIdCommand(MusicBandHandler musicBandHandler) {
		this.musicBandHandler = musicBandHandler;
		this.name = "remove";
		this.displayName = "remove [id]";
		this.description = "Удалить элемент из коллекции по его уникальному id.";
		this.fullDescription = TextColor.bold("Удалить элемент из коллекции по его уникальному id.")+
				"\nremove [id] - на месте [id] должен располагаться уникальный номер группы, которую необходимо удалить. "
				+ "\nЧтобы удалить несколько групп за раз введите "+TextColor.bold("через пробел")+" значения id." 
				+ "\nВведите команду show для просмотра всех групповых id.";
	}

	public void execute(List<String> args) {
		if(!UserInputModule.validateId(args.get(0))) {
			throw new ExecutionCancelled(0);
		}
		int id = Integer.parseInt(args.get(0));
		send(args);
		remove(id);
		System.out.println("Группа под номером " + args.get(0) + " удалена!	");
	}


	@Override
	public void silentExecute(List<String> args) throws ConnectionException {
		int id = Integer.parseInt(args.get(0));
		send(args);
		remove(id);
	}
	
	private Response send(List<String> args) {
		Application.send(new Request(new RemoveByIdCommand(), args));
		Response response = Application.receive();
		return response;
	}
	
	private void remove(int id) {
		musicBandHandler.removeById(id);
	}
}
