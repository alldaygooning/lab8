package gui.miscellaneous.profile;

import java.awt.event.*;

public class ProfileButtonActionListner implements ActionListener{
	
	ProfileFrame profileFrame;
	
	public ProfileButtonActionListner() {
		profileFrame = new ProfileFrame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		profileFrame.setVisible(true);
	}
}
