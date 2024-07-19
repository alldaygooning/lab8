package item;

import attribute.*;	

public class PersonBuilder {
	public String name; //Поле не может быть null, Строка не может быть пустой
	public Long height; //Поле может быть null, Значение поля должно быть больше 0
	public int weight; //Значение поля должно быть больше 0
	public Color hairColor; //Поле не может быть null
	public Country nationality; //Поле может быть null
    
    
    public PersonBuilder name(String name) {
    	this.name = name;
    	return this;
    }
    
    public PersonBuilder height(Long height) {
    	this.height = height;
    	return this;
    }
    
    public PersonBuilder weight(int weight) {
    	this.weight = weight;
    	return this;
    }
    
    public PersonBuilder hairColor(Color hairColor) {
    	this.hairColor = hairColor;
    	return this;
    }
    
    public PersonBuilder nationality(Country nationality) {
    	this.nationality = nationality;
    	return this;
    }
}

