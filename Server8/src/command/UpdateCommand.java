package command;

import java.io.Serializable;
import java.util.List;

import application.Application;
import attribute.*;
import exception.ExecutionCancelled;
import wrapper.*;


public class UpdateCommand extends Command implements Serializable{
	private static final long serialVersionUID = 1L;

	public UpdateCommand() {
		this.name ="update";
		this.displayName = "update [id] [параметры объекта]";
		this.description = "Обновить значение элемента коллекции, id которого равен заданному.";
		this.fullDescription = TextColor.bold("Обновить значение элемента коллекции, id которого равен заданному.")+"\nupdate [id] - для построчного "
				+ "ввода каждого поля нового объекта.\nupdate [id] [параметры"
				+ " объекта] - для ввода всех значений полей нового объекта в одну строку."
				+ "\nПри вводе в строку необходимо вводить данные "
				+TextColor.bold("через пробел")+" в следующем формате:\n"
				+ "update [id] [имя группы] Double[координаты x и y (y <= 587)] Long[количество участников группы] [жанр]("+MusicGenre.getValues()+") ["
						+ "имя фронтмена] Long[высота фронтмена] int[вес фронтмена] [цвет волос фронтмена]("+Color.getValues()+ " )[национальность фронтмена]("+ Country.getValues()+").";;
	}

	@Override
	public Response execute(String user, List<String> args) {
		try {
			Application.MusicBandHandler.update(user, args);
			return new Response(true);
		}catch(ExecutionCancelled e) {
			return new Response(false, e.reason_id);
		}
	}
}
