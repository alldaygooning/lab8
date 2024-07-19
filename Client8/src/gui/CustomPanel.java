package gui;

import javax.swing.*;

public abstract class CustomPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public CustomPanel() {
		super();
		this.setBackground(GraphicUtilities.transparent);
	}
	
	protected void translate() {
		this.revalidate();
	}
}
