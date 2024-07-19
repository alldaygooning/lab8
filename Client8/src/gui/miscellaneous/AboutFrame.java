package gui.miscellaneous;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import gui.*;

public class AboutFrame extends CustomFrame implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private static final String title = "About what?";
	private static final String pathToIcon = "content" + File.separator + "about.png";
	private static final Dimension launchSize = new Dimension(400, 200);
	
	AboutTextArea textArea1;
	AboutTextArea textArea2;

	public AboutFrame() {
		super(title, pathToIcon, launchSize);
		
		this.center();
		this.setLayout(new GridBagLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		textArea1 = new AboutTextArea("about_text_1");
		textArea2 = new AboutTextArea("about_text_2");
		
		GraphicUtilities.addToGBL(this, textArea1, 0, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTH, 1, 1);
		GraphicUtilities.addToGBL(this, textArea2, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTH, 1, 1);
	}
	
	@Override
	public void translate() {
		super.translate();
	}
}