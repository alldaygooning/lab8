package gui.main.tab.tool.search;

import java.awt.*;

import gui.*;

public class SearchPanel extends CustomPanel{
	private static final long serialVersionUID = 1L;
	
	SearchButton searchButton;
	CustomField searchField;
	
	public SearchPanel() {
		super();
		
		this.setLayout(new GridBagLayout());
		
		this.setButton();
		this.setField();
	}
	
	private void setField() {
		searchField = new CustomField(25);
		searchButton.setSearchField(searchField);
		
		searchField.setPreferredSize(new Dimension(40, 40));
		
		GraphicUtilities.addToGBL(this, searchField, 0, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 1);
	}
	
	private void setButton() {
		searchButton = new SearchButton();
		
		GraphicUtilities.addToGBL(this, searchButton, 0, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1, new Insets(10, 10, 10, 10));
	}
	
	public void setSearchController(SearchController searchController) {
		searchButton.setSearchController(searchController);
	}
}
