package gui.event;

import java.awt.AWTEvent;

public class LanguageChangeEvent extends AWTEvent{
	private static final long serialVersionUID = 1L;
	
	public static final int EVENT_ID = 15002;

	public LanguageChangeEvent(Object source) {
		super(source, EVENT_ID);
	}
}
