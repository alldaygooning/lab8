package gui.login;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gui.CustomField;

public class LoginField extends CustomField{
	private static final long serialVersionUID = 1L;

	private LoginField loginField;
	
	public LoginField() {
		super(25);
		this.loginField = this;
		this.addErrorClearConditionListners();
	}
	
	private void addErrorClearConditionListners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				loginField.clearError();
			}
		});
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				loginField.clearError();
			}
		});
	}
}
