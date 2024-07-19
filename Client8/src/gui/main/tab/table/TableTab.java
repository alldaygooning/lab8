package gui.main.tab.table;


import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import gui.*;
import gui.main.DataUpdateListener;
import gui.main.tab.tool.help.HelpPanel;
import gui.main.tab.tool.search.SearchPanel;

public class TableTab extends CustomTab implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private static final String titleKey = "table_tab";
	
	TableTab tab;
	
	MusicBandTable musicBandTable;
	JTableHeader header;
	
	HelpPanel helpPanel;
	SearchPanel searchPanel;
	
	public TableTab() {
		super(titleKey);
		this.tab = this;
		
		this.setLayout(new GridBagLayout());
		
		setTable();
		setHeader();
		setFooter();
	}
	
	private void setTable() {
		musicBandTable = new MusicBandTable();
		JScrollPane scrollableTable = new JScrollPane(musicBandTable);
		
		GraphicUtilities.addToGBL(this, scrollableTable, 0, 1, 2, GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 1);
	}
	
	private void setHeader() {
		header = musicBandTable.getTableHeader();
		musicBandTable.adjustColumns();
		
		GraphicUtilities.addToGBL(this, header, 0, 0, 2, GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 1);
	}
	
	private void setFooter() {
		searchPanel = new SearchPanel();
		searchPanel.setSearchController(musicBandTable.getSearchController());
		
		helpPanel = new HelpPanel();
		helpPanel.setBarController(musicBandTable.getBarController());
		
		GraphicUtilities.addToGBL(this, searchPanel, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 1, 1);
		GraphicUtilities.addToGBL(this, helpPanel, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1);
	}
	
	@Override
	public void translate() {
		for(int i = 0; i < musicBandTable.getColumnCount(); i++) {
			header.getColumnModel().getColumn(i).setHeaderValue(musicBandTable.getColumnName(i));
		}
		header.repaint();
		musicBandTable.adjustColumns();
		TranslatedContainer.super.translate();
	}
	
	public void updateTableData() {
		musicBandTable.updateTableData();
	}
	
	public void addDataUpdateListener(DataUpdateListener dataUpdateListener) {
		musicBandTable.addDataUpdateListener(dataUpdateListener);
	}
}
