package gui.miscellaneous.notification;

import java.awt.*;

import javax.swing.JPanel;

import attribute.NotificationType;
import gui.GraphicUtilities;
import gui.TranslatedContainer;

public class NotificationLayer implements TranslatedContainer{

	private JPanel glassPane;
	
	public NotificationLayer(Component component) {
		
		glassPane = (JPanel) component;
		
		glassPane.setLayout(new GridBagLayout());
		
		glassPane.setVisible(true);
	}
	
	public void displayNotification(NotificationType type, int text_id) {
		
		NotificationPanel notification = new NotificationPanel(type, text_id);
		
		int width = glassPane.getWidth();
		int height = glassPane.getHeight();
		if(width < 500 || height < 200) {
			GraphicUtilities.addToGBL(glassPane, notification, 0, 0, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 1, 1);
		}
		else {
			GraphicUtilities.addToGBL(glassPane, notification, 0, 0, 1, GridBagConstraints.NONE, GridBagConstraints.SOUTHWEST, 1, 1);
		}
		notification.removeWithDelay();
		
		glassPane.revalidate();
	}

	@Override
	public void translate() {
		Component[] components = glassPane.getComponents();
		for(Component component : components) {
			if(component instanceof TranslatedContainer) {
				TranslatedContainer translatedComponent = (TranslatedContainer) component;
				translatedComponent.translate();
			}
		}
		glassPane.revalidate();
		glassPane.repaint();
	}
}
