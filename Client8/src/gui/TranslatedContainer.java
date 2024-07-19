package gui;

import java.awt.*;

public interface TranslatedContainer {
	public default void translate() {
		Container container = (Container)this;
		for(Component component : container.getComponents()) {
			if(component instanceof TranslatedContainer) {
				TranslatedContainer translatedContainer = (TranslatedContainer) component;
				translatedContainer.translate();
			}
		}
	}
}
