package gui.main.tab.tool.search;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import gui.*;

public class SearchButton extends CustomButton{
	private static final long serialVersionUID = 1L;
	
	
	
	private static final String pathToIcon = "content" + File.separator + "searchIcon.png";
	private static final Dimension preferredSize = new Dimension(36, 36);
	
	CustomField searchField;
	SearchController searchController;
	
	public SearchButton() {
		super();
		this.setScaledIcon(pathToIcon, (preferredSize.width-2), (preferredSize.height-2), Image.SCALE_SMOOTH);
		
		this.setPreferredSize(preferredSize);
		this.setBorderPainted(false);
		
		this.setListener();
	}
	
	public void setSearchField(CustomField searchField) {
		this.searchField = searchField;
	}
	
	private void setListener() {
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchController.search(searchField.getText());
				searchField.setText("");
			}
		});
	}
	
	public void setSearchController(SearchController searchController) {
		this.searchController = searchController;
	}
}
