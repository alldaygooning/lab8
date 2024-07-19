package item;


import java.io.Serializable;

import attribute.*;
import collection.PersonBuilder;

public class Person implements Serializable{
    private static final long serialVersionUID = 1L;
	private String name; //Поле не может быть null, Строка не может быть пустой
    private Long height; //Поле может быть null, Значение поля должно быть больше 0
    private int weight; //Значение поля должно быть больше 0
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    
    
    public Person(PersonBuilder builder) {
    	this.name = builder.name;
    	this.height = builder.height;
    	this.weight = builder.weight;
    	this.hairColor = builder.hairColor;
    	this.nationality = builder.nationality;
    }
    
    public String getName() {
    	return this.name;
    }
    public Long getHeight() {
    	return this.height;
    }
    public int getWeight() {
    	return this.weight;
    }
    public Color getHairColor() {
    	return this.hairColor;
    }
    public Country getCountry()	{
    	return this.nationality;
    }
    
    
    
    @Override
    public String toString() {
    	return (name + " " + height + "см " + weight + "кг Цвет волос:" + hairColor + " Страна:" + nationality);
    }
}