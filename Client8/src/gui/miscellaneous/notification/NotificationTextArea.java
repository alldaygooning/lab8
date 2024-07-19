package gui.miscellaneous.notification;

import java.awt.*;

import application.Application;
import gui.CustomTextArea;
import gui.TranslatedContainer;

public class NotificationTextArea extends CustomTextArea implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private int text_id;

	public NotificationTextArea(int text_id) {
		this.text_id = text_id;
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setEditable(false);
		
		this.setText(Application.getLocalizationModule().getStringByCode(text_id));
		this.setForeground(Color.BLACK);
		this.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		this.setBounds(8, 43, 232, 107);
	}

	@Override
	public void translate() {
		this.setText(Application.getLocalizationModule().getStringByCode(text_id));
		revalidate();
	}
}
