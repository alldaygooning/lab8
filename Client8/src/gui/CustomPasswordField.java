package gui;

import java.awt.Color;

import javax.swing.*;

public class CustomPasswordField extends JPasswordField{
	private static final long serialVersionUID = 1L;
	
	public CustomPasswordField(int n) {
		super(n);
	}
	public void error() {
		this.putClientProperty("JComponent.outline", "error");
		this.setBackground(new Color(250, 200, 190));
	}
	
	public void clearError() {
		this.putClientProperty("JComponent.outline", "");
		this.setBackground(new Color(255, 255, 255));
	}
}
