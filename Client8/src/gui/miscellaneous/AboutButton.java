package gui.miscellaneous;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.*;

public class AboutButton extends CustomButton implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private static final Dimension preferredSize = new Dimension(40, 40);
	AboutFrame aboutFrame;
	
	public AboutButton() {
		super();
		
		this.setText("?");
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		this.setToolTip();
		
		this.setPreferredSize(preferredSize);
		
		setActionListener();
		
		aboutFrame = new AboutFrame();
	}
	
	private void setToolTip() {
		this.setToolTipKey("about_button_tooltip");
	}
	
	@Override
	public void translate() {
		super.translate();
	}
	
	private void setActionListener() {
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutFrame.setVisible(true);
			}
		});
	}
}
