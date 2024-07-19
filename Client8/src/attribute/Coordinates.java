package attribute;

import java.io.Serializable;

public class Coordinates implements Serializable{
	private static final long serialVersionUID = 1L;
	private Double x; //Поле не может быть null
    private Double y; //Максимальное значение поля: 587, Поле не может быть null
	public Coordinates(Double x2, Double y2) {
		this.x = x2;
		this.y = y2;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	@Override
    public String toString() {
    	return ("(" + x + ";" + y + ")");
    }
}