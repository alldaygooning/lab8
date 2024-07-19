package attribute;

import java.io.Serializable;

public class Coordinates implements Serializable{
    private static final long serialVersionUID = 1L;
	private Double x; //Поле не может быть null
    private Double y; //Максимальное значение поля: 587, Поле не может быть null
    public Coordinates(Double x, Double y) { //Для всех случаев
    	this.x = x;
    	this.y = y;
    }
    
    @Override
    public String toString() {
    	return ("(" + x + ";" + y + ")");
    }
    
    public Double getX() {
    	return this.x;
    }
    public Double getY() {
    	return this.y;
    }
    
    public String[] getFields() {
    	return new String[] {String.valueOf(x), String.valueOf(y)};
    }
}