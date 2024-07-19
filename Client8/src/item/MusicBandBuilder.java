package item;

import java.time.LocalDate;
import java.util.List;

import attribute.*;

public class MusicBandBuilder {
    public int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    public String name; //Поле не может быть null, Строка не может быть пустой
    public Coordinates coordinates; //Поле не может быть null
    public LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    public Long numberOfParticipants; //Поле не может быть null, Значение поля должно быть больше 0
    public MusicGenre genre; //Поле может быть null
    public Person frontMan; //Поле не может быть null
    public String owner;
    
    public MusicBandBuilder id(int id) {
    	this.id = id;
    	return this;
    }
    public MusicBandBuilder name(String name) {
    	this.name = name;
    	return this;
    }
    public MusicBandBuilder coordinates(Coordinates coordinates) {
    	this.coordinates = coordinates;
    	return this;
    }
    public MusicBandBuilder creationDate(LocalDate creationDate) {
    	this.creationDate = creationDate;
    	return this;
    }
    public MusicBandBuilder numberOfParticipants(Long n) {
    	this.numberOfParticipants = n;
    	return this;
    }
    public MusicBandBuilder genre(MusicGenre genre) {
    	this.genre = genre;
    	return this;
    }
    public MusicBandBuilder frontMan(Person frontMan) {
    	this.frontMan = frontMan;
    	return this;
    }
    public MusicBandBuilder owner(String owner) {
    	this.owner = owner;
    	return this;
    }
    
    public static MusicBand build(String owner, List<String> args) {
    	Person person = new Person(new PersonBuilder()
				.name(args.get(5))
				.height((args.get(6)!=null)?Long.valueOf(args.get(6)):null)
				.weight(Integer.parseInt(args.get(7)))
				.hairColor(Color.valueOf(args.get(8)))
				.nationality(Country.valueOfNullable(args.get(9)))
				);
		
		MusicBand band = new MusicBand(new MusicBandBuilder()
				.name(args.get(0))
				.coordinates(new Coordinates(Double.valueOf(args.get(1)), Double.valueOf(args.get(2))))
				.creationDate(LocalDate.now())
				.numberOfParticipants(Long.valueOf(args.get(3)))
				.genre(MusicGenre.valueOfNullable(args.get(4)))
				.frontMan(person)
				.owner(owner)
						);
		return band;
    }
}
