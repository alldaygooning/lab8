package wrapper;
import org.apache.commons.lang3.ArrayUtils;

public class WrapperAssembler {
	public int totalExpected;
	public byte[] data;
	public int nextExpected = 1;
	public int currentTotal = 0;
	public long requestTime;
	public boolean opened = true;
	public WrapperAssembler() {
	}
	
	
	public void newTime(long newTime) {
		requestTime = newTime;
	}
	
	
	public void addData(byte[] newData) {
		data = ArrayUtils.addAll(data, newData);
		nextExpected++;
		currentTotal++;
	}
	
	public void discard() {
		data = new byte[0];
		nextExpected = 1;
		currentTotal = 0;
	}
	
	public boolean checkIfAllCollected() {
		return(currentTotal == totalExpected);
	}
	
	public void newExpectation(int totalNumber) {
		totalExpected = totalNumber;
	}
}
