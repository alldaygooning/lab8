package gui.miscellaneous.profile;

import java.awt.*;
import java.io.File;

import gui.*;

public class ProfileButton extends CustomButton implements TranslatedContainer{
	private static final long serialVersionUID = 1L;

	private static final String pathToIcon = "content" + File.separator + "profile.jpg";
	private static final Dimension preferredSize = new Dimension(40, 40);
	private static final String toolTipKey = "profile_button_tooltip";
	
	public ProfileButton() {
		super();
		
		this.setScaledIcon(pathToIcon, 35, 35, Image.SCALE_SMOOTH);
		
		this.setPreferredSize(preferredSize);
		
		this.setToolTip();
		
		this.addActionListener(new ProfileButtonActionListner());
	}
	
	private void setToolTip() {
		this.setToolTipKey(toolTipKey);
	}
	
	@Override
	public void translate() {
		super.translate();
	}
}

