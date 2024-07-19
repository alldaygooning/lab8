package gui.event;

import java.awt.*;

import attribute.NotificationType;

public class NotificationDisplayRequestEvent extends AWTEvent{
	private static final long serialVersionUID = 1L;
	
	NotificationType type;
	int text_id;

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public int getText_id() {
		return text_id;
	}

	public void setText_id(int text_id) {
		this.text_id = text_id;
	}

	public NotificationDisplayRequestEvent(Object source, NotificationType type, int text_id) {
		super(source, 15001);
		this.text_id = text_id;
		this.type = type;
	}
	
}
