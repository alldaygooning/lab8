package gui;

import java.awt.*;

public class GraphicUtilities {
	
	public static Color errorColor = new Color(0xBA1200); //engineering red
	public static Color textColor = new Color(0x322C2B); //rich black
	public static Color backgroundColor = new Color(0xF8F4E1); 
	public static Color primaryColor = new Color(0xD1BB9E);
	public static Color secondaryColor = new Color(0x543310);
	public static Color transparent = new Color(0, 0, 0, 0); //transparent
	public static Color successColor = new Color(0x114232); //some shade of green
	
	public static void addToGBL(Container container, Component component, int gridx, int gridy, int gridwidth, int fill, int anchor, double weightx, double weighty) {
		addToGBL(container, component, gridx, gridy, gridwidth, fill, anchor, weightx, weighty, new Insets(2, 5, 3, 5));
	}
	
	public static void addToGBL(Container container, Component component, int gridx, int gridy, int gridwidth, int fill, int anchor, double weightx, double weighty, Insets insets) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = insets;
		
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = gridwidth;
		constraints.fill = fill;
		constraints.anchor = anchor;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		
		container.add(component, constraints);
	}
}
