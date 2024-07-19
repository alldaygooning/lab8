package gui.event;

import java.awt.*;

public class ErrorEvent extends AWTEvent{
	private static final long serialVersionUID = 1L;
	
	private int error_id;
	
	public ErrorEvent(Object source, int error_id) {
		super(source, 15000);
		this.error_id = error_id;
	}
	
	public int getError() {
		return this.error_id;
	}
}
