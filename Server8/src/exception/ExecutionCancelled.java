package exception;

public class ExecutionCancelled extends RuntimeException{
	public int reason_id;
	
	public ExecutionCancelled(int reason_id) {
		this.reason_id = reason_id;
	}
}
