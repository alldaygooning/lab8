package wrapper;

import java.io.Serializable;
import java.util.Arrays;

public class Wrapper implements Serializable{
	private static final long serialVersionUID = 1L;
	public int totalNumber;
	public int currentNumber;
	public byte[] storedData;
	public long t;
	public Wrapper(long t, int total, int number, byte[] data) {
		this.t = t;
		this.totalNumber = total;
		this.currentNumber = number;
		this.storedData = data;
	}
	@Override
	public String toString() {
		return Arrays.toString(storedData);
	}
}
