package gui;

import java.awt.*;

import javax.swing.*;

import attribute.NotificationType;
import gui.event.*;
import gui.miscellaneous.notification.NotificationLayer;
import module.ConnectionModule;

public abstract class CustomFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	NotificationLayer notificationLayer;
	
	public CustomFrame(String title, String pathToIcon, Dimension launchSize) {
		super();
		setTitle(title);
		setIcon(pathToIcon);
		
		getContentPane().setBackground(GraphicUtilities.backgroundColor);
		
		setResizable(true);
		setSize(launchSize);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		notificationLayer = new NotificationLayer(this.getGlassPane());
	}
	
	public void center() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screenSize.getWidth() - this.getWidth()) / 2);
		int y = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
	}
	
	public void setIcon(String path) {
		ImageIcon icon = new ImageIcon(path);
		this.setIconImage(icon.getImage());
	}
	
	private void displayNotification(NotificationType type, int text_id) {
		notificationLayer.displayNotification(type, text_id);
		if (text_id == 4) {
			ConnectionModule.disconnect(); //Костыль, но больше пока не придумал, куда впихнуть. При потере соединения с сервером отключает сокет.
		}
	}
	
	@Override
	protected void processEvent(AWTEvent e) {
		if(e instanceof ErrorEvent) {
			this.dispatchEvent(new NotificationDisplayRequestEvent(e.getSource(), NotificationType.ERROR, ((ErrorEvent) e).getError()));
		}
		else if(e instanceof NotificationDisplayRequestEvent) {
			NotificationDisplayRequestEvent notificationDisplayRequestEvent = (NotificationDisplayRequestEvent) e;
			displayNotification(notificationDisplayRequestEvent.getType(), notificationDisplayRequestEvent.getText_id());
			return;
		}
		super.processEvent(e);
	}
	
	protected void translate() {
		Component[] components = this.getContentPane().getComponents();
		for(Component component : components) {
			if(component instanceof TranslatedContainer) {
				TranslatedContainer translatedContainer = (TranslatedContainer) component;
				translatedContainer.translate();
			}
		}
		this.revalidate();
		notificationLayer.translate();
	}
}
