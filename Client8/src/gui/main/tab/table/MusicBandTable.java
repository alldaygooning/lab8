package gui.main.tab.table;

import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

import application.*;
import gui.*;
import gui.event.*;
import gui.main.*;
import gui.main.tab.table.editor.*;
import gui.main.tab.table.renderer.*;
import gui.main.tab.table.selection.*;
import gui.main.tab.tool.DeleteKeyListener;
import gui.main.tab.tool.help.HoldProgressBarController;
import gui.main.tab.tool.search.SearchController;
import gui.miscellaneous.ErrorListener;
import gui.miscellaneous.notification.NotificationListener;

public class MusicBandTable extends CustomTable{
	private static final long serialVersionUID = 1L;
	
	private MusicBandTable musicBandTable;
	
	MusicBandTableModel musicBandTableModel;
	MusicBandSelectionModel musicBandSelectionModel;
	TableRowSorter<MusicBandTableModel> tableRowSorter;
	
	NotificationListener notificationListener;
	ErrorListener errorListener;
	DeleteKeyListener deleteKeyListener;
	
	private List<Integer> selected_ids;
	
	SearchController searchController;
	
	public MusicBandTable() {
		super();
		this.musicBandTable = this;
		
		setTableModel();
		setTable();
		setSelectionModel();
		
		setTableRenderers();
		setTableEditors();
		
		setListeners();
	}
	
	private void setTableModel() {
		musicBandTableModel = new MusicBandTableModel();
		
		tableColumnAdjuster = new TableColumnAdjuster(musicBandTable);
		musicBandTableModel.setColumnAdjuster(tableColumnAdjuster);
		
		tableRowSorter = new TableRowSorter<MusicBandTableModel>(musicBandTableModel);
		musicBandTable.setRowSorter(tableRowSorter);
		searchController = new SearchController() {
			@Override
			public void search(String string) {
				musicBandTable.setRowFilter(string);
				musicBandTable.adjustColumns();
			}
		};
	}
	
	private void setSelectionModel() {
		musicBandSelectionModel = new MusicBandSelectionModel();
		rowSelector = new RowSelector() {
			@Override
			public boolean rowSelectable(int rowIndex) {
				return musicBandTable.rowSelectable(rowIndex);
			}
		};
		musicBandSelectionModel.addRowSelector(rowSelector);
		
		this.setSelectionModel(musicBandSelectionModel);
	}
	
	private void setTable() {
		this.setModel(musicBandTableModel);
		
		this.setFillsViewportHeight(true);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setRowHeight(25);
		
		this.getColumnModel().getColumn(3).setCellEditor(new DoubleTableEditor(Double.MIN_VALUE, 587.0));
		
		this.setSurrendersFocusOnKeystroke(false);
	}
	
	public void adjustColumns() {
		tableColumnAdjuster.adjustColumns();
	}
	
	
	private String getRowOwner(int rowIndex) {
		String owner = (String) this.getValueAt(rowIndex, 12);
		return owner;
	}
	
	@Override
	protected boolean rowSelectable(int rowIndex) {
		String user = User.getLogin();
		String owner = this.getRowOwner(rowIndex);
		return(owner.equals(user));
	}
	
	private void setTableRenderers() {
		EnumTableRenderer<attribute.MusicGenre> musicGenreTableRenderer = new EnumTableRenderer<attribute.MusicGenre>(attribute.MusicGenre.class);
		EnumTableRenderer<attribute.Color> colorTableRenderer = new EnumTableRenderer<attribute.Color>(attribute.Color.class);
		EnumTableRenderer<attribute.Country> countryTableRenderer = new EnumTableRenderer<attribute.Country>(attribute.Country.class);
		
		this.setDefaultRenderer(attribute.MusicGenre.class, musicGenreTableRenderer);
		this.setDefaultRenderer(attribute.Country.class, countryTableRenderer);
		this.setDefaultRenderer(attribute.Color.class, colorTableRenderer);
	}
	
	private void setTableEditors() {
		EnumTableEditor<attribute.MusicGenre> musicGenreTableEditor = new EnumTableEditor<attribute.MusicGenre>(attribute.MusicGenre.class);
		EnumTableEditor<attribute.Color> colorTableEditor = new EnumTableEditor<attribute.Color>(attribute.Color.class);
		EnumTableEditor<attribute.Country> countryTableEditor = new EnumTableEditor<attribute.Country>(attribute.Country.class);
		
		this.setDefaultEditor(attribute.MusicGenre.class, musicGenreTableEditor);
		this.setDefaultEditor(attribute.Color.class, colorTableEditor);
		this.setDefaultEditor(attribute.Country.class, countryTableEditor);
	}

	@Override
	public void updateTableData() {
		this.saveSelection();
		musicBandTableModel.fireTableDataChanged();
		this.applySelection();
	}
	
	
	private void saveSelection() {
		selected_ids = Arrays.stream(musicBandTable.getSelectedRows())
				.map(rowIndex -> this.getRowId(rowIndex))
				.boxed()
				.toList();
		
	}
	
	private void applySelection() {
		ListSelectionModel selectionModel = this.getSelectionModel();
		selectionModel.clearSelection();
		for(int row = 0; row < this.getRowCount(); row++) {
			Integer id = this.getRowId(row);
			if(selected_ids.contains(id)) {
				selectionModel.addSelectionInterval(row, row);
			}
		}
	}
	
	public void addDataUpdateListener(DataUpdateListener dataUpdateListener) {
		musicBandTableModel.addDataUpdateListener(dataUpdateListener);
	}
	
	private void setListeners() {
		errorListener = new ErrorListener() {
			@Override
			public void errorOccured(ErrorEvent e) {
				MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(musicBandTable);
				mainFrame.dispatchEvent(e);
			}
		};
		
		notificationListener = new NotificationListener() {
			@Override
			public void displayNotification(NotificationDisplayRequestEvent e) {
				CustomFrame customFrame = (CustomFrame) SwingUtilities.getWindowAncestor(musicBandTable);
				customFrame.dispatchEvent(e);
			}
		};
		
		musicBandTableModel.addNotificationListener(notificationListener);
		musicBandTableModel.addErrorListener(errorListener);
		
		deleteKeyListener = new DeleteKeyListener(this);
		this.addKeyListener(deleteKeyListener);
	}
	
	public int getRowId(int rowIndex) {
		int id = (int) this.getValueAt(rowIndex, 0);
		return id;
	}
	
	@Override
	public boolean editCellAt(int row, int column, EventObject e) {
		if(e instanceof KeyEvent) {
			return false;
		}
		return super.editCellAt(row, column, e);
	}
	
	public HoldProgressBarController getBarController() {
		return deleteKeyListener.getBarController();
	}
	
	public SearchController getSearchController() {
		return this.searchController;
	}
	
	public void setRowFilter(String searchString) {
		if(!tableRowSorter.getSortKeys().isEmpty()) {
			tableRowSorter.setRowFilter(new RowFilter<Object, Object>() {
				@Override
				public boolean include(Entry<?, ?> entry) {
					int column = tableRowSorter.getSortKeys().get(0).getColumn();
					return(entry.getStringValue(column).indexOf(searchString) >= 0);
				}
			});
		}
	}
}
