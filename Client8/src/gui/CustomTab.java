package gui;

import application.Application;

public class CustomTab extends CustomPanel{
	private static final long serialVersionUID = 1L;
	
	String titleKey;

	public CustomTab(String titleKey) {
		super();
		this.titleKey = titleKey;
	}
	
	public String getTitle() {
		return Application.getLocalizationModule().getString(titleKey);
	}
}
