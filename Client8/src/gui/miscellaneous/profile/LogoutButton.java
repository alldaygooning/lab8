package gui.miscellaneous.profile;

import java.awt.Window;
import java.awt.event.*;

import application.*;
import gui.*;
import gui.login.LoginFrame;

public class LogoutButton extends CustomButton implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	LogoutButton logoutButton;
	
	public LogoutButton() {
		super("logout_button");
		this.logoutButton = this;
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User.logout();
				for(Window window : Window.getWindows()) {
					if(window.isEnabled()) {
						window.dispose();
					}
				}
				new LoginFrame();
			}
		});
	}
	
	@Override
	public void translate() {
		super.translate();
	}
}
