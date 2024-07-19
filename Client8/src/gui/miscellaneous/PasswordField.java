package gui.miscellaneous;

import java.awt.event.*;

import gui.*;

public class PasswordField extends CustomPasswordField{
	private static final long serialVersionUID = 1L;

	PasswordField passwordField;
	
	public PasswordField() {
		super(25);
		this.passwordField = this;
		addErrorClearConditionListners();
	}
	
	private void addErrorClearConditionListners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				passwordField.clearError();
			}
		});
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				passwordField.clearError();
			}
		});
	}
}
