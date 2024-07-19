package gui.miscellaneous.notification;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.JPanel;

import attribute.NotificationType;
import gui.*;

public class NotificationPanel extends CustomPanel implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	NotificationPanel notification;
	
	private final Dimension preferredSize = new Dimension(260, 140);
	private final Dimension minimumSize = new Dimension(260, 140);
	
	private NotificationType type;
	private int text_id;
	
	private final int borderWidth = 3;
	private final int backgroundTransparency = 200;
	Color backgroundColor;
	Color borderColor;
	
	NotificationTextArea notificationTextArea;
	CustomLabel notificationTypeLabel;
	
	private final static int removeDelay = 2500;

	public NotificationPanel(NotificationType type, int text_id) {
		super();
		
		this.notification = this;
		
		this.type = type;
		this.text_id = text_id;
		
		this.setPreferredSize(preferredSize);
		this.setMinimumSize(minimumSize);
		
		this.setElements();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		
		switch(type) {
			case ERROR: backgroundColor = new Color(252, 164, 156, backgroundTransparency);
				borderColor = GraphicUtilities.errorColor;
				break;
			case INFO: backgroundColor = new Color(185, 230, 252, backgroundTransparency);
				borderColor = new Color(110, 140, 250);
				break;
			case SUCCESS: backgroundColor = new Color(160, 252, 156, backgroundTransparency);
				borderColor = new Color(20, 170, 8);
				break;
			default: backgroundColor = GraphicUtilities.backgroundColor;
				borderColor = GraphicUtilities.secondaryColor;
				break;
		}
		
		graphics.setPaint(backgroundColor);
		graphics.fill(new RoundRectangle2D.Double(0, 15, 240, 120, 5, 5));
		
		graphics.setPaint(borderColor);
		graphics.setStroke(new BasicStroke(borderWidth));
		graphics.draw(new RoundRectangle2D.Double(1, 16, 238, 118, 5, 5));
		super.paint(g);
	}
	
	public void removeWithDelay() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				remove();
			}
		}, removeDelay);
	}
	
	synchronized public void remove() {
		if(this.isEnabled()) {
			this.setEnabled(false);
			JPanel glassPane = (JPanel)this.getParent();
			glassPane.remove(this);
			glassPane.repaint();
		}
	}
	
	private void setElements() {
		this.setLayout(null);
		
		CloseNotificationButton closeButton = new CloseNotificationButton();
		this.add(closeButton);
		
		notificationTextArea = new NotificationTextArea(text_id);
		this.add(notificationTextArea);
		
		notificationTypeLabel = new CustomLabel(type.getStringKey(), new Font(Font.DIALOG, Font.BOLD, 20));
		notificationTypeLabel.setBounds(8, 23, 232, 20);
		notificationTypeLabel.setForeground(Color.BLACK);
		this.add(notificationTypeLabel);
	}

	@Override
	public void translate() {
		notificationTypeLabel.translate();
		notificationTextArea.translate();
		this.repaint();
	}
}
