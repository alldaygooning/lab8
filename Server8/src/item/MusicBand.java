package item;

import attribute.*;
import collection.MusicBandBuilder;

import java.io.Serializable;
import java.time.LocalDate;


public class MusicBand implements Comparable<MusicBand>, Serializable {
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
    
    
   
    
   
    
    
    
    
    
  
    
    public String getName() {
    	return this.name;
    }
    public Long getNumberOfParticipants() {
    	return(numberOfParticipants);
    }
    public Coordinates getCoordinates() {
		return coordinates;
	}












	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}












	public void setId(int id) {
		this.id = id;
	}












	public void setName(String name) {
		this.name = name;
	}












	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}












	public void setNumberOfParticipants(Long numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}












	public void setGenre(MusicGenre genre) {
		this.genre = genre;
	}












	public void setFrontMan(Person frontMan) {
		this.frontMan = frontMan;
	}












	public void setOwner(String owner) {
		this.owner = owner;
	}












	public Double getX() {
    	return this.coordinates.getX();
    }
    public Double getY() {
    	return this.coordinates.getY();
    }
    public LocalDate getCreationDate() {
    	return this.creationDate;
    }
    public MusicGenre getGenre() {
    	return this.genre;
    }
    public Person getFrontMan() {
    	return this.frontMan;
    }
    public int getId()	{
    	return this.id;
    }
    public String getOwner() {
		return owner;
	}
    

    
    @Override
    public String toString() {
    	return ("Групповой id: "+ TextColor.bold(String.valueOf(id)) + ". Название группы: " +TextColor.bold(name) + "\nКоординаты расположения: " + coordinates.toString() + ". Дата основания: " + creationDate + 
    			"\nКоличество участников: " + numberOfParticipants + ". Жанр: " + genre + "\nФронтмен: " + frontMan.toString() + " Владелец:" + TextColor.bold(owner));
    }
    @Override
	public int compareTo(MusicBand other) {
		return (this.name.compareToIgnoreCase(other.name));
	}
	
	
}