package gui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class CustomField extends JTextField{
	private static final long serialVersionUID = 1L;

	CustomField customField;
	
	public CustomField(int n) {
		super(n);
		this.customField = this;
		addErrorClearConditionListners();
	}
	
	public void error() {
		this.putClientProperty("JComponent.outline", "error");
		this.setBackground(new Color(250, 200, 190));
	}
	
	public void clearError() {
		this.putClientProperty("JComponent.outline", "");
		this.setBackground(new Color(255, 255, 255));
	}
	private void addErrorClearConditionListners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				customField.clearError();
			}
		});
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				customField.clearError();
			}
		});
	}
}
