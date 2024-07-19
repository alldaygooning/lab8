package item;


import java.io.Serializable;
import java.time.*;
import java.util.*;

import attribute.*;

public class MusicBand implements Comparable<MusicBand> , Serializable{
	private static final long serialVersionUID = 1L;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле не может быть null, Значение поля должно быть больше 0
    private MusicGenre genre; //Поле может быть null
    private Person frontMan; //Поле не может быть null
    private String owner;
    
    
    public MusicBand(MusicBandBuilder builder) {
    	this.id = builder.id;
    	this.name = builder.name;
    	this.coordinates = builder.coordinates;
    	this.creationDate = builder.creationDate;
    	this.numberOfParticipants = builder.numberOfParticipants;
    	this.genre = builder.genre;
    	this.frontMan = builder.frontMan;
    	this.owner = builder.owner;
    }
    
    public void setX(Double x) {
    	this.coordinates = new Coordinates(x, this.coordinates.getY());
    }

    public void setY(Double y) {
    	this.coordinates = new Coordinates(this.coordinates.getX(), y);
    }
    
    public Double getX() {
    	return this.coordinates.getX();
    }
    
    public Double getY() {
    	return this.coordinates.getY();
    }

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Coordinates getCoordinates() {
		return coordinates;
	}



	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}



	public LocalDate getCreationDate() {
		return creationDate;
	}



	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}



	public Long getNumberOfParticipants() {
		return numberOfParticipants;
	}



	public void setNumberOfParticipants(Long numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}



	public MusicGenre getGenre() {
		return genre;
	}



	public void setGenre(MusicGenre genre) {
		this.genre = genre;
	}



	public Person getFrontMan() {
		return frontMan;
	}



	public void setFrontMan(Person frontMan) {
		this.frontMan = frontMan;
	}



	public String getOwner() {
		return owner;
	}



	public void setOwner(String owner) {
		this.owner = owner;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public int compareTo(MusicBand other) {
		return (this.name.compareToIgnoreCase(other.name));
	}
	
	public List<String> getArgs(){
		List<String> args = new ArrayList<String>(Arrays.asList(getName(), String.valueOf(getX()),String.valueOf(getY()), String.valueOf(getNumberOfParticipants()), getGenre().toName(),
				String.valueOf(frontMan.getName()), String.valueOf(frontMan.getHeight()), String.valueOf(frontMan.getWeight()), frontMan.getHairColor().toName(), frontMan.getNationality().toName()
				));
		return args;
	}
	
	@Override
    public String toString() {
    	return ("Групповой id: "+ TextColor.bold(String.valueOf(id)) + ". Название группы: " +TextColor.bold(name) + "\nКоординаты расположения: " + coordinates.toString() + ". Дата основания: " + creationDate + 
    			"\nКоличество участников: " + numberOfParticipants + ". Жанр: " + genre + "\nФронтмен: " + frontMan.toString() + " Владелец:" + TextColor.bold(owner));
    }
}